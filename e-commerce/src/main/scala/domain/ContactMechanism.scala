package sdo.ecommerce.domain

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField, ShortTextField, ValueObject}
import sdo._
import peopleAndOrganizations.domain.contactMechanisms._

class WebAddressField extends ElectronicAddressField {

	val url = new UrlField()

}

object WebAddressField {
	def apply() = {
		val wa = new WebAddressField()
		wa
	}
}
