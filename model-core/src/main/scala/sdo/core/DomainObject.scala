package sdo.core

import reactive.Observing

trait DomainObject extends Observing 	{

	type ValidationFunction = DomainObject => List[DomainObjectError]

	protected var validationErrorList : List[DomainObjectError] = Nil

	def fieldList : List[Field[_]] =  Nil

	def dirty_? = fieldList.exists( f => f.dirty_?)

	def clean:Unit = fieldList.foreach( f => f.makeClean)

	def validationErrors [T <: ValidationError] : List[ValidationError] = validationErrorList ++ fieldList.flatMap( _.validationErrors)

	def validatorList : List[ValidationFunction] = Nil 

	def runValidations  = validationErrorList = validatorList.flatMap(_(this))

}
