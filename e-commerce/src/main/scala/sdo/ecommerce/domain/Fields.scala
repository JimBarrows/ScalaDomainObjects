package sdo.ecommerce.domain

import org.apache.commons.codec.digest.DigestUtils._
import sdo.core.domain._

import scalaz._
import std.AllInstances._
import std.list._
import Scalaz._

import sdo.core.domain.ValidationMethods._
/**
 * When an account is first created, it hasn't been verified yet.  From the unverified state it can move to
 * either the active or inactive state.  Active state can only move to inactive.  Inactive can move to active
 * according to business rules.
 */
object AccountStatusType extends Enumeration {
  type AccountStatusType = Value
  val unverified, active, inactive = Value
}

import AccountStatusType._

class AccountStatusField extends Field[AccountStatusType] {
}

object AccountStatusField {

  def apply() = {
    val asv = new AccountStatusField
    asv.value = unverified
    asv
  }
}

class PasswordField extends TextField {

  override def assign(newValue: Option[String]): ValidationNel[FieldError, PasswordField]= {
    val salted =  sha512Hex(newValue.getOrElse("") + PasswordField.salt)
    super.assign( Some( salted)).asInstanceOf[ValidationNel[FieldError, PasswordField]]
  }
}

object PasswordField {
  def salt = "r_Z4$ko-NdxM[_S?:Zgwz hdOfO9={"
  def apply(text: String) = new PasswordField value = text
  def apply() = new PasswordField

}

class UrlField extends Field[Url] {
}

object UrlField {
  def apply() = new UrlField()
}
