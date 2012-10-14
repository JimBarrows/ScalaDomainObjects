package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending
import org.scalastuff.scalabeans.Preamble._
import sdo.core.domain.{Entity, Field, BooleanField, Predicate, AndPredicate, OrPredicate, NotPredicate, EntityUuidIdField }
import sdo.core.domain.{MustBeNumeric, OnlyOneFieldCanHaveValue}
import sdo.core.domain.EntityValidationMethods.onlyOneHasValue

class PredicateSpecs extends Specification {

	class PredicateEntity( initialId :EntityUuidIdField) extends Entity {

		override def descriptor = descriptorOf[PredicateEntity]

		//val id = initialId
		val satisfy = new BooleanField()

		override lazy val fieldList :List[Field[_]] = {
			this.satisfy :: Nil
		}
	}



	class TruePredicate extends Predicate[PredicateEntity]{
		override def isSatisfiedBy( candidate: PredicateEntity) = candidate.satisfy.value.getOrElse(false)
	}

	class FalsePredicate extends Predicate[PredicateEntity]{
		override def isSatisfiedBy( candidate: PredicateEntity) = ! candidate.satisfy.value.getOrElse(false)
	}

	val ts = new TruePredicate()
	val fs = new FalsePredicate()

	val trueEntity = new PredicateEntity( EntityUuidIdField()) 
	val falseEntity = new PredicateEntity( EntityUuidIdField()) 

	trueEntity.satisfy.value_=( true)
	falseEntity.satisfy.value_=( false)

	"The Predicate trait" should {
		"determine if an object is a candidate for this predicate" in {
			ts.isSatisfiedBy( trueEntity) must 	beTrue

		}
		"logically and two predicates together" in {
			new AndPredicate( ts, ts).isSatisfiedBy( trueEntity) must beTrue 
			new AndPredicate( ts, fs).isSatisfiedBy( trueEntity) must beFalse 
			new AndPredicate( fs, ts).isSatisfiedBy( trueEntity) must beFalse 
			new AndPredicate( fs, fs).isSatisfiedBy( trueEntity) must beFalse 
			new AndPredicate( fs, fs).isSatisfiedBy( falseEntity) must beTrue 
		}
		"logically or two predicates together" in {
			new OrPredicate( ts, ts).isSatisfiedBy( trueEntity) must beTrue 
			new OrPredicate( ts, fs).isSatisfiedBy( trueEntity) must beTrue 
			new OrPredicate( fs, ts).isSatisfiedBy( trueEntity) must beTrue 
			new OrPredicate( fs, fs).isSatisfiedBy( trueEntity) must beFalse 
			new OrPredicate( fs, fs).isSatisfiedBy( falseEntity) must beTrue 
		}
		"logically not two predicates together" in {
			new AndPredicate( ts, new NotPredicate(fs)).isSatisfiedBy( trueEntity) must beTrue 
		}
	}
}
