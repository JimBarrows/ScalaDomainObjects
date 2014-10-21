package sdo.kanban.domain

import sdo.core.domain.EntityUuidIdField
import sdo.core.domain.ListField
import sdo.peopleAnOrganizations.domain.Role
import sdo.peopleAnOrganizations.domain.{Person => PartyPerson}
import sdo.workEffort.domain.{Party => WorkEffortParty}
import sdo.peopleAnOrganizations.domain.PartyRole
import sdo.workEffort.domain.Worker

class Person(initialId: EntityUuidIdField) extends PartyPerson(initialId) with WorkEffortParty {

  override val id = this.initialId
   

}