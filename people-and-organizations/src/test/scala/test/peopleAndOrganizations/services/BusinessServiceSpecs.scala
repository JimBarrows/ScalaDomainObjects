package test.peopleAndOrganizations.services

import org.specs2.mutable.Specification
import sdo.core.domain._
import sdo.peopleAnOrganizations.domain._
import sdo.peopleAnOrganizations.repositories._
import sdo.peopleAndOrganizations.services._


class BusinessServiceSpecs extends Specification {

	"A BusinessService class" should {
		
		val partyRepo = new PartyRepositoryFixture()

		object BusinessServices extends BusinessServices {
			override def partyRepository():PartyRepository = partyRepo
		}

		"create a business" in {
			val expected = new TextField()
			expected value = "TestName"
			val actual = BusinessServices.create(expected)

			actual.name.value must beSome( "TestName")
			BusinessServices.businessQuery.isSatisfiedBy( actual) must beTrue
			partyRepo.list must contain(actual)
		}

		"fail to create a business if one already exists" in {
			pending
		}
		
		"find a business" in {
			pending
		}
	}

}
