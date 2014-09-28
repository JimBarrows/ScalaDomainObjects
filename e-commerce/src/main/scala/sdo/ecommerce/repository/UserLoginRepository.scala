package sdo.ecommerce.domain

import sdo.core.domain.AndPredicate
import sdo.core.domain.{Repository, WritableRepository, Predicate, EntityUuidIdField }

trait UserLoginRepository extends Repository[ UserLogin, EntityUuidIdField] with WritableRepository[ UserLogin] {
}

object UserLoginRepository {
	
	def activeUserLogin(username :String, password :String, url :Url) = 
		new AndPredicate[UserLogin]( 
			new UsernamePasswordP(username, password, url), 
			ActiveUserLoginP())

}


class UsernamePasswordP( username :String, password :String, url :Url) extends Predicate[ UserLogin] {

	val userLogin = UserLogin( username, password, url)

	override def isSatisfiedBy( candidate :UserLogin) =  candidate.username.equals( userLogin.username)
}

object UsernamePasswordP {

	def apply( username :String, password :String, url :Url) = new UsernamePasswordP( username, password, url)
}

import sdo.ecommerce.domain.AccountStatusType._

class ActiveUserLoginP  extends Predicate[ UserLogin] {
	override def isSatisfiedBy( candidate :UserLogin) =  candidate.accountStatus.equals( active)
}

object ActiveUserLoginP {
	def apply() = new ActiveUserLoginP()
}
