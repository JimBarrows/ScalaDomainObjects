package sdo.workEffort.domain
import sdo.core.domain.{ EntityUuidIdField, ListField }
import sdo.peopleAnOrganizations.domain.PartyRole

trait Facility

trait Party {

  def id: EntityUuidIdField

  def actingAs: ListField[Worker]

  val withARateOf = new ListField[PartyRate]()

  val assignedTo = new  ListField[PartyAssignment]()
}

trait Worker extends PartyRole {

  def submitting: List[TimeSheet]
}

trait Employee extends Worker {
}

trait Contractor extends Worker {
}
