package sdo.peopleAnOrganizations.domain

import sdo.core.domain.DateTimeField
import sdo.core.domain.Entity
import sdo.core.domain.EntityUuidIdField
import sdo.core.domain.Field
import sdo.core.domain.ListField
import sdo.core.domain.TextField

class CommunicationEvent(initialId: EntityUuidIdField,
  happensVia: ContactMechanism,
  inContextOf: Relationship) extends Entity {

  override val id = initialId

  val started = DateTimeField
  val ended = DateTimeField
  val note = TextField
  val purpose = PurposeField
  val involving = new ListField[Role]()
  def via = happensVia
  def context = inContextOf
}

