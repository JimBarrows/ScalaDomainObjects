package sdo.ecommerce.specs

import java.util.Locale

import org.specs2.mutable._

import org.apache.commons.codec.digest.DigestUtils._

import sdo.core.domain._
import sdo.ecommerce.domain._

class UserLoginRepositorySpecs extends Specification {

	"A User Login Repository" should {

		val repo = new UserLoginRepository()

		class LoginPredicate( username :String, password :String, url :Url) extends Predicate[UserLogin] {

			override def isSatisfiedBy( candidate :UserLogin) = candidate.username.equals(username) && candidate.password.equals(password) && candidate.webAddress.equals( url)
			}

		"find a user login by username, password and web address for an active login" in {
			var ul = UserLogin( "user", "password", Url("http://localhost"))
			repo.save( ul)

			repo.find( new LoginPredicate("user", "password", Url("http://localhost"))) must beSome( ul)
		}

		"not find a user login by username, password and web address for an inactive login" in {
			var ul = UserLogin( "user", "password", Url("http://localhost"))
			repo.save( ul)

			repo.find( new LoginPredicate("user", "password", Url("http://localhost"))) must beNone
		}
	}
}

