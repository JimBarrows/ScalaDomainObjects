package sdo.ecommerce.domain

import java.util.Locale

import org.apache.commons.codec.digest.DigestUtils._

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField, ShortTextField, ValueObject}

class UserLogin( initialId :EntityUuidIdField) extends Entity {

	override val id = initialId

	val username = ShortTextField()
	val password = PasswordField()
	val accountStatus = AccountStatusField()
	private var _preferences :List[ WebUserPreference[ _]] = Nil

	override def fieldList :List[ Field[ _]] = List( username, password)

	def preferences =  _preferences

	def add( wup :WebUserPreference[ _]) {_preferences  = wup :: _preferences}

	def find( name :String) :Option[WebUserPreference[ _]] = _preferences.find( p => p.name.value == Some(name)) 

	def change( newValue :WebUserPreference[ _]) ={
		val oldValue = find( newValue.name.value.getOrElse(""))
		_preferences = _preferences diff List( oldValue)
		add( newValue)
	}
}

object UserLogin {

	def apply() = {
		val ul = new UserLogin( EntityUuidIdField())
		ul.setup
		}

	def apply( username :String, password :String) = {
		val ul = new UserLogin( EntityUuidIdField())
		ul.setup
		ul.username.value_=( username)
		ul.password.value_=( password)
		ul
		}

	def apply( initialId :EntityUuidIdField) {
		val ul = new UserLogin( initialId)
		ul.setup
		ul
	}
}

class PasswordField extends TextField {

	override def assign( newValue :Option[ String]) :Field[ String] = {
		if (! data.equals( newValue) && (writable_? || ! initialized_? )) {

			val salt = "r_Z4$ko-NdxM[_S?:Zgwz hdOfO9={"

			data = newValue.map( nv => sha512Hex( nv + salt))
			validate
			makeDirty
			change0.fire( newValue.get)
		} 
		this
	}

	
}

object PasswordField {

	def apply( text :String) = new PasswordField value = text
	def apply( ) = new PasswordField
}

/**When an account is first created, it hasn't been verified yet.  From the unverified state it can move to
either the active or inactive state.  Active state can only move to inactive.  Inactive can move to active
according to business rules.*/
object AccountStatusType extends Enumeration {
	type AccountStatusType = Value
	val unverified, active, inactive = Value
}

import AccountStatusType._

class AccountStatusField extends Field[ AccountStatusType] {
}

object AccountStatusField {
	
	def apply() = {
		val asv = new AccountStatusField
		asv.value = Some( unverified)
		asv
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
