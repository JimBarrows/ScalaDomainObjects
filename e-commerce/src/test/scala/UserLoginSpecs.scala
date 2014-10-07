package sdo.ecommerce.specs

import java.util.Locale

import org.specs2.mutable._

import org.apache.commons.codec.digest.DigestUtils._

import sdo.ecommerce.domain._

import sdo.ecommerce.domain.AccountStatusType._


class UserLoginSpecs extends Specification {

	"A User Login" should {
		"Accept a new username password on creation" in {
			val ul = UserLogin( "username", "password", LocalHost80)
			ul.username.value must beSome( "username")
			ul.password.value must beSome
		}

		"encrypt the password" in {
			val ul = UserLogin( "username", "password", LocalHost80)
			val salt = "r_Z4$ko-NdxM[_S?:Zgwz hdOfO9={"
			val encryptedVal :String = sha512Hex( "password" + salt)
			ul.password.value must beSome( encryptedVal) 
		}

		"start in unverified status when created" in {
			val ul = UserLogin( "username", "password", LocalHost80)
			ul.accountStatus.value must beSome( unverified)
		}

		"can have preferences added to it" in {
			val ul = UserLogin( "username", "password", LocalHost80)
			val wupf:LocalePreferenceField = LocalePreferenceField("language", Locale.US).getOrElse(new LocalePreferenceField()).asInstanceOf[LocalePreferenceField] 
			ul.preferences_+=( wupf)
			ul.preferences.list must contain( wupf)
		}

		"can retrieve a preference" in {
			val ul = UserLogin( "username", "password", LocalHost80)			
			val wupf = LocalePreferenceField("language", Locale.US).getOrElse(new LocalePreferenceField()).asInstanceOf[LocalePreferenceField]
			val wupf2 = LocalePreferenceField("currency", Locale.CANADA).getOrElse( new LocalePreferenceField()).asInstanceOf[LocalePreferenceField]
			
			ul.preferences_+=( wupf)
			ul.preferences.+=( wupf2)
			val expectedName:String = wupf.value.getOrElse( LocalePreference("", null)).name
			ul.preferences.find( p => {
				p.value.getOrElse( LocalePreference("", null)).asInstanceOf[LocalePreference].name == "language"
			}) must beSome( wupf)
		}

		"be for a single web address" in {
			val ul = UserLogin( "username", "password", LocalHost80)
			ul.webAddress.url.value must beSome(LocalHost80)
		}


	}
}
