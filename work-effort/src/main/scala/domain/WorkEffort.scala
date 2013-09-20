package sdo.workEffort.domain

import org.joda.time.Hours
import sdo.core.domain.{DateField,
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
}

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

trait Facility

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
