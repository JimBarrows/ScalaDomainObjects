package sdo.core.domain

import java.util.UUID

import reactive.{Signal, EventStream, EventSource, Observing, CanForward, Forwardable, NamedFunction}
import ValidationMethods._

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

class Field[T] extends Signal[T] {

	type ValidationFunction = Option[T] => List[FieldError]

	protected var validationErrorList:List[FieldError] = Nil

	protected var data:Option[T] = None

	protected var dirty = false

	protected var initialized = false

	private var writable = true


	def dirty_? = dirty
	
	def writable_? = writable

	def validations:List[ValidationFunction]=Nil

	def clean_? = ! dirty

	def initialized_? = initialized

	override def now = data.get

	lazy val change: EventStream[T] = change0
  private lazy val change0 = new EventSource[T] 

	protected def runValidations(in :Option[T]) :List[FieldError] = in match{
		case Some(_) => validations.flatMap(_ ( in)).removeDuplicates
		case None => Nil
	}

	def value:Option[T] = data


	def assign( newValue : Option[T]):Field[T] = {
		if (! data.equals( newValue) && (writable_? || ! initialized_? )) {
			validationErrorList = runValidations( newValue)
			data = newValue
			dirty = true
			initialized=true
			change0.fire( newValue.get)
		} 
		this
	}

	def value_=(newValue : Option[T]) = assign( newValue)

	def value_=(newValue : T) :Field[T]= assign( newValue)

	def assign( newValue : T):Field[T] = {
		if (newValue == null) 
			assign( None) 
		else 
			assign( Some( newValue))
	}

	def update( newValue: T):Unit = assign( newValue)

	def makeClean:Unit = dirty=false

	def makeReadOnly:Unit = writable=false

	def validationErrors = validationErrorList

	def <-->(other: Field[T])(implicit observing: Observing): this.type = {
		this.distinct >> other
		other.distinct >> this
		this
  }
}

class EntityIdField( uuid :UUID) extends Field[UUID] {
	override def writable_? = false

	value_=( uuid)
}

object EntityIdField {
	def apply = new EntityIdField( UUID.randomUUID())
}

/** A Field consisting entirely of numbers
*/
class NumericField extends Field[String] {
	override def validations:List[ValidationFunction] = allNumeric _  :: Nil
}

object NumericField {
	def apply( value :String) = {
		val nf = new NumericField()
		nf.value_= (value)
		nf
	}
	def apply( value :Integer) = {
		val nf = new NumericField()
		nf.value_= (value.toString)
		nf
	}
}

/** A Field consisting entirely of alphabetic characters, and punctuation
*/
class AlphaField extends Field[String] {
	override def validations:List[ValidationFunction] = allAlpha _  :: Nil
}

/**A field that represents notes and other large text.*/
class TextField extends Field[String] {
}

/** A Field that is either true or false.
*/
class BooleanField extends Field[Boolean] {
}
