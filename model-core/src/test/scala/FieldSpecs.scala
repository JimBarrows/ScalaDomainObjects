package sdo.specs

import org.specs2.mutable._
import sdo.core._
import sdo.core.ValidationMethods.emptyFieldErrorList

class FieldSpecs extends Specification {

	"The Field trait " should {

		case class TestError extends FieldError

		class TestField extends Field[String] {
		
			override def validations:List[ValidationFunction] = validationCalled _ :: Nil

			def validationCalled( value:Option[String]):List[FieldError] =  emptyFieldErrorList
		}

		"be clean by default" in {
			val testField = new TestField()
			testField.clean_? must beTrue
		}

		"uninitialized by default" in {
			val testField = new TestField()
			testField.initialized_? must beFalse
		}

		"readable by default" in {
			val testField = new TestField()
			testField.readable_? must beTrue
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
			var validatorWasCalled = false
			val testField = new TestField() {
				
				override def validationCalled( value:Option[String]):List[FieldError] =  {
					validatorWasCalled = true
					emptyFieldErrorList
					}
			}
			testField.assign(Some("this is another string"))
			validatorWasCalled must beTrue
		}

		"assignment returns empty list when validations pass" in {
			val testField = new TestField()
			testField.assign(Some("this is another string")) must be empty
		}

		"assignment returns a list of error when validations fail" in{
			val testField = new TestField(){

				override def validationCalled( value:Option[String]):List[FieldError] =  {
					TestError() :: Nil	
					}

			}
			testField.assign(Some("this is another string")) must contain( TestError())

		}

		"when not readable, returns None" in {
			val testField = new TestField() {
				override def readable_? = false
			}
			testField.assign(Some("A string"))
			testField.value must beNone
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
			val testField = new TestField()  {
				override def writable_? = allowWrite
			}
			testField.assign(Some("A string"))
			allowWrite = false
			testField.assign(Some("Another string"))
			testField.value must beSome("A string")
		}
		
		"allow write if field is uninitialized" in {
			val testField = new TestField()  {
				override def writable_? = false
			}
			testField.assign(Some("A string"))
			testField.value must beSome("A string")
		}

		"doesn't allow write to field after initialized" in {
			val testField = new TestField()  {
				override def writable_? = false
			}
			testField.assign(Some("A string"))
			testField.assign(Some("Another string"))
			testField.value must beSome("A string")
		}
	}
}
