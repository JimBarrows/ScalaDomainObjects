package sdo.products

import sdo.core.domain._

class Product ( initialId :EntityUuidIdField) extends Entity {

	override val id = initialId

	val productId = new ProductId()
	
	val name = new ShortTextField()
	
	val introductionDate = new DateField()
	
	val salesDiscontinuationDate = new DateField()
	
	val supportDiscontinuationDate = new DateField()
	
	val comment = new TextField()
}

object Product {
	def apply() = new Product(EntityUuidIdField())
}

class Good ( initialId :EntityUuidIdField) extends Product(initialId) {
	
}

class Service ( initialId :EntityUuidIdField) extends Product(initialId) {
	
}