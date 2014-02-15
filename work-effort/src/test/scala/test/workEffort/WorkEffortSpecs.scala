package test.workEffort

import org.specs2.mutable.Specification
import scalaz.Scalaz._
import sdo.workEffort.domain.WorkEffort
import sdo.workEffort.domain.Party
import sdo.core.domain.EntityUuidIdField
import sdo.core.domain.ListField
import sdo.workEffort.domain.PartyRole
import sdo.workEffort.domain.PartyRate
import sdo.workEffort.domain.PartyAssignment

class WorkEffortSpecs extends Specification {

  "The work effort class" should {

    "allow a person to be assigned to it" in {
      val workEffort = WorkEffort()      
      workEffort.assignTo(party);
      val pa = PartyAssignment( workEffort, party)
      workEffort.assignedTo.list must contain( pa)
      party.assignedTo.list must contain (pa)
    }
   
  }
   val party = new Party {
      
      val _id = EntityUuidIdField()
      
      val _actingAs = new ListField[PartyRole]()     
      
      def id = _id

      def actingAs = _actingAs

    }
}