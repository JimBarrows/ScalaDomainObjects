package sdo.workEffort.domain
import sdo.core.domain.{ EntityUuidIdField, ListField }

trait Facility

trait Party {

  def id: EntityUuidIdField

  def actingAs = new ListField[PartyRole]

  def withARateOf = new ListField[PartyRate]

  def assignedTo = new ListField[PartyAssignment]()
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
