package sdo.peopleAndOrganizations.domain

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

import sdo.peopleAndOrganizations.domain.contactMechanisms.Contactable

trait RolesToPlay {

	val roles =new  ListField[RoleField]()
}

trait Role extends Contactable {
	val applies :DateRangeField
}

class RoleField extends Field[ Role] {
	override def toString = "RoleField( %s)".format( value.toString)
}

trait PersonRole extends Role

class Employee( dateRange :DateRangeField)  extends PersonRole {
	override val applies = this.dateRange
}


trait OrganizationRole extends Role

class OrganizationUnit(dateRange: DateRangeField) extends OrganizationRole {
	override val applies = this.dateRange
}

class Partner(dateRange :DateRangeField) extends OrganizationRole{
	override val applies = this.dateRange
}

class InternalOrganization(dateRange :DateRangeField) extends OrganizationRole{
	override val applies = this.dateRange
}

class Customer(dateRange :DateRangeField) extends PersonRole with OrganizationRole{
	override val applies = this.dateRange
}

class BillTocustomer(dateRange :DateRangeField) extends Customer (dateRange)

class Prospect(dateRange :DateRangeField) extends PersonRole with OrganizationRole{
	override val applies = this.dateRange
}

