package sdo.peopleAnOrganizations.domain

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

/**Trait to manage the roles an entity might play.
 * 
 */
trait RolesToPlay[T <: Role] {

  val roles = new ListField[T]()
}

/**Base class for all roles.
 * 
 */
class Role extends ValueObject {
  val applies = DateRangeField()
}

class RoleField extends Field[Role] {
  override def toString = "RoleField( %s)".format(value.toString)
}

trait PartyRole extends Role

trait PersonRole extends PartyRole

trait OrganizationRole extends PartyRole

class Employee extends PersonRole 

class OrganizationUnit extends OrganizationRole 

class Partner extends OrganizationRole 

class InternalOrganization extends OrganizationRole 

class ConsumerRole extends PersonRole with OrganizationRole 

class Customer extends ConsumerRole

class BillTocustomer extends Customer

class Prospect extends ConsumerRole
