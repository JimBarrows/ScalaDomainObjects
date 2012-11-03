package sdo.ecommerce.domain

import org.apache.commons.codec.digest.DigestUtils._

import sdo.core.domain.{Entity, Field, EntityUuidIdField, TextField, ShortTextField, ValueObject}

class UserLogin( initialId :EntityUuidIdField) extends Entity {

	override val id = initialId

	val username = ShortTextField()
	val password = PasswordField()
	val accountStatus = AccountStatusField()
	val preferences :List[ WebUserPreferences[ _]] = Nil

	override def fieldList :List[ Field[ _]] = List( username, password)
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
//			val encryptedVal :String = sha512Hex(  nv + salt)

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

class WebUserPreferences[T <: Field[ _]] ( name :ShortTextField, value :T) extends ValueObject {
	override def fieldList :List[ Field[ _]] = List( name, value)
}
