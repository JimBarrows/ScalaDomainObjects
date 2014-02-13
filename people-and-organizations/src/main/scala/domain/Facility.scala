package sdo.peopleAndOrganizations.domain

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField}
import sdo.core.domain.ValidationMethods._

import sdo.peopleAndOrganizations.domain.contactMechanisms.Contactable

class Facility( initialId: EntityUuidIdField) extends Entity
																							with Contactable {

	override val id = initialId
	val description = TextField
	val roles: List[FacilityRoleField] = Nil
	val partOf: Option[Facility] = None
	val madeUpOf: List[Facility] = Nil
}

class Warehouse(initialId: EntityUuidIdField) extends Facility( initialId) {
}

case class FacilityRole( forParty: Party)

class FacilityRoleField extends Field[FacilityRole]
