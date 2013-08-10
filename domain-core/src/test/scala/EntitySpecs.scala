package sdo.specs

import com.github.nscala_time.time.Imports._
import org.specs2.mutable.Specification
import org.specs2.execute.Pending
import org.scalastuff.scalabeans.Preamble._
import sdo.core.domain.{Entity, Field, NumericField, AlphaField, EntityUuidIdField, DateTimeField, TextField }
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

		"the list of fields should handle very field type" in {
			
			val dateTimeField = DateTimeField( DateTime.now)
			val textField = TextField("foo")

			class Foo extends Entity {
				val scheduledAt = dateTimeField
				val successfulOutcome = textField


				override val id = EntityUuidIdField()
				override def fieldList = List( scheduledAt, successfulOutcome)

				}

			new Foo().fieldList must contain( allOf( dateTimeField, textField))
			
		}

		"setup shouldn't try to process null pointers" in {
		 class Foo( initialId :EntityUuidIdField) extends Test( initialId) {
				val scheduledAt = new DateTimeField()
				val successfulOutcome = new TextField()

				override def fieldList :List[ Field[ _]] = super.fieldList ++ List( scheduledAt, successfulOutcome)

				setup
			}

			class Bar( initialId :EntityUuidIdField) extends Foo( initialId) {
				val icky = null
				val nicky = new TextField()

				override def fieldList :List[ Field[ _]] = super.fieldList ++ List( icky, nicky)

				setup
			}

			new Bar( EntityUuidIdField())
			success
		}

		"Should receive only 1 notification for a change" in {

			var callCount = 0

			class Foo( initialId :EntityUuidIdField) extends Test( initialId) {
				val scheduledAt = new DateTimeField()
				val successfulOutcome = new TextField()

				override def fieldList :List[ Field[ _]] = super.fieldList ++ List( scheduledAt, successfulOutcome)

			}

			object Foo {
				def apply() = {
					val f = new Foo( EntityUuidIdField()) 
					f.setup
					f
				}
			}

			class Bar( initialId :EntityUuidIdField) extends Foo( initialId) {
				val icky = new DateTimeField()
				val nicky = new TextField()

				override def fieldList :List[ Field[ _]] = super.fieldList ++ List( icky, nicky)

				override def runValidations( d0 :Any) :Unit = {
					callCount += 1
					validate
				}

			}

			object Bar {
				def apply() = {
					val f = new Bar( EntityUuidIdField()) 
					f.setup
					f
				}
			}


			val b = Bar( )

			b.successfulOutcome value = "hello"

			callCount must be_== (1)
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
