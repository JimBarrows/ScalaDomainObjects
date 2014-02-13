package sdo.ecommerce.domain

import java.util.Locale

import sdo.core.domain.{Entity, Field, EntityUuidIdField, ListField, TextField, ShortTextField, ValueObject}
import sdo.peopleAndOrganizations.domain.Party

class UserLogin( initialId :EntityUuidIdField) extends Entity {

	override val id = initialId

	val username = ShortTextField()
	val password = PasswordField()
	val webAddress = WebAddressField()
	val accountStatus = AccountStatusField()
	val owner: Option[Party] = None

	val preferences = new ListField[ WebUserPreference[ _]] ()

	override def fieldList :List[ Field[ _]] = List( username, password, webAddress, accountStatus, preferences)

}

object UserLogin {

	def apply() = {
		val ul = new UserLogin( EntityUuidIdField())
		ul.setup
		ul
		}

	def apply( username :String, password :String, url :Url) = {
		val ul = new UserLogin( EntityUuidIdField())
		ul.setup
		ul.username.value =( username)
		ul.password.value =( password)
		ul.webAddress.url.value =( url)
		ul
		}

	def apply( initialId :EntityUuidIdField) = {
		val ul = new UserLogin( initialId)
		ul.setup
		ul
	}
}


class LocaleField extends Field[ Locale] {
	override def toString = "LocaleField( %s)".format( data)
}

object LocaleField {
	
	def apply( locale :Locale) = (new LocaleField value = locale).asInstanceOf[LocaleField]

}

class WebUserPreference[T <: Field[ _]] ( initialName :ShortTextField, initialValue :T) extends ValueObject {
	def name :ShortTextField = initialName
	def value :T = initialValue
	override def fieldList :List[ Field[ _]] = List( initialName, initialValue)
	override def toString = "WebUserPreference( name= %s, value= %s)".format( name, value)
}

class LocalePreference( name :ShortTextField, value :LocaleField) extends WebUserPreference[ LocaleField]( name, value) {
	override def toString = "LocalePreference( name= %s, value= %s)".format( name, value)
}

object LocalePreference {
	
	def apply( name :String, locale :Locale) = new LocalePreference( ShortTextField( name), LocaleField( locale)).asInstanceOf[LocalePreference]
}

