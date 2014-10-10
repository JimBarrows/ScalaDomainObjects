package sdo.peopleAnOrganizations.repositories

import sdo.core.utils.ReflectionUtils._

import sdo.core.domain.{ Repository, EntityUuidIdField, Predicate, WritableRepository }
import sdo.peopleAnOrganizations.domain._

/**
 * Contains the business structure.
 */
trait PartyRepository extends Repository[Party, EntityUuidIdField]
                          with WritableRepository[Party] {

  /** An option containing the first element in the repository that matches the id. */
  override def find[EntityUuidIdField](id: EntityUuidIdField): Option[Party] 

  /** An option containing the first element in the repository that matches the predicate. */
  override def find(predicate: Predicate[Party]): Option[Party] 

  /** Organizationhe collection consisting of those elements of xs that satisfy the predicate */
  override def filter(predicate: Predicate[Party]): List[Party] 

  /** Organizationhe collection consisting of those elements of xs that fail to satisfy the predicate */
  override def filterNot(predicate: Predicate[Party]): List[Party] 

  /**Determine if there is a value in the repository that matches the predicated. */
  override def exists(predicate: Predicate[Party]): Boolean 

  /**Count hoe many items int he repository match the predicate. */
  override def count(predicate: Predicate[Party]): Long 

  override def toList: List[Party] 

  override def save(entity: Party): Unit 

  override def saveAll(entities: List[Party]): Unit 

}

class RolePredicate(role: PartyRole) extends Predicate[ PartyRole] {
  
  override def isSatisfiedBy( candidate :PartyRole) = getType(role) =:=  getType(candidate)
}

object RolePredicate {
  def apply(role: PartyRole) = new RolePredicate( role)
}