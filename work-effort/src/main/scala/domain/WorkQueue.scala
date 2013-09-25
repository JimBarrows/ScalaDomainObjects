package sdo.workEffort.domain

import sdo.core.domain.{Entity, 
												EntityUuidIdField,
												IntegerField,
												ListField}

class WorkQueue( initialId: EntityUuidIdField) extends Entity {

	override val id = initialId

	val workInProgressLimit = IntegerField( 3)

	val backLogLimit = IntegerField( 15)

	val processors = new ListField[ Party]() 

	val workInProgress = new ListField[ WorkEffort]()

	val backlog = new ListField[ WorkEffort]() 

	setup
}

object WorkQueue {

	def apply = new WorkQueue( EntityUuidIdField())
}
