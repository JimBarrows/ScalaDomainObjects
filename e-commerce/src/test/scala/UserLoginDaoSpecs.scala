package test

import org.specs2.mutable._
import sdo.core.domain._
import sdo.ecommerce.infrastructure.UserLoginDao

class UserLoginDaoSpecs extends Specification {

	"The UserLoginDao should" should {

		"create a user login record" in {
			UserLoginTable.create	
			todo()
		}
	}
}
