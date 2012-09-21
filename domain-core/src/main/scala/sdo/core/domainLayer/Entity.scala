package sdo.core.domain

import org.scalastuff.scalabeans.Preamble._
import reactive.Observing

trait Entity extends Observing 	{

	type ValidationFunction = Entity => List[ValidationError]

	protected var validationErrorList : List[ValidationError] = Nil

	def descriptor =  descriptorOf[Entity]

	def fieldList : List[Field[_]] = Nil
	/*
	{
		descriptor.properties.map( p =>
			descriptor.get(this, p.name) match {
				case f:Field[_] => f
   	   case _ =>
			}
		).asInstanceOf[List[Field[_]]]
	}
	*/


	def dirty_? = fieldList.exists( f => f.dirty_?)

	def clean:Unit = fieldList.foreach( f => f.makeClean)

	def validationErrors [T <: ValidationError] : List[ValidationError] = validationErrorList ++ fieldList.flatMap( _.validationErrors)

	def validatorList : List[Entity => List[ValidationError]] = Nil 

	def runValidations(domainObject: Any):Unit  = validationErrorList = validatorList.flatMap(_(this))

	def setup() {
	fieldList.foreach( f =>  {
		f.change foreach runValidations 
		})
		}
}


