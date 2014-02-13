package sdo.ecommerce.specs

import org.specs2.mutable._
import org.specs2.matcher._

import scala.util.parsing.combinator._
import scala.util.parsing.combinator.syntactical._

import sdo.ecommerce.domain.DomainName

class DomainNameSpecs extends Specification with ParserMatchers{

	val parsers = DomainName

	"A domain name" should {
		 "parse localhost" in {
		 	val expected = DomainName("localhost"  :: Nil)		 			
		 	val r = DomainName.run("localhost")	
		 	r.getOrElse(failure( r.toString)) must be_== (expected)
		 }
		 
		 "parse loca123" in {
		 	val expected = DomainName("local123"  :: Nil)		 			
		 	val r = DomainName.run("local123")
		 	r.getOrElse(failure(r.toString)) must be_==(expected)
		 }
		 
		 "not parse 123local" in {
		 		DomainName.run("123local") must beAFailure
		 	
		 }
		
		
		"parse localhost.localdomain" in {			
			val expected = DomainName("localhost"  :: "localdomain" :: Nil)
							
			val r = DomainName.run("localhost.localdomain")	
			r.getOrElse(failure( r.toString)) must be_== (expected)			
		}
		
		"parse www.google.com" in {			
			val expected = DomainName("www"  :: "google" :: "com" :: Nil)
							
			val r = DomainName.run("www.google.com")	
			r.getOrElse(failure( r.toString)) must be_== (expected)			
		}
		
		"return com as the tld for www.google.com" in {
			val domainName = DomainName("www"  :: "google" :: "com" :: Nil)
			domainName.tld must be_==("com")
		}
		
		"return google as the site for www.google.com" in {
			val domainName = DomainName("www"  :: "google" :: "com" :: Nil)
			domainName.site must be_==("google")
		}
		
		"return www.google.com as a string" in {
			val domainName = DomainName("www"  :: "google" :: "com" :: Nil)
			domainName.toString must be_==("www.google.com")
		}
	}
	
}
