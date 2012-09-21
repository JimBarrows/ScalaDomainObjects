package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending
import org.scalastuff.scalabeans.Preamble._
import sdo.core.domain.{Entity, Field, BooleanField, Specification=>EntitySpecification, AndSpecification, OrSpecification, NotSpecification }
import sdo.core.domain.{MustBeNumeric, OnlyOneFieldCanHaveValue}
import sdo.core.domain.EntityValidationMethods.onlyOneHasValue

class SpecificationSpecs extends Specification {

	class SpecificationEntity extends Entity {

		override def descriptor = descriptorOf[SpecificationEntity]

		val satisfy = new BooleanField()

		override lazy val fieldList :List[Field[_]] = {
			this.satisfy :: Nil
		}
	}


	class TrueSpecification extends EntitySpecification[SpecificationEntity] {
		override def isSatisfiedBy( candidate: SpecificationEntity) = candidate.satisfy.value.getOrElse(false)
	}

	class FalseSpecification extends EntitySpecification[SpecificationEntity] {
		override def isSatisfiedBy( candidate: SpecificationEntity) = ! candidate.satisfy.value.getOrElse(false)
	}

	val ts = new TrueSpecification()
	val fs = new FalseSpecification()

	val trueEntity = new SpecificationEntity 
	val falseEntity = new SpecificationEntity 

	trueEntity.satisfy.value_=( true)
	falseEntity.satisfy.value_=( false)

	"The Specification trait" should {
		"determine if an object is a candidate for this specification" in {
			ts.isSatisfiedBy( trueEntity) must 	beTrue

		}
		"logically and two specifications together" in {
			new AndSpecification( ts, ts).isSatisfiedBy( trueEntity) must beTrue 
			new AndSpecification( ts, fs).isSatisfiedBy( trueEntity) must beFalse 
			new AndSpecification( fs, ts).isSatisfiedBy( trueEntity) must beFalse 
			new AndSpecification( fs, fs).isSatisfiedBy( trueEntity) must beFalse 
			new AndSpecification( fs, fs).isSatisfiedBy( falseEntity) must beTrue 
		}
		"logically or two specifications together" in {
			new OrSpecification( ts, ts).isSatisfiedBy( trueEntity) must beTrue 
			new OrSpecification( ts, fs).isSatisfiedBy( trueEntity) must beTrue 
			new OrSpecification( fs, ts).isSatisfiedBy( trueEntity) must beTrue 
			new OrSpecification( fs, fs).isSatisfiedBy( trueEntity) must beFalse 
			new OrSpecification( fs, fs).isSatisfiedBy( falseEntity) must beTrue 
		}
		"logically not two specifications together" in {
			new AndSpecification( ts, new NotSpecification(fs)).isSatisfiedBy( trueEntity) must beTrue 
		}
	}
}
