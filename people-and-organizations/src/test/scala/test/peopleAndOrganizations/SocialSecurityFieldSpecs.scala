package test.peopleAndOrganizations

import org.specs2.mutable._
import sdo.core.domain._
import scalaz.{Success => ValidationSuccess, Failure=> ValidationFailure, Scalaz, _}
import Scalaz._
import sdo.peopleAnOrganizations.domain._

class AreaNumberSpecs extends Specification {

	"An area number" should {

		"method notBetween734And749 should return valid if the value is not between 734 & 749." in {
			AreaNumber.notBetween734And749(Some("734")) must_== ValidationFailure(NonEmptyList(AreaCannotBeBetween734And749( "734")))
		}

		"not validate  if the area number is between 734 and 749" in {
			(734 to 749) foreach { n=>
				val areaNumber = AreaNumber(n.toString) 
				areaNumber.validate must_== ValidationFailure(NonEmptyList(AreaCannotBeBetween734And749( n.toString)))
			}	
			success
		}

		"not validate  if the area number is over 772" in {
			val areaNumber = AreaNumber("773") 
			areaNumber.validate must_== ValidationFailure(NonEmptyList(CannotBeOver772( "773")))
		}

		"not validate  if area number is 666" in {
			val areaNumber = AreaNumber("666") 
			areaNumber.validate must_== ValidationFailure(NonEmptyList(CannotContain666("666")))
		}
		"not validate  if the area number is not exactly 3 numbers in length" in {
			var areaNumber = AreaNumber("1") 
			areaNumber.validate must_== ValidationFailure(NonEmptyList(MustBeExactly(3,"1")))
			areaNumber = AreaNumber("22")
			areaNumber.validate must_== ValidationFailure(NonEmptyList(MustBeExactly(3,"22")))
			areaNumber = AreaNumber("4444")
			areaNumber.validate must_== ValidationFailure(NonEmptyList(MustBeExactly(3,"4444")))
		}
	}
}

class SocialSecurityFieldSpecs extends Specification {

	"A Social Security Number Field" should {


		"not validate  if the area number is between 734 and 749" in {
			val ssnField = new SocialSecurityNumberField() 
			ssnField.value = SSN( Some(AreaNumber("734")), None, SerialNumber("1234"))

			ssnField.validate must_== ValidationFailure(NonEmptyList(AreaCannotBeBetween734And749( "734")))
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
