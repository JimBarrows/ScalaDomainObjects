package sdo.kanban.domain

import scalaz._
import Scalaz._
import sdo.core.domain.{Entity, 
						EntityError,
						EntityUuidIdField,
						Field,
						IntegerField,
						ListField}
import sdo.workEffort.domain.WorkEffort
import sdo.peopleAnOrganizations.domain.{Person => PartyPerson}

class WorkQueue( initialId: EntityUuidIdField) extends Entity {

	override val id = initialId

	val workInProgressLimit = IntegerField( 3)

	val backLogLimit = IntegerField( 15)

	val processors = new ListField[ Person]() 

	val workInProgress = new ListField[ WorkEffort]()

	val backlog = new ListField[ WorkEffort]() 

	override def validate = WorkQueueValidationMethods.processorCannotHaveMoreWipThanWipLimit(this)

	setup
}

object WorkQueue {

	def apply = new WorkQueue( EntityUuidIdField())
}

object WorkQueueValidationMethods {

	def processorCannotHaveMoreWipThanWipLimit(workQueue: WorkQueue):  ValidationNel[EntityError, WorkQueue] = {
		val wipLimit = workQueue.workInProgressLimit.value.getOrElse( 0)
		println("wipLimit: %s".format(wipLimit))
		val processorsWithMoreWip = workQueue.processors.list.filter( _.assignedTo.length > wipLimit)
		processorsWithMoreWip.foreach(f => println("processor: %s".format( f.toString)))
		if( processorsWithMoreWip.isEmpty) 
		  workQueue.successNel[EntityError]
		else		
			ProcessorHasMoreWipThanAllowed( processorsWithMoreWip, 	wipLimit).failureNel[WorkQueue] 
				
	}
	case class ProcessorHasMoreWipThanAllowed( party: List[Person], wipAllowed: Int) extends EntityError
}


