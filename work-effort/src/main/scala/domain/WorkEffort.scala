package sdo.workEffort.domain

import org.joda.time.Hours
import sdo.core.domain.{DateField,
												DateRangeField,
												DateTimeField,
												Field,
												Entity,
												EntityUuidIdField,
												IntegerField,
												ListField,
												MoneyField,
												TextField}


class WorkEffort(initialId: EntityUuidIdField) extends Entity {

	override val id = initialId

	val name = TextField
	val description = TextField
	val scheduledStartDate = DateField
	val scheduledCompletionDate = DateField
	val totalAmountAllowed = MoneyField
	val totalHoursAllowed = HoursField
	val estimatedHours = HoursField
	val actualStartDateTime = DateTimeField
	val actualCompletionDateTime = DateTimeField
	val actualHours = HoursField
	val specialTerms = TextField
	val performedAt: Option[Facility] = None
	val fulfillmentOf: List[Requirement] = Nil
	val redoneVia: List[WorkEffort] = Nil
	val versionOf: Option[WorkEffort] = None
	val status = StatusListField 
	val trackedVia = TimeEntryListField
}

class StatusListField extends ListField[ Status]

object StatusListField {
	def apply = new StatusListField()
}

class PartyAssignment( effective: DateRangeField, comment: TextField, assignedTo: Party)


case class ProjectManager( effective: DateRangeField, comment: TextField, assignedTo: Party ) 
	extends PartyAssignment( effective, comment, assignedTo)

case class TeamMember( effective: DateRangeField, comment: TextField, assignedTo: Party ) 
	extends PartyAssignment( effective, comment, assignedTo)

class Status( at: DateTimeField)

case class ToDo( at: DateTimeField)
case class Doing( at: DateTimeField)
case class Done( at: DateTimeField)

class Program(initialId: EntityUuidIdField) extends WorkEffort (initialId)
class Phase(initialId: EntityUuidIdField) extends WorkEffort (initialId)
class Project(initialId: EntityUuidIdField) extends WorkEffort (initialId)
class Task(initialId: EntityUuidIdField) extends WorkEffort (initialId)
class Activity(initialId: EntityUuidIdField) extends WorkEffort (initialId)
class ProductionRun(initialId: EntityUuidIdField) extends WorkEffort (initialId) {
	val quantityToProduce = IntegerField
	val quantityProduced = IntegerField
	val quantityRejected = IntegerField
}
class Maintenance(initialId: EntityUuidIdField) extends WorkEffort (initialId)
class WorkFlow(initialId: EntityUuidIdField) extends WorkEffort (initialId)
class Research(initialId: EntityUuidIdField) extends WorkEffort (initialId)

class HoursField extends Field[Hours]

object HoursField {
	def apply = new HoursField()
}

trait OrderItem {
	def seqId: IntegerField
	def estimatedDeliveryDate: DateField
	def quantity: IntegerField
	def unitPrice: MoneyField
	def shippingInstructions: TextField
	def comment: TextField
	def itemDescription: TextField
	val fulfillerOf = new ListField[OrderRequirementCommitment]()
}

case class OrderRequirementCommitment( usageOf: OrderItem, commitmentOf: Requirement, quantity: Integer)


class Association( from: WorkEffort, to: WorkEffort, effective: DateRangeField)

case class Breakdown ( from: WorkEffort, to: WorkEffort, effective: DateRangeField) 
	extends Association( from, to, effective)

class Dependency ( from: WorkEffort, to: WorkEffort, effective: DateRangeField) 
	extends Association( from, to, effective)

class Precedency ( from: WorkEffort, to: WorkEffort, effective: DateRangeField) extends
	Dependency( from, to, effective)

class Concurrency ( from: WorkEffort, to: WorkEffort, effective: DateRangeField) extends
	Dependency( from, to, effective)


