package sdo.ecommerce.domain
import sdo.core.domain.{ DateRangeField, Entity, EntityUuidIdField }
import sdo.peopleAnOrganizations.domain.RolesToPlay
import sdo.peopleAnOrganizations.domain.ConsumerRole
import sdo.peopleAnOrganizations.domain.Relationship
import sdo.peopleAnOrganizations.domain.PersonRole
import sdo.peopleAnOrganizations.domain.Party
import sdo.peopleAnOrganizations.domain.OrganizationRole
import sdo.peopleAnOrganizations.domain.Role
import sdo.peopleAnOrganizations.domain.PartyRole

class AutomatedAgent(initialId: EntityUuidIdField) extends Party(initialId)
                                                    with RolesToPlay[PartyRole] {

}

class Webmaster extends PersonRole

class Isp extends OrganizationRole

class Visitor extends ConsumerRole

class Subscriber extends ConsumerRole

class Referrer extends Role

class AutomatedAgentRole extends Role

class HostingServer extends AutomatedAgentRole

class WebMasterAssignment(from: AutomatedAgentRole,
  to: Webmaster,
  period: DateRangeField)
  extends Relationship(from, to, period)

class VisitorIsp(from: Visitor,
  to: Isp,
  period: DateRangeField)
  extends Relationship(from, to, period)

class HostServerVisitor(from: Visitor,
  to: HostingServer,
  period: DateRangeField)
  extends Relationship(from, to, period)
