package sdo.products.test.unit

import org.specs2.mutable.Specification
import scalaz.Scalaz._
import sdo.core.domain._
import sdo.peopleAnOrganizations.domain._

class ProductToProductAssociationSpecs extends Specification {

	"A product to product association" should {
		"optionally be made up of product components" in {
			pending
		}

		"optionally be made up of product substitute" in {
			pending
		}

		"optionally be made up of product obsolences" in {
			pending
		}

		"optionally be made up of product complements" in {
			pending
		}

		"optionally be made up of product incompatbility" in {
			pending
		}
	}

	"A product component" should {
		"have a date range it's good for" in {
			pending
		}
		"have a quantity used" in {
			pending
		}
		"have instructions" in {
			pending
		}
		"optionally have comments" in {
			pending
		}
	}

	"A product substitute" should {
		"have a date range it's good for" in {
			pending
		}
		"have a quantity" in {
			pending
		}
		"optionally have comments" in {
			pending
		}
	}

	"A product obsolesence" should {
		"Have a supression date" in {
			pending
		}
		"have a reason" in {
			pending
		}
	}

	"A product complement" should {
		"have a date range" in {
			pending
		}
		"have a reason" in {
			pending
		}
	}

	"A product incompatibility" should {
		"have a date range" in {
			pending
		}
		"have a reason" in {
			pending
		}
	}
}