package sdo.products.test.unit

import org.specs2.mutable.Specification
import scalaz.Scalaz._
import sdo.core.domain._
import sdo.peopleAnOrganizations.domain._

class PricingSpecs extends Specification{
	
	"A pricing component" should {
		
		"be for a date range" in {
			pending
		}
		
		"be for a fixed amount xor percent" in {
			pending
		}
		
		"be for a product" in {
			pending
		}
		
		"be for a product feature" in {
			pending
		}
		
		"be specified for an organization" in {
			pending
		}
		
		"be optionally based on a geographic boundary" in {
			pending
		}
		
		"be optionally based on a party type" in {
			pending
		}
		
		"be optionally based on quantity break" in {
			pending
		}
		
		"be optionally based on the order value" in {
			pending
		}
		
		"be optionally based on the sale type" in {
			pending
		}
	}
	
	"A recurring charge" should {
		"be a kind of price component" in {
			pending
		}
		
		"be based on a time frequency measure" in {
			pending
		}
	}

	"A utilization charge" should {
		"be  kind of price component" in {
			pending
		}
		
		"have a quantity indicating the utilization" in {
			pending
		}
		
		"be based on a quantity measure" in {
			pending
		}
	}
}