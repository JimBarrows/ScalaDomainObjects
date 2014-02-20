package sdo.kanban.domain

import sdo.core.domain.EntityUuidIdField
import sdo.core.domain.ListField
import sdo.peopleAnOrganizations.domain.Role
import sdo.peopleAnOrganizations.domain.{Person => PartyPerson}
import sdo.workEffort.domain.Party
import sdo.peopleAnOrganizations.domain.PartyRole

class Person(initialId: EntityUuidIdField) extends PartyPerson(initialId) with Party {

  override val id = this.initialId

   override def actingAs = this.roles

}