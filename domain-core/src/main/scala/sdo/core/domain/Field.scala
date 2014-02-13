package sdo.core.domain

import scala.math.BigInt
import scala.collection.mutable.MutableList
import java.util.{Currency, Locale, UUID}

import com.github.nscala_time.time.Imports._
import org.joda.time.DateMidnight

import reactive.{Signal, EventStream, EventSource, Observing, CanForwardTo, Forwardable, NamedFunction }
import reactive.CanForwardTo.vari
import reactive.CanForwardTo.eventSource
import ValidationMethods._


class Field[T] extends Signal[T] with Validation[Option[T]] with ChangeStateTracking{

	protected var data:Option[T] = None

	private var writable = true
	
	def writable_? = writable

	override def now = data.get

	override def validate :Unit = validationErrorList = validations.flatMap( v => v(data))

	def value:Option[T] = data

	def assign( newValue : Option[T]) :Field[T]  = {
		if (! data.equals( newValue) && (writable_? || ! initialized_? )) {
			data = newValue
			validate
			makeDirty
			change0.fire( newValue.get)
		} 
		this
	}

	def value_=(newValue : Option[T]) = assign( newValue)

	def value_=(newValue : T) = if( newValue == null) assign(None) else assign( Some( newValue))

	def update( newValue: T):Unit = value = newValue

	override def equals( that :Any) = {
		(that.getClass == this.getClass) && (that.asInstanceOf[Field[T]].value == this.value)
	}

	def makeReadOnly:Unit = writable=false

	lazy val change: EventStream[T] = change0

  protected lazy val change0 = new EventSource[T] 

	def <-->(other: Field[T])(implicit observing: Observing): this.type = {
		this.distinct >> other
		other.distinct >> this
		this
  }

	override def toString = "Field[T]( %s)".format( data)
}

object Field {
 
	implicit def vari[T]: CanForwardTo[Field[T], T] = new CanForwardTo[Field[T], T] {
		def forwarder(t: => Field[T]) = NamedFunction(">>"+t.debugName)(t.update)
  }
	implicit def eventSource[T]: CanForwardTo[EventSource[T], T] = new CanForwardTo[EventSource[T], T] {
		def forwarder(t: => EventSource[T]) = NamedFunction(">>"+t.debugString)(t.fire)
  }
}

class EntityIdField[T]( id :T) extends Field[T] {
	override def writable_? = false

	value =( id)
}

class EntityUuidIdField( val id :UUID) extends EntityIdField[UUID]( id) {
	override def toString = "EntityUuidIdField( %s)".format( data)
}

object EntityUuidIdField {
	def apply() :EntityUuidIdField = {
		val i = UUID.randomUUID()
		new EntityUuidIdField( i)
	}
}

/** A Field consisting entirely of numbers
*/
class NumericField extends Field[String] {
	override def validations:List[ValidationFunction] = allNumeric _  :: Nil
	override def toString = "NumericField( %s)".format( data)
}

object NumericField {

	def apply = new NumericField()
	def apply( value :String) = {
		val nf = new NumericField()
		nf.value = (value)
		nf
	}

	def apply( value :Integer) = {
		val nf = new NumericField()
		nf.value = (value.toString)
		nf
	}
}

/** A Field consisting entirely of alphabetic characters, and punctuation
*/
class AlphaField extends Field[String] {
	override def validations:List[ValidationFunction] = allAlpha _  :: Nil
	override def toString = "AlphaField( %s)".format( data)
}

/** A Field that is either true or false.
*/
class BooleanField extends Field[Boolean] {
	override def toString = "BooleanField( %s)".format( data)
}

object BooleanField {

	def apply = new BooleanField()

	def apply( init: Boolean) = {
		val bf = new BooleanField()
		bf.value = (init)
		bf
	}
}

/** A class representing the mathematical concept of Integers.
*/
class IntegerField extends Field[BigInt] {
	override def toString = "IntegerField( %s)".format( data)
}

object IntegerField {
	def apply( value :Integer) = {
		val intField = new IntegerField() 
		intField value = BigInt(value)
		intField
	}
}

/** A field that can be anything that will fit in a string, but isn't that long.*/
class ShortTextField extends Field[String] {
	override def validations :List[ValidationFunction] = maxLength( 140) _:: Nil
	override def toString = "ShortTextField( %s)".format( data)
}

object ShortTextField {
	def apply( text :String) = (new ShortTextField().value = text).asInstanceOf[ShortTextField]
	def apply( ) = new ShortTextField()
}

/**A field that represents notes and other large text.*/
class TextField extends Field[String] {
	override def toString = "TextField( %s)".format( data)
}

object TextField {
	def apply( text :String) :TextField = new TextField().value_=(text).asInstanceOf[TextField]
	def apply() :TextField = new TextField()
}

class DateTimeField extends Field[DateTime] {
	override def toString = "DateTimeField( %s)".format( data)
}

object DateTimeField {
	def apply( newDateTime :DateTime) :DateTimeField = new DateTimeField().value_=(newDateTime).asInstanceOf[DateTimeField]
	def apply() :DateTimeField = new DateTimeField()
}

class DateField extends Field[DateMidnight] 

object DateField {
	def apply = new DateField()
}

class ListField[T] extends Field[ MutableList[ T]] {

	data = Some( new MutableList[T]())

	def +=( newValue: T): Unit = add( newValue)

	def add( newValue :T) :Unit = data.map( l => { 

		if (writable_? || ! initialized_? ) {
			l += newValue 
			validate
			makeDirty
			change0.fire( l)
		} 
	})

	def length: Int = list.length

	def find( p: (T) => Boolean):Option[T] = data.map( _.find( p)).getOrElse(None)

	def list: List[T] = data.getOrElse(new MutableList[T]).toList

	def exists( predicate: T => Boolean): Boolean  = data.map( _.exists( predicate)).getOrElse(false)

	def contains( value: T): Boolean = data.map( _.contains( value)).getOrElse(false)

	def remove( value :T) :Unit = data = data.map( l=> {
		if (writable_? || ! initialized_? ) {
			val newList = l.diff( value :: Nil)	
			validate
			makeDirty
			change0.fire( newList)
			newList
		} else {
			l
		}
	})

	override def toString = "ListField( %s)".format( data)
}


case class Range[T]( from: Option[T], thru :Option[T])

class RangeField[T] extends Field[ Range[T]] {
}

case class DateRange( from :DateMidnight, thru :Option[DateMidnight])


class DateRangeField extends Field[ DateRange] {
}

object DateRangeField {
	
	def apply() :DateRangeField= {
		val drf = new DateRangeField()
		drf.value = new DateRange( DateMidnight.now, None)
		drf
	}
}

case class DateTimeRange( from :DateTime, thru :Option[DateTime])

class DateTimeRangeField extends Field[ DateTimeRange] {
}

/** Immutable money class.
 */
case class Money( amount :BigDecimal, currency :Currency) {

	/**Convience method for creating the result of an operation.
	 */
	private def newMoney(amount :BigDecimal) = Money( amount, this.currency)

	def +(money :Money) = {
		require(money.currency.equals(currency), "Currencies must be the same. this: %s that:%s".format(this.currency, money.currency))
		newMoney( amount + money.amount)
	}

	def -(money :Money) = {
		require(money.currency.equals(currency), "Currencies must be the same. this: %s that:%s".format(this.currency, money.currency))
		newMoney( amount - money.amount)
	}
}

object Money {

	def $( amount :BigDecimal) = Money( amount, Currency.getInstance(Locale.US))
}

class MoneyField extends Field[Money]{
}

object MoneyField {
	def apply() = new MoneyField()
}
