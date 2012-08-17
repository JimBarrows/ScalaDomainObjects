package sdo.specs

import org.specs2.mutable._
import sdo.core._
import sdo.core.ValidationMethods._

class ValidationsSpecs extends Specification {

	"The Validation object " should {
		"validate an all numeric string" in {
			allNumeric( Some("999")) must be empty
		}
		"validate none" in {
			allNumeric( None) must be empty
		}
		"return MustBeNumeric with a value of a123" in {
			allNumeric( Some("a123")) must contain  (MustBeNumeric( "a123"))
		}
		"return MustBeNumeric with a value of 1a23" in {
			allNumeric( Some("1a23")) must contain  (MustBeNumeric( "1a23"))
		}
		"return MustBeNumeric with a value of 123a" in {
			allNumeric( Some("123a")) must contain  (MustBeNumeric( "123a"))
		}
		"validate 100 is not all zeros" in {
			notAllZeros( Some("100")) must be empty
		}
		"validate 010 is not all zeros" in {
			notAllZeros( Some("010")) must be empty
		}
		"validate 001 is not all zeros" in {
			notAllZeros( Some("001")) must be empty
		}
		"validate None is not all zeros" in {
			notAllZeros( None) must be empty
		}
		"return CannotBeAllZeros( 000) when all zeros" in {
			notAllZeros( Some("000")) must contain (CannotBeAllZeros("000"))
		}
		"return CannotBeAllZeros( 0) when all zeros" in {
			notAllZeros( Some("0")) must contain (CannotBeAllZeros("0"))
		}
		"validate 100 is not all 666" in {
			not666( Some("100")) must be empty
		}
		"validate 1666 is not all 666" in {
			not666( Some("1666")) must be empty
		}
		"validate 6661 is not all 666" in {
			not666( Some("6661")) must be empty
		}
		"validate None is not all 666" in {
			not666( None) must be empty
		}
		"return CannotContain666( 666) when 666" in {
			not666( Some("666")) must contain( CannotContain666( "666"))
		}
		"validate string of 3 characters when the maxlength is 4" in {
			maxLength( 4)(Some("123")) must be empty
		}
		"validate string of 4 characters when the maxlength is 4" in {
			maxLength( 4)(Some("1234")) must be empty
		}
		"validate None when the maxlength is 4" in {
			maxLength( 4)(None) must be empty
		}
		"returns CannotBeLongerThan(4, 12345) when the string 12345, and the length 4" in {
			maxLength(4)(Some("12345")) must contain( CannotBeLongerThan( 4, "12345"))
		}
	}
}
