package sdo.core.specs

import scalaz._
import Scalaz._
import org.specs2.mutable._
import sdo.core.domain._
import sdo.core.domain.ValidationMethods._
import sdo.core.domain.EntityValidationMethods._

class ValidationSpecs extends Specification {

	"The Field Validation object " should {
		"validate an all numeric string" in {
			allNumeric( Some("999")) must_== Success(Some("999"))
		}
		"validate none" in {
			allNumeric( None) must_== Success(None)
		}
		"return MustBeNumeric with a value of a123" in {
			allNumeric( Some("a123")) must_==  Failure(MustBeNumeric( "a123"))
		}
		"return MustBeNumeric with a value of 1a23" in {
			allNumeric( Some("1a23")) must_==  Failure(MustBeNumeric( "1a23"))
		}
		"return MustBeNumeric with a value of 123a" in {
			allNumeric( Some("123a")) must_==  Failure(MustBeNumeric( "123a"))
		}
		"validate 100 is not all zeros" in {
			notAllZeros( Some("100")) must_== Success(Some("100"))
		}
		"validate 010 is not all zeros" in {
			notAllZeros( Some("010")) must_== Success(Some("010"))
		}
		"validate 001 is not all zeros" in {
			notAllZeros( Some("001")) must_== Success(Some("001"))
		}
		"validate None is not all zeros" in {
			notAllZeros( None)  must_== Success(None)
		}
		"return CannotBeAllZeros( 000) when all zeros" in {
			notAllZeros( Some("000")) must_==  Failure(CannotBeAllZeros("000"))
		}
		"return CannotBeAllZeros( 0) when all zeros" in {
			notAllZeros( Some("0")) must_==  Failure(CannotBeAllZeros("0"))
		}
		"validate 100 is not all 666" in {
			not666( Some("100")) must_== Success(Some("100"))
		}
		"validate 1666 is not all 666" in {
			not666( Some("1666"))  must_== Success(Some("1666"))
		}
		"validate 6661 is not all 666" in {
			not666( Some("6661")) must_== Success(Some("6661"))
		}
		"validate None is not all 666" in {
			not666( None) must_== Success(None)
		}
		"return CannotContain666( 666) when 666" in {
			not666( Some("666")) must_==  Failure( CannotContain666( "666"))
		}
		"validate string of 3 characters when the maxlength is 4" in {
			maxLength( 4) ( Some("123"))must_== Success(Some("123"))
		}
		"validate string of 4 characters when the maxlength is 4" in {
			maxLength( 4) ( Some("1234")) must_== Success(Some("1234"))
		}
		"validate None when the maxlength is 4" in {
			maxLength( 4 ) ( None)must_== Success(None)
		}
		"returns CannotBeLongerThan(4, 12345) when the string 12345, and the length 4" in {
			maxLength(4) ( Some("12345"))must_==  Failure( CannotBeLongerThan( 4, "12345"))
		}
		"validate 'abc' is all alpha" in {
			allAlpha( Some("abc")) must_== Success(Some("abc"))
		}
		"returns MustBeAlpha('1abc') when the string is '1abc'" in {
			allAlpha( Some("1abc")) must_==  Failure( MustBeAlpha("1abc"))
		}

		"validate minimum validates when the value is equal" in {
			minimum( 3)( Some(3))  must_== Success(Some(3))
		}

		"validate minimum validates when the value is greater" in {
			minimum( 3)( Some(4))must_== Success(Some(4))
		}
		
		"returns LessThanMinimum( 3, 2) when the value is 2" in {
			minimum( 3)( Some(2)) must_==  Failure( LessThanMinimum( 3, 2))
		}
	}


	"The Entity Validation Object" should {
		"validate that when no fields have value onlyOneHasValue has no errors" in {
			val field1 = new NumericField()
			val field2 = new AlphaField()
			onlyOneHasValue( field1 :: field2 :: Nil)(null) must_== Success(null)
		}
		"validate that when one field has value onlyOneHasValue has no errors" in {
			val field1 = new NumericField()
			val field2 = new AlphaField()
			field1.value = "1"
			onlyOneHasValue( field1 :: field2 :: Nil)( null) must_== Success(null)
		}
		"validate that when all fields have value onlyOneHasValue returns OnlyOneFieldCanHaveValue( fieldList)" in {
			val field1 = new NumericField()
			val field2 = new AlphaField()
			field1.value = "1"
			field2.value = "a"
			onlyOneHasValue( field1:: field2::Nil)( null) must_== Failure( OnlyOneFieldCanHaveValue( field1 :: field2 :: Nil))
		}
	}
}
