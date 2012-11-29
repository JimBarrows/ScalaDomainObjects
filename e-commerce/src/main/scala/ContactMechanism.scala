package sdo.ecommerce.domain

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField, ShortTextField, ValueObject}
import sdo._
import peopleAndOrganizations._

class WebAddress extends ElectronicAddress {

	val url = new UrlField()

	override def fieldList = super.fieldList ::: url :: Nil
}

object WebAddress {
	def apply() = {
		val wa = new WebAddress()
		wa.setup
		wa
	}
}
