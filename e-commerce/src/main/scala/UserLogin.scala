package sdo.ecommerce.domain

import sdo.core.domain.{Entity, Field, EntityUuidIdField, ShortTextField}

class UserLogin( initialId :EntityUuidIdField) extends Entity {

	override val id = initialId

	val username = new ShortTextField
	val password = new PasswordField

	override def fieldList :List[ Field[ _]] = List( username, password)
}

object UserLogin {

	def apply() = {
		val ul = new UserLogin( EntityUuidIdField())
		ul.setup
		}

	def apply( initialId :EntityUuidIdField) {
		val ul = new UserLogin( initialId)
		ul.setup
		ul
	}
}

class PasswordField extends ShortTextField {
}

object PasswordField {

	def apply( text :String) = new PasswordField value = text
}
