package sdo.ecommerce.domain

import java.util.Locale
import scalaz._
import std.AllInstances._
import std.list._
import Scalaz._
import sdo.core.domain.ValidationMethods._
import sdo.core.domain._
import sdo.peopleAnOrganizations.domain.Party

class UserLogin( initialId :EntityUuidIdField) extends Entity {

	override val id = initialId

	val username = ShortTextField()
	val password = PasswordField()
	val webAddress = WebAddressField()
	val accountStatus = AccountStatusField()
	val owner: Option[Party] = None

	val preferences = new ListField[ WebUserPreferenceField[_]] ()

	def preferences_+= (preference: LocalePreferenceField) =
		if( preference != null)  {						
			preferences.add( preference)
		}
	

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

class WebUserPreference[+T]( name: String, value: T)
case class LocalePreference (name: String, locale: Locale) extends WebUserPreference[Locale](name, locale)

class WebUserPreferenceField[ T ] extends Field[T]{
	
	override def toString = "WebUserPreference=(%s)".format( value)
}

class LocalePreferenceField extends WebUserPreferenceField[LocalePreference] {
	import LocalePreferenceField._
	override def validations :List[Option[LocalePreference] => Validation[FieldError, Option[LocalePreference]]]  =  List(nameAndLocaleCantBeEmptyOrNull _)
 }

object LocalePreferenceField {
	
	def apply( name :String, locale :Locale) = {
		val field = new LocalePreferenceField()		
		field.value =( LocalePreference(name, locale))
		field
	}

	def nameAndLocaleCantBeEmptyOrNull( localPreference: Option[LocalePreference]): Validation[FieldError, Option[LocalePreference]]  = {
		localPreference.map( lp =>{
			if(lp.name == null || lp.locale == null){
				CannotBeNull.fail[Option[LocalePreference]]
			} else if (lp.name == "") {
				CannotBeEmpty.fail[Option[LocalePreference]]
			} else {
				localPreference.success[FieldError]
			}
		}).getOrElse( localPreference.success[FieldError]).asInstanceOf[ Validation[FieldError, Option[LocalePreference]]]
	}
}

