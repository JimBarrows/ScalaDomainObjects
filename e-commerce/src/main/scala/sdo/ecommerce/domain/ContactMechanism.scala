package sdo.ecommerce.domain

import scalaz._
import scalaz.Scalaz._
import sdo.core.domain.{Field, FieldError}
import sdo.core.domain.ValidationMethods._
import sdo.peopleAnOrganizations.domain.ElectronicAddressField

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

  import EmailAddressField._
  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  List( emailValidation _)
  
}

object EmailAddressField {
  val emailRegex = """\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b""".r

  def emailValidation(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => EmailAddressField.emailRegex findFirstIn v match {
        case None => MustBeEmailAddress(v).fail[Option[String]]
        case _ => value.success[FieldError]
      }
      case None => value.success[FieldError]
    }

}

case class MustBeIpAddress(badValue: String) extends FieldError

class IpAddressField extends Field[String] {

  import IpAddressField._
  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  List( ipAddressValidation _)
  
}

object IpAddressField {
  val ipRegex = """^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$""".r

def ipAddressValidation(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => IpAddressField.ipRegex findFirstIn v match {
        case None => MustBeIpAddress(v).fail[Option[String]]
        case _ => value.success[FieldError]
      }
      case None => value.success[FieldError]
    }
}

