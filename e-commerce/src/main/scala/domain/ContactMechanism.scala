package sdo.ecommerce.domain

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField, ShortTextField, ValueObject}
import sdo._
import peopleAndOrganizations.domain.contactMechanisms._

class WebAddress extends ElectronicAddress {

	val url = new UrlField()

}

object WebAddress {
	def apply() = {
		val wa = new WebAddress()
		wa
	}
}
