package sdo.peopleAndOrganizations.domain.contactMechanisms

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

/**Implement this trait on someone, group or thing that can be contacted.
*/
trait Contactable {
	
	private val contactMechanisms = new ListField[ ContactMechanismField[ContactMechanism]]()
}


/** This is the information surrounding the end point.
*/
case class ContactMechanism (
	solicitable :BooleanField = BooleanField( false),
	purposes: List[PurposeField]	 = Nil,
	activePeriod: DateRangeField = DateRangeField()
)

class ContactMechanismField[T <: ContactMechanism] extends Field[ T] {
}

case class Purpose( activePeriod: DateRangeField)

class PurposeField extends Field[ Purpose] {
}

trait ElectronicAddress extends ContactMechanism {
}

class ElectronicAddressField extends ContactMechanismField[ ElectronicAddress] {
}

class PhoneNumber extends ContactMechanism {
	val acceptsData = BooleanField
	val acceptsFaxes = BooleanField
	val acceptsTextMessages = BooleanField
	val areaCode = NumericField
	val contactNumber = NumericField
	val countryCode = NumericField
	val extension = NumericField
}

class PhoneNumberField extends ContactMechanismField[ PhoneNumber] {
}

class PostalAddress(activePeriod: DateRangeField, comment: TextField, address: TextField, within: GeographicBoundary) extends ContactMechanism {
	
	
}

object PostalAddress {

	def usPostalAddress(address: String, city: City, state: State, zipCode: ZipCode) = {
		state contains_+ ( zipCode contains_+ ( city))
		new PostalAddress( DateRangeField(), TextField(), TextField( address), city)
	} 
}

class PostalAddressField extends ContactMechanismField[ PostalAddress] {
}

class GeographicBoundary( geoCode: TextField, initialName: TextField, abbreviation: ShortTextField, 
												 initialContainedBy: ListField[GeographicBoundary], initialContains: ListField[GeographicBoundary])  {

	val name = initialName

	val contains = initialContains
	val containedBy = initialContainedBy

	def contains_+ (containedBoundary: GeographicBoundary) = {
		if( ! contains.exists( gb => gb.name == containedBoundary.name)) {
			contains.add( containedBoundary)
		}
		this
	}

	def containedBy_+ ( boundary: GeographicBoundary) = {
		if( !containedBy.exists( gb => gb.name == boundary.name)) {
			containedBy.add( boundary)
		}
	}
	
}

class GeographicBoundaryField extends Field[ GeographicBoundary] {
}

class City( geoCode: TextField, name: TextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}

class State( geoCode: TextField, name: TextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}

class ZipCode( geoCode: TextField, name: TextField, plusFour: ShortTextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}

class Country( geoCode: TextField, name: TextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}

class Province( geoCode: TextField, name: TextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}

class SalesTerritory( geoCode: TextField, name: TextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}

class ServiceTerritory( geoCode: TextField, name: TextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}

class Region( geoCode: TextField, name: TextField, abbreviation: ShortTextField, within: ListField[GeographicBoundary], in: ListField[GeographicBoundary]) extends GeographicBoundary( geoCode, name, abbreviation, within, in) {
}
