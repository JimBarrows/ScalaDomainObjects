package sdo.peopleAndOrganizations.domain

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

import sdo.peopleAndOrganizations.domain.contactMechanisms.Contactable

trait RolesToPlay {

	val roles =new  ListField[RoleField]()
}

trait Role extends Contactable {
	val applies =DateRangeField()
}

class RoleField extends Field[ Role] {
	override def toString = "RoleField( %s)".format( value.toString)
}

trait PersonRole extends Role

class Employee  extends PersonRole {
}


trait OrganizationRole extends Role

class OrganizationUnit extends OrganizationRole {
}

class Partner extends OrganizationRole{
}

class InternalOrganization extends OrganizationRole{
}

class ConsumerRole extends PersonRole with OrganizationRole {
}

class Customer extends ConsumerRole

class BillTocustomer extends Customer

class Prospect extends ConsumerRole

