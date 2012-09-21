package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending
import org.scalastuff.scalabeans.Preamble._
import sdo.core.{Entity, Field, NumericField, AlphaField }
import sdo.core.{EntityError, ValidationError, MustBeNumeric, OnlyOneFieldCanHaveValue}
import sdo.core.EntityValidationMethods.onlyOneHasValue

class Test extends Entity{

	override def descriptor = descriptorOf[Test]

	val numeric = new NumericField()

	val alpha = new AlphaField()

	override lazy val fieldList : List[Field[_]] =  {
		this.numeric :: this.alpha :: Nil
		}
	
	setup()
}

class EntitySpecs extends Specification {

	"The Entity trait " should {

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
				override def validatorList : List[ Entity => List[EntityError]] = onlyOneHasValue( numeric:: alpha::Nil) _ :: Nil				
			}
			test.numeric value = "1"
			test.alpha value="a"
			test.runValidations( null)
			test.validationErrors must contain (OnlyOneFieldCanHaveValue( test.numeric :: test.alpha :: Nil))
		}

		"validates itself whenever a field changes" in {
			
			val test = new Test() {
				override def validatorList : List[Entity => List[EntityError]] = onlyOneHasValue( numeric:: alpha::Nil) _ :: Nil				
			}
			test.numeric value = "1"
			test.alpha value="a"
			test.validationErrors must contain (OnlyOneFieldCanHaveValue( test.numeric :: test.alpha :: Nil))
		}
	}
}
