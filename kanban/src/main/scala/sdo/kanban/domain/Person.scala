package sdo.kanban.domain

import sdo.core.domain.EntityUuidIdField
import sdo.core.domain.ListField
import sdo.peopleAnOrganizations.domain.Role
import sdo.peopleAnOrganizations.domain.{Person => PartyPerson}
import sdo.workEffort.domain.Party
import sdo.peopleAnOrganizations.domain.PartyRole
import sdo.workEffort.domain.Worker

class Person(initialId: EntityUuidIdField) extends PartyPerson(initialId) with Party {

  override val id = this.initialId

   override def actingAs:List[Worker] = this.roles.list.filter( r => 
       r.isInstanceOf[Worker])
       ).getOrElse(Nil).asInstanceOf[List[Worker]]

}