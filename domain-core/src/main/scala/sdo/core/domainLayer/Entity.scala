package sdo.core.domain

import org.scalastuff.scalabeans.Preamble._
import reactive.Observing

/**  An object not fundamentally defined by it's attributes, but rather by a thread of continuity and identity
*/
trait Entity extends Observing with Validation	{

	val id = EntityUuidIdField

	def descriptor =  descriptorOf[Entity]

	def fieldList : List[Field[_]] = Nil

	/**Sets up the object.  Since this trait is done before a subclass, the fieldList will a list of nulls,
	  * which is not what we want, so we have to delay the setup of the obsersvers.
	  */
	def setup { 
		fieldList.foreach( field =>  field.change foreach runValidations )
	}

	def dirty_? = fieldList.exists( f => f.dirty_?)

	def clean:Unit = fieldList.foreach( f => f.makeClean)

	override def validationErrors :List[ValidationError] = validationErrorList ++ fieldList.flatMap( _.validationErrors) 

	override def equals( that :Any) = that match {
		case entity :Entity => entity.id == this.id
	}

	/** for some reason the Observing functionality needs a Any => Unit method, so we provide one
	*/
	protected def runValidations( dO :Any) :Unit = validate

}


