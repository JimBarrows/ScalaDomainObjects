package sdo.specs

import org.specs2.mutable._
import reactive.{Observing, Var}
import sdo.core.domain._
import sdo.core.domain.ValidationMethods.noErrors
import sdo.peopleAndOrganizations._

class SocialSecurityFieldSpecs extends Specification {

	"A Social Security Number Field" should {


		"publish an error if the area number is between 734 and 749" in {
			
			val ssnField = new SocialSecurityNumberField() 
			ssnField.value = SSN( Some(AreaNumber("734")), None, SerialNumber("1234"))
			ssnField.validationErrors must contain (AreaCannotBeBetween734And749( "734"))
		}

		"publish an error if the area number is over 772" in {
			todo
		}

		"publish an error if area number is 666" in {
			todo
		}
		"publish an error if the area number is not exactly 3 numbers in length" in {
			todo
		}

		"publish an error if a group number is not exactly 2 numbers in length" in {
			todo
		}

		"publish an error if a serial number is not exactly 4 numbers in length" in {
			todo
		}

		"publish an error if the social security number is between 987-65-4320 through 987-65-4329 inclusive" in {
			todo
		}

		"publish an error if the area, group or serial number is all zeros" in {
			todo
		}

		"be valid if only the last 4 are provided" in {
			todo
		}
	}
}
