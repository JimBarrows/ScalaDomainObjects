package sdo.products.test.unit

import org.joda.time.DateMidnight
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import sdo.products.Product
import sdo.products.Category
import sdo.core.domain.DateRange


class ProductSpecs extends Specification {

  "The product class" should {
	  
  	"have a business related id field" in {
  		val product = Product()
  		product.productId value = "new ID"
  		product.productId.value must beSome( beEqualTo("new ID"))
  	}
  	
  	"have a name" in {
  		val product = Product()
  		product.name value = "new ID"
  		product.name.value must beSome( beEqualTo("new ID"))
  	}
  	
  	"have an introduction date" in {
  		val product = Product()
  		product.introductionDate value =( new DateMidnight(2004, 4, 4))
  		product.introductionDate.value must beSome( beEqualTo(new DateMidnight(2004, 4, 4)))
  	}
  	
  	"have an optional sales discontinuation date" in {
  		val product = Product()
  		product.salesDiscontinuationDate  value =( new DateMidnight(2004, 4, 4))
  		product.salesDiscontinuationDate.value must beSome( beEqualTo(new DateMidnight(2004, 4, 4)))
  	}
  	
  	
  	"have an optional support discontinuation date" in {
  		val product = Product()
  		product.supportDiscontinuationDate   value =( new DateMidnight(2004, 4, 4))
  		product.supportDiscontinuationDate.value must beSome( beEqualTo(new DateMidnight(2004, 4, 4)))
  	}
  	
  	"allow comments to be made about the product" in {
  		val product = Product()
  		product.comment value = "new ID"
  		product.comment.value must beSome( beEqualTo("new ID"))
  	}
  	
  	"be categorized" in {
  		val product = Product()
  		val category = Category()
  		product.categories += category
  		
  		product.categories.length must be_== (1)
  	}
  	
  	"allow for categorization for a period of time" in {
  		val category = Category()
  		category.active value = DateRange( DateMidnight.now().plusDays(-30), Some(DateMidnight.now().plusDays(30)))
  		category.active.containsNow must beTrue
  	}
  	
  	"allow for only one primary category" in {
  		val product = Product()
  		val primaryCategory = Category()
  		val secondPrimaryCategory = Category()
  		primaryCategory.primaryFlag  value = true
  		secondPrimaryCategory.primaryFlag  value = true
  		product.categories += primaryCategory
  		
  		
  	}
  }
}
