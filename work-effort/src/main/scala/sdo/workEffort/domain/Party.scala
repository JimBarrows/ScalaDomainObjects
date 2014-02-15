package sdo.workEffort.domain
import sdo.core.domain.{ EntityUuidIdField, ListField }

trait Facility

trait Party {

  def id: EntityUuidIdField

  def actingAs: ListField[PartyRole]

  val withARateOf = new ListField[PartyRate]()

  val assignedTo = new  ListField[PartyAssignment]()
}

trait PartyRole {
}

trait Worker extends PartyRole {

  def submitting: List[TimeSheet]
}

trait Employee extends Worker {
}

trait Contractor extends Worker {
}
