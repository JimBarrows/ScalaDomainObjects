package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending

import sdo.core.domain.{EntityUuidIdField,  ListField}

import sdo.workEffort.domain.{Party, PartyRate, PartyRole, WorkQueue}

class WorkQueueSpecs extends Specification {

	"The WorkQueue" should {

		"add party to as processors" in {
			val workQ = new WorkQueue( EntityUuidIdField())

			val party = new Party(){
				val _id= EntityUuidIdField()	
				override def id = _id
				override def actingAs: List[ PartyRole] = Nil
				override def withARateOf = new ListField[ PartyRate]()
				override def assignedTo=new ListField[ WorkQueue]()
			}

			workQ.processors +=  party 

			workQ.processors.exists( _.id.value == party.id.value ) must beTrue
		}

	}
}
