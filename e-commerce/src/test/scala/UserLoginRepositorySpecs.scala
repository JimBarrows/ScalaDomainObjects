package sdo.ecommerce.specs

import java.util.Locale

import scala.collection.mutable.MutableList
import org.specs2.mutable._

import org.apache.commons.codec.digest.DigestUtils._

import sdo.core.domain._
import sdo.ecommerce.domain._

class UserLoginRepositorySpecs extends Specification {

	"A User Login Repository" should {

		class UserLoginRepositoryFixture extends UserLoginRepository() {

			val userLogin =  new MutableList[UserLogin]()

			override def find[EntityUuidIdField]( id : EntityUuidIdField) :Option[UserLogin] = {
				userLogin.find( p=> p.id.equals( id))	
			}

			override def find( predicate :Predicate[UserLogin]) :Option[UserLogin] = {
				userLogin.find( predicate.isSatisfiedBy( _)	)
			}

			override def filter( predicate :Predicate[UserLogin]) :List[UserLogin] = {
				userLogin.filter( predicate.isSatisfiedBy( _)).toList
			}

			override def filterNot( predicate :Predicate[UserLogin]) :List[UserLogin] = {
				userLogin.filter( ! predicate.isSatisfiedBy( _)).toList	
			}

			override def exists( predicate :Predicate[UserLogin]) :Boolean = {
				userLogin.exists(  predicate.isSatisfiedBy( _))	
			}

			override def count( predicate :Predicate[UserLogin]) :Long = {
				userLogin.count(  predicate.isSatisfiedBy( _))	
			}

			override def toList :List[UserLogin] = {
				userLogin.toList
			}

			override def save( entity :UserLogin) : Unit = {
				userLogin += entity
			}

			override def saveAll( entities :List[UserLogin]) : Unit = {
				userLogin ++= entities
			}
	
		}


		"find a user login by username, password and web address for an active login" in {
			val repo = new UserLoginRepositoryFixture() 
			var ul = UserLogin( "user", "password", Url("http://localhost"))
			repo.save( ul)

			repo.find( new UsernamePasswordP("user", "password", Url("http://localhost"))) must beSome( ul)
		}

		"not find a user login by username, password and web address for an inactive login" in {
			val repo = new UserLoginRepositoryFixture() 
			var active = UserLogin( "user", "password", Url("http://localhost"))
			var inactive = UserLogin( "user2", "password2", Url("http://localhost"))
			inactive.accountStatus.value_=(AccountStatusType.inactive)
			repo.save( active)
			repo.save( inactive)

			repo.find( UserLoginRepository.activeUserLogin("user2", "password2", Url("http://localhost"))) must beNone
		}
	}
}

