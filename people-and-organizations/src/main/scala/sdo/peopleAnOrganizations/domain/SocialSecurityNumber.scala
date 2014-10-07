package sdo.peopleAnOrganizations.domain

import scalaz._
import scalaz.Scalaz._
import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

/** Base class for all 3 fields in a SocialSecurityNumber number.  Forces them to be numeric.
*/
class SocialSecurityNumberNumber extends NumericField {

  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  List( notAllZeros _)

}

case class AreaCannotBeBetween734And749(badValue: String) extends FieldError
case class CannotBeOver772(badValue: String) extends FieldError
case class CannotBeBetween987_65_4320To987_65_4329(badValue: SocialSecurityNumber) extends FieldError

object AreaNumber {
  def apply(number: String) = {
    val n = new AreaNumber()
    n value = number
  }

  def unapply(a: AreaNumber) = a.value

  def notBetween734And749(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => {
        if ((734 to 749) contains v.toInt ){
          AreaCannotBeBetween734And749(v).fail[Option[String]]
        } else {
          value.success[FieldError]
        }
      }

      case None => value.success[FieldError]
    }

  def notOver772(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => 
        if( v.toInt > 772) {
          CannotBeOver772(v).fail[Option[String]]
        } else {
          value.success[FieldError]
        }
      case None => value.success[FieldError]
    }
}

class AreaNumber extends SocialSecurityNumberNumber {
  import AreaNumber._
  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  List( notBetween734And749 _, notOver772 _, not666 _, exactLength(3) _)

}

class GroupNumber extends SocialSecurityNumberNumber {

  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  super.validations :+  (maxLength(2) _)

}

object GroupNumber {
  def apply(number: String) = {
    val n = new GroupNumber()
    n.assign(Some(number))
  }

  def unapply(a: GroupNumber) = a.value
}

class SerialNumber extends SocialSecurityNumberNumber {

  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  super.validations :+(maxLength(4) _)

}

object SerialNumber {
  def apply(number: String) = {
    val n = new SerialNumber()
    n.assign(Some(number))
    n
  }

  def unapply(a: SerialNumber) = a.value
}

case class SocialSecurityNumber(area: Option[AreaNumber], group: Option[GroupNumber], serial: SerialNumber)

class SocialSecurityNumberField extends Field[SocialSecurityNumber] {

  import SocialSecurityNumberField._
  override def validations :List[Option[SocialSecurityNumber] => Validation[FieldError, Option[SocialSecurityNumber]]] =  List( not987_65_4320To987_65_4329 _)
  
 

}

object SocialSecurityNumberField {

  def apply( socialSecurityNumber: SocialSecurityNumber)={
    val SocialSecurityNumber = new SocialSecurityNumberField()
    SocialSecurityNumber value = socialSecurityNumber
  }

  def not987_65_4320To987_65_4329(value: Option[SocialSecurityNumber]): Validation[FieldError, Option[SocialSecurityNumber]]=
    value match {
      case Some(v) => v match {
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4320")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4321")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4322")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4323")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4324")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4325")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4326")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4327")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4328")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case SocialSecurityNumber(Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4329")) => CannotBeBetween987_65_4320To987_65_4329(v).fail[Option[SocialSecurityNumber]]
        case _ => value.success[FieldError]
      }
      case None => value.success[FieldError]
    } 
}
