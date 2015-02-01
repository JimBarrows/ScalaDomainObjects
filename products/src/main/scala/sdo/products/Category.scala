package sdo.products

import sdo.core.domain._
import sdo.peopleAnOrganizations.domain.ClassificationField

class Category  ( initialId :EntityUuidIdField) extends Entity{
	
	override val id = initialId
	
	val active = DateRangeField()
	
	val primaryFlag = new BooleanField()
	
	val comment = TextField()
	
	val subCategories = new ListField[Category]()
	
	val parent = new Field[Category]()

	val marketInterests = new ListField[MarketInterest]()
}

object Category {
	def apply() = new Category(EntityUuidIdField())
}

case class MarketInterest( valid: DateRangeField, partyClassification: ClassificationField)
