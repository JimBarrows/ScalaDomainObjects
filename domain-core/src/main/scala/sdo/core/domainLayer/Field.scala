package sdo.core.domain

import scala.math.BigInt
import scala.collection.mutable.MutableList
import java.util.UUID

import org.scala_tools.time.Imports._
import org.joda.time.DateMidnight

import reactive.{Signal, EventStream, EventSource, Observing, CanForward, Forwardable, NamedFunction }
import reactive.CanForward.vari
import reactive.CanForward.eventSource
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

	implicit def vari[T]: CanForward[Field[T], T] = new CanForward[Field[T], T] {
		def forward(s: Forwardable[T], t: => Field[T])(implicit o: Observing) = 
			s foreach NamedFunction(">>"+t.debugName)(t.update)
  }
	implicit def eventSource[T]: CanForward[EventSource[T], T] = new CanForward[EventSource[T], T] {
		def forward(s: Forwardable[T], t: => EventSource[T])(implicit o: Observing) = 
			s foreach NamedFunction(">>"+t.debugString)(t.fire)
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

	def apply() = new NumericField()
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
}

class DateTimeField extends Field[DateTime] {
	override def toString = "DateTimeField( %s)".format( data)
}

class DateField extends Field[DateMidnight] {
}

class ListField[T] extends Field[ MutableList[ T]] {

	data = Some( new MutableList[T]())

	def add( newValue :T) :Unit = data.map( l => { 

		if (writable_? || ! initialized_? ) {
			l += newValue 
			validate
			makeDirty
			change0.fire( l)
		} 
	})

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
