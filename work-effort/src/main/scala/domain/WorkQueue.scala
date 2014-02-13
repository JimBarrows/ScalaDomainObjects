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

	override def validations: List[ValidationFunction] = WorkQueueValidationMethods.processorCannotHaveMoreWipThanWipLimit() _ :: Nil

	setup
}

object WorkQueue {

	def apply = new WorkQueue( EntityUuidIdField())
}

object WorkQueueValidationMethods {

	def processorCannotHaveMoreWipThanWipLimit( )(workQueue: WorkQueue): List[EntityError] = {
		val wipLimit = workQueue.workInProgressLimit.value.getOrElse(BigInt(0))
		workQueue.processors.list.filter( _.assignedTo.length > wipLimit).map( p=>
			ProcessorHasMoreWipThanAllowed( p, 
																			p.assignedTo.length, 
																			wipLimit))
	}
}

case class ProcessorHasMoreWipThanAllowed( party: Party, numberAssigned: Int, wipAllowed: BigInt) extends EntityError
