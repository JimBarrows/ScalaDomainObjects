package sdo.core.specs

import org.specs2.mutable._
import reactive.{ Observing, Var }
import scalaz._
import Scalaz._
import sdo.core.domain._
import sdo.core.domain.ValidationMethods.noErrors

class FieldSpecs extends Specification {

  "The Field trait " should {

    case class TestError extends FieldError

    class TestField extends Field[String] {
      override def validate = value match {
        case Some(f) => validationCalled(value)
        case None => value.successNel[FieldError]
      }

      var called = false
      def validationCalled(testField: Option[String]):ValidationNel[FieldError, Option[String]] = { called = true; value.successNel[FieldError] }
    }

    "be clean by default" in {
      val testField = new TestField()
      testField.clean_? must beTrue
    }

    "uninitialized by default" in {
      val testField = new TestField()
      testField.initialized_? must beFalse
    }

    "writable by default" in {
      val testField = new TestField()
      testField.writable_? must beTrue
    }

    "return Nil, be initialized and dirty when assigned a legal value" in {
      val testField = new TestField()
      testField.assign(Some("this is a string"))
      testField.clean_? must beFalse
      testField.initialized_? must beTrue
    }

    "after being made dirty, can be made clean" in {
      val testField = new TestField()
      testField.assign(Some("this is another string"))
      testField.makeClean
      testField.clean_? must beTrue
    }

    "valdiator is called when assignment made" in {
      val testField = new TestField() {

      }
      testField.assign(Some("this is another string"))
      testField.called must beTrue
    }

    "field contains an empty list when validations pass" in {
      val testField = new TestField()
      testField.assign(Some("this is another string"))
      testField.validate must_== Success(Some("this is another string")) 
      
    }

    "field contains a list of errors when validations fail" in {
      val testField = new TestField() {

        override def validationCalled(testField: Option[String]):ValidationNel[FieldError, Option[String]]  =  TestError().failureNel[Option[String]]

      }
      testField.assign(Some("this is another string")).validate must_== Failure(NonEmptyList( TestError()))

    }

    "returns the value assigned" in {
      val testField = new TestField()
      testField.assign(Some("A string"))
      testField.value must beSome("A string")
    }

    "returns None when no value is assigned" in {
      val testField = new TestField()
      testField.value must beNone
    }

    "doesn't change the value of the field when made unwritable" in {
      var allowWrite = true
      val testField = new TestField() {
        override def writable_? = allowWrite
      }
      testField.assign(Some("A string"))
      allowWrite = false
      testField.assign(Some("Another string"))
      testField.value must beSome("A string")
    }

    "allow write if field is uninitialized" in {
      val testField = new TestField() {
        override def writable_? = false
      }
      testField.assign(Some("A string"))
      testField.value must beSome("A string")
    }

    "doesn't allow write to field after initialized" in {
      val testField = new TestField() {
        override def writable_? = false
      }
      testField.assign(Some("A string"))
      testField.assign(Some("Another string"))
      testField.value must beSome("A string")
    }

    "notifiy others when it becomes dirty" in {
      var heard = false
      object Listener extends Observing {
        val testField = new TestField()
        testField.change foreach setHeardTrue
        def setHeardTrue(v: String): Unit = {
          heard = true
        }
      }
      Listener.testField.assign(Some("A string"))
      heard must beTrue
    }

    "not be equal if of different types" in {
      NumericField(3).equals(ShortTextField("3")) must beFalse
    }

    "be equal if same type and value" in {
      NumericField(3).equals(NumericField(3)) must beTrue
    }

    "not be equal if same type and different values" in {
      NumericField(3).equals(NumericField(2)) must beFalse
    }
  }

  "A numeric field " should {
    "accept '123456'" in {
      val numeric = new NumericField()
      numeric.assign(Some("123456")).validate must_== Success(Some("123456")) 
    }

    "return error for 'a123456'" in {
      val numeric = new NumericField()
      numeric.assign(Some("a123456")).validate must_== Failure(NonEmptyList(MustBeNumeric("a123456")))
    }

    "return error for '123a456'" in {
      val numeric = new NumericField()
      numeric.assign(Some("123a456")).validate must_== Failure(NonEmptyList(MustBeNumeric("123a456")))
    }

    "return error for '123456a'" in {
      val numeric = new NumericField()
      numeric.assign(Some("123456a")).validate must_== Failure(NonEmptyList(MustBeNumeric("123456a")))
    }

    "return error for ''" in {
      val numeric = new NumericField()
      numeric.assign(Some("")).validate must_== Failure(NonEmptyList(MustBeNumeric("")))
    }

    "return error for ' 1 '" in {
      val numeric = new NumericField()
      numeric.assign(Some(" 1 ")).validate must_== Failure(NonEmptyList(MustBeNumeric(" 1 ")))
    }

    "Be able to put together a list of different subtypes" in {
      val scheduledAt = new DateTimeField()
      val successfulOutcome = new TextField()

      List(scheduledAt, successfulOutcome) must contain(allOf(scheduledAt, successfulOutcome))

    }
  }

  "An alpha field " should {
    "accept 'abc'" in {
      val alpha = new AlphaField()
      alpha.assign(Some("abc")).validate must_== Success(Some("abc"))
    }
    "return MustBeAlpha('1abc') for value '1abc'" in {
      val alpha = new AlphaField()
      alpha.assign(Some("1abc")).validate must_== Failure(NonEmptyList(MustBeAlpha("1abc")))
    }
  }

  "An entity id field" should {
    "not be mutable" in {
      val foo = new EntityIdField[Long](1l)
      foo.value = 3l
      foo.value must beSome.which(_ == 1l)
    }

    "have a uuid setable via apply" in {
      val uuid = EntityUuidIdField()
      uuid.value must beSome
    }

    "A uuid field must be equal to itself" in {
      val uuid = EntityUuidIdField()
      uuid must be_==(uuid)
    }

    "The apply method should return different UUID values" in {
      EntityUuidIdField() must not be_== (EntityUuidIdField())
    }
  }

  "An integer field" should {
    "accept an integer to initialize itself" in {
      IntegerField(3).value must beSome.which(_ == 3)
    }
  }

  "A list field" should {
    "take a list of some type" in {
      class StringListField extends ListField[String] {
      }
      val field = new StringListField
      success
    }

    "add an element to the list" in {
      class StringListField extends ListField[String] {
      }
      val field = new StringListField
      field.add("Hello")

      field.value.get must contain("Hello")
    }

    "remove an element to the list" in {
      class StringListField extends ListField[String] {
      }
      val field = new StringListField
      field.add("Hello")
      field.remove("Hello")

      field.value.get must be empty
    }

    "two list fields are equal if they have the same elements" in {
      class StringListField extends ListField[String] {
      }
      val field1 = new StringListField
      val field2 = new StringListField
      field1.add("Hello")
      field2.add("Hello")

      field1 must be_==(field2)
    }
  }
}

