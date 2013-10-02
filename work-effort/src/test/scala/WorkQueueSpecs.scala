package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending

import sdo.core.domain.{EntityUuidIdField,  ListField}

import sdo.workEffort.domain.{Party, PartyAssignment, PartyRate, 
															PartyRole, ProcessorHasMoreWipThanAllowed, WorkEffort,
															WorkQueue}

class WorkQueueSpecs extends Specification {

	"The WorkQueue" should {

		val workQ = new WorkQueue( EntityUuidIdField())

		val party = new Party(){
			val _id= EntityUuidIdField()	
			override def id = _id
			override def actingAs: List[ PartyRole] = Nil
			override def withARateOf = new ListField[ PartyRate]()
			override def assignedTo=new ListField[ PartyAssignment]()
		}

		"add party as a processor" in {

			workQ.processors +=  party 

			workQ.processors.exists( _.id.value == party.id.value ) must beTrue
		}

		"add work effort to the queue" in {
			val workEffort =	WorkEffort()
			workQ.workInProgress += workEffort 

			workQ.workInProgress.exists( _.id.value == workEffort.id.value) must beTrue
		}

		"raise an error is a work effort is assigned to a party not assigned to the work queue" in {
			pending
		}

		"raise an error when a processor has been assigned more work in progress than allowed" in {
			workQ.processors +=  party 
			1 to 4 foreach { _ => workQ.workInProgress +=( WorkEffort()) }
			workQ.workInProgress.value.map( _.foreach( wip => wip.assignedTo += PartyAssignment( party)
			workQ.validationErrors must contain( ProcessorHasMoreWipThanAllowed( party, 4, 3))
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
