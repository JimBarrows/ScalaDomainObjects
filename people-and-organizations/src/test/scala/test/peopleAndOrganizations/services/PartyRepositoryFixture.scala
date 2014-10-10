package test.peopleAndOrganizations.services

import org.specs2.mutable.Specification
import sdo.core.domain._
import sdo.peopleAnOrganizations.domain._
import sdo.peopleAnOrganizations.repositories._

class PartyRepositoryFixture extends PartyRepository {
			var list: List[Party] = Nil
			
			override def find[EntityUuidIdField](id: EntityUuidIdField): Option[Party] = list.find{p => p.id == id}

			override def find(predicate: Predicate[Party]): Option[Party]  = list.find { p=> predicate.isSatisfiedBy( p)}

			override def filter(predicate: Predicate[Party]): List[Party]  = list.filter {p=> predicate.isSatisfiedBy( p)}

			override def filterNot(predicate: Predicate[Party]): List[Party] =list.filter {p=> ! predicate.isSatisfiedBy( p)}

			override def exists(predicate: Predicate[Party]): Boolean = list.exists { p=> predicate.isSatisfiedBy( p)}

			override def count(predicate: Predicate[Party]): Long = list.count {p=> predicate.isSatisfiedBy(p)}

			override def toList: List[Party] = list

			override def save(entity: Party): Unit = list = list :+ entity

			override def saveAll(entities: List[Party]): Unit  = list = list ++ entities
		}