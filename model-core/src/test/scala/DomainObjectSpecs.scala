package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending
import sdo.core.{DomainObject, Field, NumericField, AlphaField}
import sdo.core.{MustBeNumeric, OnlyOneFieldCanHaveValue}
import sdo.core.DomainValidationMethods.onlyOneHasValue

class Test extends DomainObject {

	val numeric = new NumericField()

	val alpha = new AlphaField()

	override def fieldList : List[Field[_]]= numeric :: alpha :: Nil

}

class DomainObjectSpecs extends Specification {

	"The DomainObject trait " should {

		"provide a list of it's fields" in {
			Pending("Scala reflection is changing soon, and is way complex, so do this later")
		}

		"mark itself dirty when one of it's fields is dirty" in {
			val test = new Test()
			test.numeric value = "1"
			test.dirty_? must beTrue
		}

		"make itself clean" in {
			val test = new Test()
			test.numeric value = "1"
			test.clean
			test.dirty_? must beFalse
		}

		"returns a list of field errors" in {
			val test = new Test()
			test.numeric value = "a"
			test.validationErrors must contain (MustBeNumeric("a"))
		}

		"returns a list of domain errors" in {
			val test = new Test() {
				override def validatorList : List[ValidationFunction] = onlyOneHasValue( numeric:: alpha::Nil) _ :: Nil				
			}
			test.numeric value = "1"
			test.alpha value="a"
			test.runValidations
			test.validationErrors must contain (OnlyOneFieldCanHaveValue( test.numeric :: test.alpha :: Nil))
		}
	}
}
