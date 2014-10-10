package sdo.peopleAndOrganizations.services

import scala.reflect.runtime.universe._

import sdo.core.domain._
import sdo.core.utils.ReflectionUtils._
import sdo.peopleAnOrganizations.domain._
import sdo.peopleAnOrganizations.repositories._

/**These are services related to the business owning the data.
*/
trait BusinessServices {

	def partyRepository():PartyRepository

	def create( name:TextField) = {
		val lo = new LegalOrganization(EntityUuidIdField())
		lo.name.value = name.value.getOrElse("")
		lo.roles += new ParentOrganization()
		lo.roles += new InternalOrganization()
		partyRepository save lo
		lo
	}

	def business() = partyRepository.find( businessQuery)

	object InternalOrganizationQuery extends Predicate[Organization] {
		override def isSatisfiedBy( candidate :Organization) = {			
			candidate.roles.exists { role => 
				role match {
					case r : InternalOrganization => true
					case _ => false
				}				
			}
		}
	}

	object ParentOrganizationQuery extends Predicate[Organization] {
		override def isSatisfiedBy( candidate :Organization) = {			
			candidate.roles.exists { role => 				
				role match {
					case r : ParentOrganization => true
					case _ => false
				}				
			}
		}
	}

	val businessQuery = new AndPredicate[Organization]( InternalOrganizationQuery, ParentOrganizationQuery)
}
