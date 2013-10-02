package sdo.workEffort.domain

import sdo.core.domain.{Entity, 
												EntityError,
												EntityUuidIdField,
												Field,
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

object WorkQueueValidationMethods {

	def processorAssignedWorkOverWip( )(workQueue: WorkQueue): List[EntityError] = {
		workQueue.processors.flatmap( processor =>
			if( processor.assignedTo.value.getOrElse(0) > workQueue.workInProgressLimit.value.getOrElse(0) {
				 	ProcessorHasMoreWipThanAllowed( party, 
				 																	assigned.length, 
				 																	workInProgressLimit.value.getOrElse(0))
			} 
	}

}

case class ProcessorHasMoreWipThanAllowed( party: Party, numberAssigned: Int, wipAllowed: Int) extends EntityError
