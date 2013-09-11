 package sdo.peopleAndOrganizations

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

import sdo.peopleAndOrganizations.contactMechanisms.Contactable

class Person(initialId :EntityUuidIdField) extends Entity 
																						with Party
																						with Classifications
																						with RolesToPlay
																						with Contactable{
	
	override val id = initialId

	val gender = GenderField
	val birthday = DateField 
 
}

object Person {
}

object Gender extends Enumeration {
	type Gender = Value
	val Male, Femail = Value
}

import Gender._

class GenderField extends Field[Gender]

object GenderField {
	def apply = new GenderField()
}
