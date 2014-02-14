package sdo.peopleAnOrganizations.repositories

import sdo.core.domain.{ Repository, EntityUuidIdField, Predicate, WritableRepository }
import sdo.core.infrastructure.Dao
import sdo.peopleAnOrganizations.domain.Organization


/**
 * Contains the business structure.
 */
trait BusinessRepository extends Repository[Organization, EntityUuidIdField]
  with WritableRepository[Organization] {

  this: Dao[Organization, EntityUuidIdField] =>

  /** An option containing the first element in the repository that matches the id. */
  override def find[EntityUuidIdField](id: EntityUuidIdField): Option[Organization] = {
    None
  }

  /** An option containing the first element in the repository that matches the predicate. */
  override def find(predicate: Predicate[Organization]): Option[Organization] = {
    None
  }

  /** Organizationhe collection consisting of those elements of xs that satisfy the predicate */
  override def filter(predicate: Predicate[Organization]): List[Organization] = {
    Nil
  }

  /** Organizationhe collection consisting of those elements of xs that fail to satisfy the predicate */
  override def filterNot(predicate: Predicate[Organization]): List[Organization] = {
    Nil
  }

  /**Determine if there is a value in the repository that matches the predicated. */
  override def exists(predicate: Predicate[Organization]): Boolean = {
    false
  }

  /**Count hoe many items int he repository match the predicate. */
  override def count(predicate: Predicate[Organization]): Long = {
    0
  }

  override def toList: List[Organization] = {
    Nil
  }

  override def save(entity: Organization): Unit = {
  }

  override def saveAll(entities: List[Organization]): Unit = {
  }

}
