package sdo.workEffort.domain
import org.joda.time.Duration
import sdo.core.domain.{ DateField, DateRangeField, DateTimeField, Entity, EntityUuidIdField, IntegerField, ListField, MoneyField, TextField }
import sdo.core.domain.ValueObject
import sdo.core.domain.Field

class WorkEffort(initialId: EntityUuidIdField) extends Entity {

  override val id = initialId

  val name = TextField
  val description = TextField
  val scheduledStartDate = DateField
  val scheduledCompletionDate = DateField
  val totalAmountAllowed = MoneyField
  val estimate = EstimateField
  val actualStartDateTime = new DateTimeField()
  val actualCompletionDateTime = new DateTimeField()
  val specialTerms = TextField
  val performedAt: Option[Facility] = None
  val fulfillmentOf: List[Requirement] = Nil
  val redoneVia: List[WorkEffort] = Nil
  val versionOf: Option[WorkEffort] = None
  val assignedTo = new ListField[PartyAssignment]
  val status = StatusListField
  val trackedVia = TimeEntryListField
  def actualHours: Option[Duration] = if (actualStartDateTime.value.isEmpty || actualCompletionDateTime.value.isEmpty) {
    None
  } else {
    Some(new Duration(actualStartDateTime.value.get, actualCompletionDateTime.value.get))
  }
  
  def assignTo( party: Party) = {
    
    val pa = PartyAssignment(this, party)
    party.assignedTo += pa    
    assignedTo.add(pa)
        
    pa
  }
}

object WorkEffort {

  def apply() = new WorkEffort(EntityUuidIdField())
}

class StatusListField extends ListField[Status]

object StatusListField {
  def apply = new StatusListField()
}

class PartyAssignment extends ValueObject {
  val partyAssigned = new Field[Party]
  val assignment = new Field[WorkEffort]
  val effective: DateRangeField = DateRangeField() 
  val comment: TextField = TextField()   
  val rate: Option[AssignmentRate] = None
}

object PartyAssignment {
  
  def apply(assignment: WorkEffort, assignedTo: Party) = {
    val pa = new PartyAssignment
    pa.assignment.value = assignment
    pa.partyAssigned.value = assignedTo
    pa
  }
}

class ProjectManager extends PartyAssignment

class TeamMember extends PartyAssignment

case class Performer  extends PartyAssignment

class Status(at: DateTimeField)

case class ToDo(at: DateTimeField)
case class Doing(at: DateTimeField)
case class Done(at: DateTimeField)

class Program(initialId: EntityUuidIdField) extends WorkEffort(initialId)
class Phase(initialId: EntityUuidIdField) extends WorkEffort(initialId)
class Project(initialId: EntityUuidIdField) extends WorkEffort(initialId)
class Task(initialId: EntityUuidIdField) extends WorkEffort(initialId)
class Activity(initialId: EntityUuidIdField) extends WorkEffort(initialId)
class ProductionRun(initialId: EntityUuidIdField) extends WorkEffort(initialId) {
  val quantityToProduce = IntegerField
  val quantityProduced = IntegerField
  val quantityRejected = IntegerField
}
class Maintenance(initialId: EntityUuidIdField) extends WorkEffort(initialId)
class WorkFlow(initialId: EntityUuidIdField) extends WorkEffort(initialId)
class Research(initialId: EntityUuidIdField) extends WorkEffort(initialId)

case class OrderRequirementCommitment(usageOf: OrderItem, commitmentOf: Requirement, quantity: Integer)

class Association(from: WorkEffort, to: WorkEffort, effective: DateRangeField)

case class Breakdown(from: WorkEffort, to: WorkEffort, effective: DateRangeField)
  extends Association(from, to, effective)

class Dependency(from: WorkEffort, to: WorkEffort, effective: DateRangeField)
  extends Association(from, to, effective)

class Precedency(from: WorkEffort, to: WorkEffort, effective: DateRangeField) extends Dependency(from, to, effective)

class Concurrency(from: WorkEffort, to: WorkEffort, effective: DateRangeField) extends Dependency(from, to, effective)

