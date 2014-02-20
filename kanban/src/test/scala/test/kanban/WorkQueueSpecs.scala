package test.kanban

import scalaz._
import Scalaz._
import org.specs2.mutable.Specification
import sdo.core.domain.EntityUuidIdField
import sdo.core.domain.ListField
import sdo.workEffort.domain.WorkQueue
import sdo.kanban.domain.Person
import sdo.workEffort.domain.WorkEffort
import sdo.workEffort.domain.PartyAssignment
import sdo.workEffort.domain.ProcessorHasMoreWipThanAllowed

class WorkQueueSpecs extends Specification {

  "The WorkQueue" should {

    "add party as a processor" in {
      val workQ = new WorkQueue(EntityUuidIdField())
      val person = new Person(EntityUuidIdField()) 

      workQ.processors += person

      workQ.processors.exists(_.id.value == person.id.value) must beTrue
    }

    "add work effort to the queue" in {
      val workQ = new WorkQueue(EntityUuidIdField())
      val workEffort = WorkEffort()
      workQ.workInProgress += workEffort

      workQ.workInProgress.exists(_.id.value == workEffort.id.value) must beTrue
    }

    "raise an error is a work effort is assigned to a person not assigned to the work queue" in {
      pending
    }

    "raise an error when a processor has been assigned more work in progress than allowed" in {
      val workQ = new WorkQueue(EntityUuidIdField())
      val person = new Person(EntityUuidIdField())
        
      workQ.processors += person
      
      workQ.workInProgressLimit.value = 3
      
      1 to 4 foreach { _ => workQ.workInProgress += (WorkEffort()) }      
      println("wip set to: %d".format(workQ.workInProgress.length))
      
      workQ.workInProgress.list.foreach( _.assignTo( person))
      
      person.assignedTo.value.foreach( f => 
        println("Person %s is assigned %s".format(person.id.value, f.toString)))
        
      workQ.validate must_== Failure(ProcessorHasMoreWipThanAllowed(workQ.processors.value.get.toList, 3))
    }

    "raise an error when a backlog has more than it's limit * number of processors" in {
      pending
    }

    "raise an error when the list of work in progress list is greater than the number of processors times the work in porgress limit" in {
      pending
    }

    "raise an error if backlog or work in progress is added to and there are no processors" in {
      pending
    }

    "raise an error is there are backlog or work in progress and the last process is removed" in {
      pending
    }
  }
}
