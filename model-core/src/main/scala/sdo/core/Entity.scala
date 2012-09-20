package sdo.core

import org.scalastuff.scalabeans.Preamble._
import reactive.Observing

trait Entity extends Observing 	{

	type ValidationFunction = Entity => List[EntityError]

	protected var validationErrorList : List[EntityError] = Nil

	def descriptor =  descriptorOf[Entity]

	def fieldList : List[Field[_]] = {
		descriptor.properties.map( p =>
			descriptor.get(this, p.name) match {
				case f:Field[_] => f
   	   case _ =>
			}
		).asInstanceOf[List[Field[_]]]
	}


	def dirty_? = fieldList.exists( f => f.dirty_?)

	def clean:Unit = fieldList.foreach( f => f.makeClean)

	def validationErrors [T <: ValidationError] : List[ValidationError] = validationErrorList ++ fieldList.flatMap( _.validationErrors)

	def validatorList : List[ValidationFunction] = Nil 

	def runValidations(domainObject: Any):Unit  = validationErrorList = validatorList.flatMap(_(this))

	def setup() {
	fieldList.foreach( f =>  {
		f.change foreach runValidations 
		})
		}
}
