package sdo.peopleAndOrganizations

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._


/** This is the information surrounding the end point.
*/
class ContactMechanism  extends ValueObject {

	val solicitable :BooleanField = new BooleanField

	override def fieldList = super.fieldList ::: solicitable :: Nil
	
}

object ContactMechanism {
	
	def apply() = {
		val cm = new ContactMechanism()
		cm.setup
		cm
	}
}

/**Implement this trait on someone, group or thing that can be contacted.
*/
trait Contactable[ T <: ContactMechanism] {
	
	private val contactMechanisms :List[ T] = Nil
}


class ElectronicAddress extends ContactMechanism {
}

object ElectronicAddress {
	
	def apply() = {
		val cm = new ElectronicAddress()
		cm.setup
		cm
	}
}
