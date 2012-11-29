package sdo.ecommerce.domain

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField, ShortTextField, ValueObject}

class UrlField extends Field[ Url] {
}

object UrlField {
	def apply() = new UrlField()
}
