package sdo.peopleAnOrganizations.domain

import sdo.core.domain._

class Party(initialId: EntityUuidIdField) extends Entity
											with Classifications
											with Contactable {
	 override val id = initialId
}
