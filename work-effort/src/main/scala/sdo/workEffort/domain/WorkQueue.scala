package sdo.workEffort.domain

import scalaz._
import scalaz.Scalaz._
import sdo.core.domain.{ Entity, EntityError, EntityUuidIdField, IntegerField, ListField }

class WorkQueue(initialId: EntityUuidIdField) extends Entity {

  override val id = initialId

  val workInProgressLimit = IntegerField(3)

  val backLogLimit = IntegerField(15)

  val processors = new ListField[Party]()

  val workInProgress = new ListField[WorkEffort]()

  val backlog = new ListField[WorkEffort]()

  override def validate = WorkQueueValidationMethods.processorCannotHaveMoreWipThanWipLimit(this)

  setup
}

object WorkQueue {

  def apply = new WorkQueue(EntityUuidIdField())
}

object WorkQueueValidationMethods {

  def processorCannotHaveMoreWipThanWipLimit(workQueue: WorkQueue): ValidationNel[EntityError, WorkQueue] = {
    val wipLimit = workQueue.workInProgressLimit.value.getOrElse(0)
    val processorsWithMoreWip = workQueue.processors.list.filter(_.assignedTo.length > wipLimit)
    if (processorsWithMoreWip.isEmpty)
      workQueue.successNel[EntityError]
    else
      ProcessorHasMoreWipThanAllowed(processorsWithMoreWip, wipLimit).failureNel[WorkQueue]

  }
}

case class ProcessorHasMoreWipThanAllowed(party: List[Party], wipAllowed: Int) extends EntityError

