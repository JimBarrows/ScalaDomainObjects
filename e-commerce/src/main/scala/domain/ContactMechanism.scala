package sdo.ecommerce.domain

import scalaz._
import Scalaz._
import sdo.core.domain.{ Entity, EntityUuidIdField, Field, FieldError, TextField, ShortTextField, ValueObject }
import sdo.core.domain.ValidationMethods._
import sdo.peopleAndOrganizations.domain.contactMechanisms._
import sdo.ecommerce.domain
import sdo.core.domain.FieldError

class WebAddressField extends ElectronicAddressField {

  val url = new UrlField()

}

object WebAddressField {
  def apply() = {
    val wa = new WebAddressField()
    wa
  }
}

case class MustBeEmailAddress(badValue: String) extends FieldError

class EmailAddressField extends Field[String] {

  override def validate = super.validate.flatMap(emailValidation _)

  def emailValidation(value: Option[String]): ValidationNel[FieldError, Option[String]] =
    value match {
      case Some(v) => EmailAddressField.emailRegex findFirstIn v match {
        case None => MustBeEmailAddress(v).failNel[Option[String]]
        case _ => value.successNel[FieldError]
      }
      case None => value.successNel[FieldError]
    }
}

object EmailAddressField {
  val emailRegex = """\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b""".r
}

case class MustBeIpAddress(badValue: String) extends FieldError

class IpAddressField extends Field[String] {

  override def validate = super.validate.flatMap(ipAddressValidation _)

  def ipAddressValidation(value: Option[String]): ValidationNel[FieldError, Option[String]] =
    value match {
      case Some(v) => IpAddressField.ipRegex findFirstIn v match {
        case None => MustBeIpAddress(v).failNel[Option[String]]
        case _ => value.successNel[FieldError]
      }
      case None => value.successNel[FieldError]
    }
}

object IpAddressField {
  val ipRegex = """^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$""".r

}

