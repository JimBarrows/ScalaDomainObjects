package sdo.core.domain

import scala.util.matching._

trait ValidationError

trait FieldError extends ValidationError

trait EntityError extends ValidationError

case class CannotBeAllZeros( badValue:String) extends FieldError
case class CannotContain666( badValue:String) extends FieldError
case class CannotBeLongerThan( length:Integer, badValue:String) extends FieldError
case class LessThanMinimum( minimum :BigInt, badValue :BigInt) extends FieldError
case class MustBeNumeric( badValue :String) extends FieldError
case class MustBeAlpha( badValue :String) extends FieldError
case class OnlyOneFieldCanHaveValue( fields: List[Field[_]]) extends EntityError

object EntityValidationMethods {

	val emptyEntityErrorList = List[EntityError]()

	def onlyOneHasValue( fields : List[Field[_]]) (entityObject : Entity) = fields.filter( f => ! f.value.isEmpty ).length match {
		case l if 0 until 2 contains l => emptyEntityErrorList
		case x => OnlyOneFieldCanHaveValue( fields) :: Nil
	}
}

object ValidationMethods {
	val numericStringRegex = """^\d+$""".r
	val alphaStringRegex = """^[a-zA-Z]*$""".r
	val allZerosRegex = """^0+$""".r
	val triple6 = """^666$""".r
	val noErrors = List[FieldError]()

	def allNumeric( value : Option[String]) : List[FieldError] = 
		value.map( v => numericStringRegex findFirstIn v match {
			case Some(f) => noErrors
			case None => MustBeNumeric( v) :: Nil
		}).getOrElse( noErrors)

	def allAlpha( value : Option[String]) : List[FieldError] = 
		value.map( v => alphaStringRegex findFirstIn v match {
			case Some(f) => noErrors
			case None => MustBeAlpha( v) :: Nil
		}).getOrElse( noErrors)

	def notAllZeros( value : Option[String]):List[FieldError] = 
		value.map( v => allZerosRegex findFirstIn v match {
			case Some(f) => CannotBeAllZeros( f) :: Nil
			case None => noErrors
	}).getOrElse( noErrors)

	def not666( value : Option[String]):List[FieldError] = 
		value.map( v => ValidationMethods.triple6 findFirstIn v match {
			case Some(f) => CannotContain666( f) :: Nil
			case None => noErrors
	}).getOrElse( noErrors)

	def maxLength(length:Integer)( value : Option[String]) :List[FieldError] = 
		value.map( v => 
			if (v.length > length) 
				CannotBeLongerThan( length, v) :: Nil 
			else noErrors ).getOrElse( noErrors)

	def minimum( minimum :BigInt)( value :Option[BigInt]) :List[FieldError] =
		value.map( v => if (v < minimum) LessThanMinimum( minimum, value.get) :: Nil else noErrors).getOrElse( noErrors)

}
