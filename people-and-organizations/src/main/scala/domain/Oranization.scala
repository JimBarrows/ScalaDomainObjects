package sdo.peopleAndOrganizations.domain

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField}
import sdo.core.domain.ValidationMethods._

import sdo.peopleAndOrganizations.domain.contactMechanisms.Contactable

class Organization(initialId :EntityUuidIdField) extends Entity 
																									with Party 
																									with Classifications 
																									with RolesToPlay
																									with Contactable{
																									

	override val id = initialId

	val name = TextField()

	override def fieldList :List[ Field[ _]] = List(name )
	setup

}

object Organization {
	def apply = new Organization( EntityUuidIdField())
	def apply( id :EntityUuidIdField) = new Organization( id)
}

trait GovernmentId

class GovernmentIdField extends Field[GovernmentId] 

class LegalOrganization( initialId :EntityUuidIdField) extends Organization(initialId) {

	val governmentId = new GovernmentIdField()
}

object LegalOrganization {
	def apply = new LegalOrganization( EntityUuidIdField())
	def apply( id :EntityUuidIdField) = new LegalOrganization( id)
}

class Corporation(initialId :EntityUuidIdField) extends LegalOrganization( initialId)

object Corporation {
	def apply = new Corporation( EntityUuidIdField())
	def apply( id :EntityUuidIdField) = new Corporation( id)
}

class InformalOrganization( initialId :EntityUuidIdField) extends Organization(initialId) {

}

object InformalOrganization {
	def apply = new InformalOrganization( EntityUuidIdField())
	def apply( id :EntityUuidIdField) = new InformalOrganization( id)
}

class Team( initialId :EntityUuidIdField) extends InformalOrganization(initialId) {

}

object Team {
	def apply = new Team( EntityUuidIdField())
	def apply( id :EntityUuidIdField) = new Team( id)
}
