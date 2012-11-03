package sdo.ecommerce.specs

import org.specs2.mutable._

import org.scala_tools.time.Imports._

import org.apache.commons.codec.digest.DigestUtils._

import sdo.ecommerce.domain.UserLogin
import sdo.ecommerce.domain.AccountStatusType._
class UserLoginSpecs extends Specification {

	"A User Login" should {
		"Accept a new username password on creation" in {
			val ul = UserLogin( "username", "password")
			ul.username.value must beSome( "username")
			ul.password.value must beSome
		}

		"encrypt the password" in {
			val ul = UserLogin( "username", "password")
			val salt = "r_Z4$ko-NdxM[_S?:Zgwz hdOfO9={"
			val encryptedVal :String = sha512Hex( "password" + salt)
			ul.password.value must beSome( encryptedVal) 
		}

		"start in unverified status when created" in {
			val ul = UserLogin( "username", "password")
			ul.accountStatus.value must beSome( unverified)
		}


	}
}
