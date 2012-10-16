package sdo.specs

import org.specs2.mutable._
import sdo.core.domain._
import sdo.core.domain.ValidationMethods._
import sdo.core.domain.EntityValidationMethods._

class ValidationSpecs extends Specification {

	"The Field Validation object " should {
		"validate an all numeric string" in {
			allNumeric( NumericField("999")) must be empty
		}
		"validate none" in {
			allNumeric( NumericField()) must be empty
		}
		"return MustBeNumeric with a value of a123" in {
			allNumeric( NumericField("a123")) must contain  (MustBeNumeric( "a123"))
		}
		"return MustBeNumeric with a value of 1a23" in {
			allNumeric( NumericField("1a23")) must contain  (MustBeNumeric( "1a23"))
		}
		"return MustBeNumeric with a value of 123a" in {
			allNumeric( NumericField("123a")) must contain  (MustBeNumeric( "123a"))
		}
		"validate 100 is not all zeros" in {
			notAllZeros( NumericField("100")) must be empty
		}
		"validate 010 is not all zeros" in {
			notAllZeros( NumericField("010")) must be empty
		}
		"validate 001 is not all zeros" in {
			notAllZeros( NumericField("001")) must be empty
		}
		"validate None is not all zeros" in {
			notAllZeros( NumericField()) must be empty
		}
		"return CannotBeAllZeros( 000) when all zeros" in {
			notAllZeros( NumericField("000")) must contain (CannotBeAllZeros("000"))
		}
		"return CannotBeAllZeros( 0) when all zeros" in {
			notAllZeros( NumericField("0")) must contain (CannotBeAllZeros("0"))
		}
		"validate 100 is not all 666" in {
			not666( NumericField("100")) must be empty
		}
		"validate 1666 is not all 666" in {
			not666( NumericField("1666")) must be empty
		}
		"validate 6661 is not all 666" in {
			not666( NumericField("6661")) must be empty
		}
		"validate None is not all 666" in {
			not666( NumericField()) must be empty
		}
		"return CannotContain666( 666) when 666" in {
			not666( NumericField("666")) must contain( CannotContain666( "666"))
		}
		"validate string of 3 characters when the maxlength is 4" in {
			maxLength( 4, NumericField("123")) must be empty
		}
		"validate string of 4 characters when the maxlength is 4" in {
			maxLength( 4, NumericField("1234")) must be empty
		}
		"validate None when the maxlength is 4" in {
			maxLength( 4, NumericField()) must be empty
		}
		"returns CannotBeLongerThan(4, 12345) when the string 12345, and the length 4" in {
			maxLength(4, NumericField("12345")) must contain( CannotBeLongerThan( 4, "12345"))
		}
		"validate 'abc' is all alpha" in {
			allAlpha(NumericField("abc")) must be empty
		}
		"returns MustBeAlpha('1abc') when the string is '1abc'" in {
			allAlpha(NumericField("1abc")) must contain( MustBeAlpha("1abc"))
		}

		"validate minimum validates when the value is equal" in {
			minimum( 3, IntegerField(3)) must be empty
		}

		"validate minimum validates when the value is greater" in {
			minimum( 3, IntegerField(4)) must be empty
		}
		
		"returns LessThanMinimum( 3, 2) when the value is 2" in {
			minimum( 3, IntegerField(2)) must contain( LessThanMinimum( 3, 2))
		}
	}


	"The Entity Validation Object" should {
		"validate that when no fields have value onlyOneHasValue has no errors" in {
			val field1 = new NumericField()
			val field2 = new AlphaField()
			onlyOneHasValue( field1 :: field2 :: Nil)() must be empty
		}
		"validate that when one field has value onlyOneHasValue has no errors" in {
			val field1 = new NumericField()
			val field2 = new AlphaField()
			field1.value = "1"
			onlyOneHasValue( field1 :: field2 :: Nil)() must be empty
		}
		"validate that when all fields have value onlyOneHasValue returns OnlyOneFieldCanHaveValue( fieldList)" in {
			val field1 = new NumericField()
			val field2 = new AlphaField()
			field1.value = "1"
			field2.value = "a"
			onlyOneHasValue( field1:: field2::Nil)() must contain( OnlyOneFieldCanHaveValue( field1 :: field2 :: Nil))
		}
	}
}
