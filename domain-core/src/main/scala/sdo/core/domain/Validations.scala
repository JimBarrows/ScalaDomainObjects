package sdo.core.domain

import scalaz._
import Scalaz._

trait FieldError extends ValidationError

trait EntityError extends ValidationError

case class CannotBeAllZeros(badValue: String) extends FieldError
case class CannotContain666(badValue: String) extends FieldError
case class CannotBeLongerThan(length: Int, badValue: String) extends FieldError
case class CannotBeShorterThan(length: Int, badValue: String) extends FieldError
case class LessThanMinimum(minimum: BigInt, badValue: BigInt) extends FieldError
case class MustBeNumeric(badValue: String) extends FieldError
case class MustBeAlpha(badValue: String) extends FieldError
case class MustBeExactly( length: Int, badValue: String) extends FieldError
case class FieldIsReadOnly(newValue: Option[_]) extends FieldError
case class CannotBeNull() extends FieldError
case class CannotBeEmpty() extends FieldError

case class OnlyOneFieldCanHaveValue(fields: List[Field[_]]) extends EntityError
case class FieldsAreInvalid () extends EntityError

object EntityValidationMethods {

  def onlyOneHasValue(fields: List[Field[_]])(e: Entity): Validation[EntityError, Entity] =
    fields.filter(f =>
      !f.value.isEmpty).length match {
      case l if 0 until 2 contains l => e.success[EntityError]
      case x => OnlyOneFieldCanHaveValue(fields).fail[Entity]
    }
  
}

object ValidationMethods {
  val numericStringRegex = """^\d+$""".r
  val alphaStringRegex = """^[a-zA-Z]*$""".r
  val allZerosRegex = """^0+$""".r
  val triple6 = """^666$""".r
  val noErrors = List[FieldError]()

  def allNumeric(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => numericStringRegex findFirstIn v match {
        case None => MustBeNumeric(v).fail[Option[String]]
        case _ => value.success[FieldError]
      }
      case None => value.success[FieldError]
    }

  def allAlpha(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => alphaStringRegex findFirstIn v match {
        case Some(f) => value.success[FieldError]
        case None => MustBeAlpha(v).fail[Option[String]]
      }
      case None => value.success[FieldError]
    }

  def notAllZeros(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => allZerosRegex findFirstIn v match {
        case Some(f) => CannotBeAllZeros(v).fail[Option[String]]
        case None => value.success[FieldError]
      }
      case None => value.success[FieldError]
    }

  def not666(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) => triple6 findFirstIn v match {
        case Some(f) => CannotContain666(v).fail[Option[String]]
        case None => value.success[FieldError] 
      }
      case None => value.success[FieldError]
    }

  def maxLength(length: Int)(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) if (v.length > length) => CannotBeLongerThan(length, v).fail[Option[String]]
      case _ => value.success[FieldError]
    }

  def minLength(length: Int)(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) if (v.length > length) => CannotBeShorterThan(length, v).fail[Option[String]]
      case _ => value.success[FieldError]
    }

  def exactLength(length: Int)(value: Option[String]): Validation[FieldError, Option[String]] =
    value match {
      case Some(v) if (v.length != length) => MustBeExactly(length, v).fail[Option[String]]
      case _ => value.success[FieldError]
  }

  def minimum(minimum: Int)(value: Option[Int]): Validation[FieldError, Option[Int]] =
    value match {
      case Some(v) if (v < minimum) => LessThanMinimum(minimum, v).fail[Option[Int]]
      case _ => value.success[FieldError]
    }

}

