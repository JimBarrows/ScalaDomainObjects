package sdo.ecommerce.repositories

import sdo.core.domain.AndPredicate
import sdo.core.domain.{Repository, WritableRepository, Predicate, EntityUuidIdField }
import sdo.ecommerce.domain.{UserLogin, Url}

class UserLoginRepository extends Repository[ UserLogin, EntityUuidIdField] with WritableRepository[ UserLogin] {

	override def find[EntityUuidIdField]( id : EntityUuidIdField) :Option[UserLogin] = {
		None
	}

	override def find( predicate :Predicate[UserLogin]) :Option[UserLogin] = {
		None
	}

	/** The collection consisting of those elements of xs that satisfy the predicate */
	override def filter( predicate :Predicate[UserLogin]) :List[UserLogin] = {
		Nil
	}

	/** The collection consisting of those elements of xs that fail to satisfy the predicate */
	override def filterNot( predicate :Predicate[UserLogin]) :List[UserLogin] = {
		Nil
	}

	/**Determine if there is a value in the repository that matches the predicated. */
	override def exists( predicate :Predicate[UserLogin]) :Boolean = {
		false
	}

	/**Count hoe many items int he repository match the predicate. */
	override def count( predicate :Predicate[UserLogin]) :Long = {
		0
	}

	override def toList :List[UserLogin] = {
		Nil
	}

	def save( entity :UserLogin) : Unit = {
	}

	override def saveAll( entities :List[UserLogin]) : Unit = {
	}

}

object UserLoginRepository {
	
	def apply = new UserLoginRepository()

	def activeUserLogin(username :String, password :String, url :Url) = new AndPredicate[UserLogin]( new UsernamePasswordP(username, password, url), ActiveUserLoginP())

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
