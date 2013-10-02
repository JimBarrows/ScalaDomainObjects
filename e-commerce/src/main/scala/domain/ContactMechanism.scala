package sdo.ecommerce.domain

import sdo.core.domain.{Entity, EntityUuidIdField, Field, FieldError, TextField, ShortTextField, ValueObject}
import sdo.core.domain.ValidationMethods._
import sdo.peopleAndOrganizations.domain.contactMechanisms._

class WebAddressField extends ElectronicAddressField {

	val url = new UrlField()

}

object WebAddressField {
	def apply() = {
		val wa = new WebAddressField()
		wa
	}
}

case class MustBeEmailAddress( badValue: String) extends FieldError

class EmailAddressField extends Field[String]{

	override def validations: List[ValidationFunction] = emailValidation _ :: Nil

	def emailValidation( value: Option[String]): List[FieldError] =
		value.map( v => EmailAddressField.emailRegex findFirstIn v match {
			case Some(f) => noErrors
			case None => MustBeEmailAddress( v) :: Nil
		}).getOrElse( noErrors)

}

object EmailAddressField {
		val emailRegex = """\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b""".r
}


case class MustBeIpAddress( badValue: String) extends FieldError

class IpAddressField extends Field[ String] {

	override def validations: List[ValidationFunction] = ipAddressValidation _ :: Nil

	def ipAddressValidation( value: Option[String]): List[FieldError]=
		value.map( v => IpAddressField.ipRegex findFirstIn v match {
			case Some(f) => noErrors
			case None => MustBeIpAddress( v) :: Nil
			}).getOrElse( noErrors)

}

object IpAddressField {
	val ipRegex = """^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$""".r

}
