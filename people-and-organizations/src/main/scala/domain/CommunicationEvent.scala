package sdo.peopleAndOrganizations.domain.communicationEvent

import sdo.peopleAndOrganizations.domain.{Party, Relationship}
import sdo.peopleAndOrganizations.domain.contactMechanisms.ContactMechanism
import sdo.core.domain._

class CommunicationEvent( initialId: EntityUuidIdField, 
													happensVia: ContactMechanism,
													inContextOf: Relationship) extends Entity{

	override val id = initialId

	val started = DateTimeField
	val ended = DateTimeField
	val note = TextField
	val purpose = PurposeField
	val involving =  new ListField[Role] ()
	def via = happensVia
	def context = inContextOf 
}

class Role( description: String, party: Party)

class RoleField extends Field[ Role]

object RoleField {
	def apply = new RoleField()
}

class Purpose( description: String)

class PurposeField extends Field[ Purpose]

object PurposeField {
	def apply = new PurposeField()
}
