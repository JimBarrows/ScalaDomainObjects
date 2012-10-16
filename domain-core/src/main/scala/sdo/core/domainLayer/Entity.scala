package sdo.core.domain

import org.scalastuff.scalabeans.Preamble._
import reactive.Observing

/**  An object not fundamentally defined by it's attributes, but rather by a thread of continuity and identity
*/
trait Entity extends Observing with Validation	with ChangeStateTracking{

	val id = EntityUuidIdField

	def descriptor =  descriptorOf[Entity]

	def fieldList : List[Field[_]] = Nil

	/**Sets up the object.  Since this trait is done before a subclass, the fieldList will a list of nulls,
	  * which is not what we want, so we have to delay the setup of the obsersvers.
	  */
	def setup { 
		fieldList.foreach( field =>  field.change foreach runValidations )
		fieldList.foreach( field => field.change foreach updateChangeState)
	}

	override def makeClean:Unit = {
		fieldList.foreach( f => f.makeClean)
		state = ChangeState.clean
		}


	override def validationErrors :List[ValidationError] = validationErrorList ++ fieldList.flatMap( _.validationErrors) 

	override def equals( that :Any) = that match {
		case entity :Entity => entity.id == this.id
	}

	/** for some reason the Observing functionality needs a Any => Unit method, so we provide one
	*/
	protected def runValidations( dO :Any) :Unit = validate

	/** Updates the current state based on the fields state. Any field that is dirty, the Entity is dirty, otherwise
	  * it's clean.*/
	protected def updateChangeState( d :Any) :Unit = 
		if (fieldList.exists( _.dirty_?)) state = ChangeState.dirty else state = ChangeState.clean

}


