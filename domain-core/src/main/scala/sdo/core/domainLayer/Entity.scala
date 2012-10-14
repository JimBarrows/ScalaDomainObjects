package sdo.core.domain

import org.scalastuff.scalabeans.Preamble._
import reactive.Observing

/**  An object not fundamentally defined by it's attributes, but rather by a thread of continuity and identity
*/
class Entity extends Observing with Validation	{

	val id = EntityUuidIdField

	def descriptor =  descriptorOf[Entity]

	def fieldList : List[Field[_]] = Nil

	def dirty_? = fieldList.exists( f => f.dirty_?)

	def clean:Unit = fieldList.foreach( f => f.makeClean)

	override def validationErrors :List[ValidationError] = validationErrorList ++ fieldList.flatMap( _.validationErrors) 

	override def equals( that :Any) = that match {
		case entity :Entity => entity.id == this.id
	}

//	protected def runValidations( dO :Any) :Unit = validate( None)

/*	fieldList.foreach( f =>  {
 		f.change foreach runValidations 
 		})
 		*/
}


