package sdo.specs

import org.specs2.mutable._
import sdo.core.domain.{Entity, EntityUuidIdField, NumericField, Repository, Predicate, WritableRepository,DestructibleRepository}

class RepositorySpecs extends Specification {
	
	"A repository" should {

		val repo = new Repo

		"find an entity via id" in {
			repo.find( te2.id) must beSome.which( _.id==te2.id)
		}

		"find none if an enitty with the id isn't found" in {
			repo.find( EntityUuidIdField) must beNone
		}

		"return the first entity that meets a predicate" in {

			repo.find( fidnNumericEqualTo2) must beSome.which( _.id == te2.id)
			
		}

		"return a list of entities that meets a predicate" in {

			repo.filter( fidnNumericEqualTo2) must contain (te2, te3)
		}

		"return a list of entities that do not satisfy a predicate" in {

			repo.filterNot( fidnNumericEqualTo2) must contain (te1)
		}

		"determine if at least one entity satisfied a predicate" in {
			repo.exists( fidnNumericEqualTo2) must beTrue
		}

		"count how many entities satisfy a predicate" in {
			repo.count( fidnNumericEqualTo2) must_== 2
		}
	}


	"A writable repository" should {
		val writableRepo = new WritableRepo
		"save an entity to the repository [note: updating an entity is up to the implementation, not the trait]" in {
			writableRepo.save( te4)
			writableRepo.exists( findNumericEqualTo5) must beTrue
		}

		"save a list of entities to the repository" in {
			writableRepo.saveAll( te5 :: te6 :: Nil)
			writableRepo.filter( findNumericEqualTo6) must contain( te5, te6)
		}
	}

	"A desctructible repository" should {
		val destructibleRepo = new DesctructibleRepo 
		"remove an entity from the repo" in {
			destructibleRepo.remove( te1)
			destructibleRepo.exists( new Predicate[TestEntity] {
				override def isSatisfiedBy( candidate :TestEntity) = {
					candidate.id == te1.id
				}
			}) must beFalse
		}
		"remove a list of entities from the repo" in {
			destructibleRepo.removeAll( te1 :: te2 :: Nil)
			destructibleRepo.exists( new Predicate[TestEntity] {
				override def isSatisfiedBy( candidate :TestEntity) = {
					candidate.id == te1.id || candidate.id == te2.id
				}
			}) must beFalse
		}
	}

	class TestEntity( initNumeric :NumericField)  extends Entity {
		val id = EntityUuidIdField()
		val numeric =  initNumeric
	}

	val te1 = new TestEntity( NumericField(1))
	val te2 = new TestEntity( NumericField(2))
	val te3 = new TestEntity( NumericField(2))
	val te4 = new TestEntity( NumericField(5))
	val te5 = new TestEntity( NumericField(6))
	val te6 = new TestEntity( NumericField(6))

	val fidnNumericEqualTo2 = new Predicate[TestEntity] () {
		override def isSatisfiedBy( candidate :TestEntity) = {
			candidate.numeric ==  NumericField(2)
		}
	}

	val findNumericEqualTo5 = new Predicate[TestEntity]() {
		override def isSatisfiedBy( candidate :TestEntity) = {
			candidate.numeric ==  NumericField(5)
		}
	}

	val findNumericEqualTo6 = new Predicate[TestEntity]() {
		override def isSatisfiedBy( candidate :TestEntity) = {
			candidate.numeric ==  NumericField(6)
		}
	}

	class Repo extends Repository[TestEntity] {

		var list :List[TestEntity] = te1 :: te2 :: te3 :: Nil

		override def find[ EntityUuidIdField]( id :EntityUuidIdField) :Option[TestEntity] = list.find( _.id == id)

		override def find( predicate :Predicate[TestEntity]) = list.find( predicate.isSatisfiedBy( _))

		override def filter( predicate :Predicate[TestEntity]) = list.filter( predicate.isSatisfiedBy( _))

		override def filterNot( predicate :Predicate[TestEntity]) = list.filterNot( predicate.isSatisfiedBy( _))

		override def exists( predicate :Predicate[TestEntity])  = list.exists( predicate.isSatisfiedBy( _))

		override def count( predicate :Predicate[TestEntity])  = list.count( predicate.isSatisfiedBy( _))

		override def toList :List[TestEntity] = list

	}

	class WritableRepo extends Repository[TestEntity] with WritableRepository[TestEntity] {

		var list :List[TestEntity] = te1 :: te2 :: te3 :: Nil

		override def save( entity :TestEntity) :Unit = {
			list = entity :: list
			}

		override def saveAll( entityList :List[TestEntity]) :Unit = list = list ++ entityList

		override def find[ EntityUuidIdField]( id :EntityUuidIdField) :Option[TestEntity] = list.find( _.id == id)

		override def find( predicate :Predicate[TestEntity]) = list.find( predicate.isSatisfiedBy( _))

		override def filter( predicate :Predicate[TestEntity]) = list.filter( predicate.isSatisfiedBy( _))

		override def filterNot( predicate :Predicate[TestEntity]) = list.filterNot( predicate.isSatisfiedBy( _))

		override def exists( predicate :Predicate[TestEntity])  = list.exists( predicate.isSatisfiedBy( _))

		override def count( predicate :Predicate[TestEntity])  = list.count( predicate.isSatisfiedBy( _))

		override def toList :List[TestEntity] = list

	}
	
	class DesctructibleRepo extends Repository[TestEntity] with DestructibleRepository[TestEntity] {

		var list :List[TestEntity] = te1 :: te2 :: te3 :: te4 :: te5 :: te6 :: Nil

		override def remove( entity :TestEntity) :Unit = {
			list =  list.filterNot( e => e.id == entity.id)
		}

		override def removeAll( entityList :List[TestEntity]) :Unit = {
			list = list.filterNot( e => entityList.exists( e))
		}

		override def find[ EntityUuidIdField]( id :EntityUuidIdField) :Option[TestEntity] = list.find( _.id == id)

		override def find( predicate :Predicate[TestEntity]) = list.find( predicate.isSatisfiedBy( _))

		override def filter( predicate :Predicate[TestEntity]) = list.filter( predicate.isSatisfiedBy( _))

		override def filterNot( predicate :Predicate[TestEntity]) = list.filterNot( predicate.isSatisfiedBy( _))

		override def exists( predicate :Predicate[TestEntity])  = list.exists( predicate.isSatisfiedBy( _))

		override def count( predicate :Predicate[TestEntity])  = list.count( predicate.isSatisfiedBy( _))

		override def toList :List[TestEntity] = list

	}
}
