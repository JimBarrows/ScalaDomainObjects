package sdo.workEffort.domain

import sdo.core.domain.{Entity, EntityUuidIdField}

class WorkQueue( initialId: EntityUuidIdField) extends Entity {

	override val id = initialId

	val workInProgress: List[ WorkEffort] = Nil

	val backlog: List[ WorkEffort] = Nil
}
