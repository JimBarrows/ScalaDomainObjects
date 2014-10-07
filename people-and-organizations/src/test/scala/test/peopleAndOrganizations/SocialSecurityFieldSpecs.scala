package test.peopleAndOrganizations

import org.specs2.mutable._
import sdo.core.domain._
import scalaz.{Success => ValidationSuccess, Failure=> ValidationFailure, Scalaz, _}
import Scalaz._
import org.specs2.scalaz.ValidationMatchers._
import sdo.peopleAnOrganizations.domain._

class AreaNumberSpecs extends Specification {

	"An area number" should {

		"method notBetween734And749 should return valid if the value is not between 734 & 749." in {
			AreaNumber("734") must beFailing
		}

		"not validate  if the area number is between 734 and 749" in {
			(734 to 749) foreach { n=>
				AreaNumber(n.toString) must beFailing
			}	
			success
		}

		"not validate  if the area number is over 772" in {
			AreaNumber("773") must beFailing
		}

		"not validate  if area number is 666" in {
			AreaNumber("666")  must beFailing
		}
		"not validate  if the area number is not exactly 3 numbers in length" in {
			AreaNumber("1")  must beFailing
			AreaNumber("22") must beFailing
			AreaNumber("4444") must beFailing
		}
	}
}

class SocialSecurityFieldSpecs extends Specification {

	"A Social Security Number Field" should {


		"not validate  if the area number is between 734 and 749" in {
			//SocialSecurityNumberField( SocialSecurityNumber( Some(AreaNumber("734")), GroupNumber("12"), SerialNumber("1234"))) must beFailing
			todo
		}

		"not validate  if the area number is over 772" in {
			todo
		}

		"not validate  if area number is 666" in {
			todo
		}
		"not validate  if the area number is not exactly 3 numbers in length" in {
			todo
		}

		"not validate  if a group number is not exactly 2 numbers in length" in {
			todo
		}

		"not validate if a serial number is not exactly 4 numbers in length" in {
			todo
		}

		"not validate if the social security number is between 987-65-4320 through 987-65-4329 inclusive" in {
			todo
		}

		"not validate if the area, group or serial number is all zeros" in {
			todo
		}

		"be valid if only the last 4 are provided" in {
			todo
		}
	}
}
