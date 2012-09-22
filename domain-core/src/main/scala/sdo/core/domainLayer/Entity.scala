package sdo.core.domain

import org.scalastuff.scalabeans.Preamble._
import reactive.Observing

/**  An object not fundamentally defined by it's attributes, but rather by a thread of continuity and identity
*/
trait Entity extends Observing 	{

	type ValidationFunction = Entity => List[ValidationError]

	protected var validationErrorList : List[ValidationError] = Nil

	def descriptor =  descriptorOf[Entity]

	def fieldList : List[Field[_]] = Nil

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


