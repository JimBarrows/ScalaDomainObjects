package sdo.workEffort.domain

import sdo.core.domain.{DateField,
												Entity, 
												EntityUuidIdField, 
												Field,
												IntegerField,
												ListField,
												MoneyField,
												TextField}

class Requirement(initialId: EntityUuidIdField) extends Entity {

	override val id = initialId

	val description = TextField

	val creationDate = DateField

	val requiredBy = DateField

	val estimatedBudget = MoneyField

	val quantity = IntegerField

	val reason = TextField

	val composedOf: List[Requirement] = Nil

	val partOf: Option[Requirement] = None
	setup
	
}

class CustomerRequirement(initialId: EntityUuidIdField) extends Requirement( initialId)
class InternalRequirement(initialId: EntityUuidIdField) extends Requirement( initialId)
class ProductRequirement(initialId: EntityUuidIdField) extends Requirement( initialId)
class WorkRequirement(initialId: EntityUuidIdField) extends Requirement( initialId) {

	val workToProduce = Deliverable
	val relatedTo:  Option[FixedAsset] = None
	val produce:  Option[Product] = None
}

class Deliverable(initialId: EntityUuidIdField) extends Entity {

	override val id = initialId
}

object Deliverable {
	def apply = new Deliverable(EntityUuidIdField())
}

trait FixedAsset{

	def id: EntityUuidIdField

	def name: TextField

	def dateAquired: DateField
	def dateLastServiced: DateField
	def dateNextService: DateField
	def productionCapacity: IntegerField
}

trait Product {

	def id: EntityUuidIdField

	def name: TextField

	def introductionDate: DateField

	def salesDiscontinuationDate: DateField

	def supportDiscontinuationDate: DateField

	def comment: TextField
}

