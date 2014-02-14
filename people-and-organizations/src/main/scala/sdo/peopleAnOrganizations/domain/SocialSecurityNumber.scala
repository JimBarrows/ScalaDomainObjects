package sdo.peopleAnOrganizations.domain

import scalaz._
import scalaz.Scalaz._
import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

class SsnNumber extends NumericField {

  override def validate = super.validate.flatMap(notAllZeros(_))

}

case class AreaCannotBeBetween734And749(badValue: String) extends FieldError
case class CannotBeOver772(badValue: String) extends FieldError
case class CannotBeBetween987_65_4320To987_65_4329(badValue: SSN) extends FieldError

class AreaNumber extends SsnNumber {

  override def validate = super.validate.flatMap(not666 _).flatMap(maxLength(3) _).flatMap(notBetween734And749 _).flatMap(notOver772 _)

  def notBetween734And749(value: Option[String]): ValidationNel[FieldError, Option[String]] =
    value match {
      case Some(v) => """^7[34][4-9]$""".r findFirstIn v match {
        case None => AreaCannotBeBetween734And749(v).failNel[Option[String]]
        case _ => value.successNel[FieldError]
      }
      case None => value.successNel[FieldError]
    }

  def notOver772(value: Option[String]): ValidationNel[FieldError, Option[String]] =
    value match {
      case Some(v) => """^[8-9][8-9][3-9]$""".r findFirstIn v match {
        case None => CannotBeOver772(v).failNel[Option[String]]
        case _ => value.successNel[FieldError]
      }
      case None => value.successNel[FieldError]
    }

}

object AreaNumber {
  def apply(number: String) = {
    val n = new AreaNumber()
    n.assign(Some(number))
    n
  }

  def unapply(a: AreaNumber) = a.value
}

class GroupNumber extends SsnNumber {

  override def validate = super.validate.flatMap(maxLength(2) _)

}

object GroupNumber {
  def apply(number: String) = {
    val n = new GroupNumber()
    n.assign(Some(number))
    n
  }

  def unapply(a: GroupNumber) = a.value
}
class SerialNumber extends SsnNumber {

  override def validate = super.validate.flatMap(maxLength(4) _)

}

object SerialNumber {
  def apply(number: String) = {
    val n = new SerialNumber()
    n.assign(Some(number))
    n
  }

  def unapply(a: SerialNumber) = a.value
}

sealed case class SSN(area: Option[AreaNumber], group: Option[GroupNumber], serial: SerialNumber)

class SocialSecurityNumberField extends Field[SSN] {

  override def validate = super.validate.flatMap(not987_65_4320To987_65_4329 _)

  def not987_65_4320To987_65_4329(value: Option[SSN]): ValidationNel[FieldError, Option[SSN]] =
    value match {
      case Some(v) => v match {
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4320")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4321")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4322")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4323")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4324")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4325")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4326")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4327")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4328")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case SSN(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4329")) => CannotBeBetween987_65_4320To987_65_4329(v).failNel[Option[SSN]]
        case _ => value.successNel[FieldError]
      }
      case None => value.successNel[FieldError]
    }

}
