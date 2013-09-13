package sdo.peopleAndOrganizations.domain

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

trait Relationships{

	val relationships = new ListField[Relationship]()
}

class Relationship( from: Role, to: Role, period :DateRangeField) {

}

class EmploymentRelationship( from: InternalOrganization, to :Employee, period :DateRangeField) extends Relationship( from, to, period)

class CustomerRelationship( from: Customer, to: InternalOrganization, period: DateRangeField) extends Relationship(from, to, period)

class OrganizationRollup( from: OrganizationUnit, to: OrganizationUnit, period: DateRangeField) extends Relationship(from, to, period)
