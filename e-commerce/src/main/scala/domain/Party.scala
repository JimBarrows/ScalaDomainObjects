package sdo.ecommerce.domain

import sdo.peopleAndOrganizations.domain.{ConsumerRole, OrganizationRole, Party, Relationship, PersonRole, Role, RolesToPlay}
import sdo.core.domain.{DateRangeField, Entity, EntityUuidIdField}

class AutomatedAgent(initialId: EntityUuidIdField) extends Entity 
											with Party
											with RolesToPlay {
	override val id = initialId
}

class Webmaster extends PersonRole

class Isp extends OrganizationRole

class Visitor extends ConsumerRole

class Subscriber extends ConsumerRole

class Referrer extends Role

class AutomatedAgentRole extends Role 

class HostingServer extends AutomatedAgentRole 


class WebMasterAssignment( from: AutomatedAgentRole, 
														to: Webmaster, 
														period: DateRangeField)
		extends Relationship( from, to, period)

class VisitorIsp( from: Visitor, 
									to: Isp, 
									period: DateRangeField)
		extends Relationship( from, to, period)

class HostServerVisitor( from: Visitor, 
													to: HostingServer, 
													period: DateRangeField)
		extends Relationship( from, to, period)
