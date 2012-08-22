package sdo.core 

import ValidationMethods._

trait Field[ValueType] {

	type ValidationFunction = Option[ValueType] => List[FieldError]

	protected var dirty = false

	protected var initialized = false

	protected var data:Option[ValueType] = None

	def dirty_? = dirty
	
	def readable_? = true

	def writable_? = true

	def validations:List[ValidationFunction]=Nil

	def validate: List[FieldError] = runValidations( value)

	def clean_? = ! dirty

	def initialized_? = initialized

	protected def runValidations(in :Option[ValueType]) :List[FieldError] = in match{
		case Some(_) => validations.flatMap(_ ( in)).removeDuplicates
		case None => Nil
	}

	def value:Option[ValueType] = synchronized {
		if( readable_?) data
		else None
	}


	def assign( newValue : Option[ValueType]):List[FieldError] = {
		if (! data.equals( newValue) && (writable_? || ! initialized_? )) {
			var errors = runValidations( newValue)
			if( errors.isEmpty) {
				data = newValue
				dirty = true
				initialized=true
			} 
			return errors
		} 
		return Nil
	}

	def makeClean = dirty=false
}

/** A Field consisting entirely of numbers
*/
class NumericField extends Field[String] {
	override def validations:List[ValidationFunction] = allNumeric _  :: Nil
}

/** A Field consisting entirely of alphabetic characters, and punctuation
*/
class AlphaField extends Field[String] {
	override def validations:List[ValidationFunction] = allAlpha _  :: Nil
}

class TextField extends Field[String] {
}
