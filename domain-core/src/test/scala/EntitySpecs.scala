package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending
import org.scalastuff.scalabeans.Preamble._
import sdo.core.domain.{Entity, Field, NumericField, AlphaField, EntityUuidIdField }
import sdo.core.domain.{EntityError, ValidationError, MustBeNumeric, OnlyOneFieldCanHaveValue}
import sdo.core.domain.EntityValidationMethods.onlyOneHasValue

class Test( initialId :EntityUuidIdField) extends Entity{

	override val id = initialId

	override def descriptor = descriptorOf[Test]

	val numeric = new NumericField()

	val alpha = new AlphaField()

	override def fieldList : List[Field[_]] =  this.numeric :: this.alpha :: Nil 

	setup
	
}

object Test {
	def apply() = new Test( EntityUuidIdField())
}

class EntitySpecs extends Specification {

	"The Entity trait " should {

		"provide a list of it's fields" in {
			Pending("Scala reflection is changing soon, and is way complex, so do this later")
		}

		"mark itself dirty when one of it's fields is dirty" in {
			val test = Test()
			test.numeric value = "1"
			test.dirty_? must beTrue
		}

		"make itself clean" in {
			val test = Test()
			test.numeric value = "1"
			test.makeClean
			test.dirty_? must beFalse
		}

		"returns a list of field errors" in {
			val test = Test()
			test.numeric value = "a"
			test.validationErrors must contain (MustBeNumeric("a"))
		}

		"returns a list of domain errors" in {
			val test = new Test( EntityUuidIdField()) {
				override def validations : List[ ValidationFunction] = onlyOneHasValue( numeric :: alpha :: Nil) _ :: Nil
			}
			test.numeric value = "1"
			test.alpha value="a"
			test.validate
			test.validationErrors must contain (OnlyOneFieldCanHaveValue( test.numeric :: test.alpha :: Nil))
		}

		"validates itself whenever a field changes" in {
			
			val test = new Test( EntityUuidIdField()) {
				override def validations : List[ ValidationFunction] = onlyOneHasValue( numeric:: alpha::Nil) _ :: Nil				
			}
			test.numeric value = "1"
			test.alpha value="a"
			test.validationErrors must contain (OnlyOneFieldCanHaveValue( test.numeric :: test.alpha :: Nil))
		}

		"uses id field for equality" in {
			val id = EntityUuidIdField()
			new Test( id) must be_==( new Test( id))
		}
	}
}
