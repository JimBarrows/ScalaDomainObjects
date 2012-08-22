package sdo.core

import scala.util.matching._

trait FieldError

case class MustBeNumeric( badValue :String) extends FieldError
case class MustBeAlpha( badValue :String) extends FieldError
case class CannotBeAllZeros( badValue:String) extends FieldError
case class CannotContain666( badValue:String) extends FieldError
case class CannotBeLongerThan( length:Integer, badValue:String) extends FieldError

object ValidationMethods {
	val numericStringRegex = """^\d+$""".r
	val alphaStringRegex = """^[a-zA-Z]*$""".r
	val allZerosRegex = """^0+$""".r
	val triple6 = """^666$""".r
	val emptyFieldErrorList = List[FieldError]()

	def allNumeric( value : Option[String]) : List[FieldError] = 
		value.map( v => numericStringRegex findFirstIn v match {
			case Some(f) => emptyFieldErrorList
			case None => MustBeNumeric( v) :: Nil
		}).getOrElse( emptyFieldErrorList)

	def allAlpha( value : Option[String]) : List[FieldError] = 
		value.map( v => alphaStringRegex findFirstIn v match {
			case Some(f) => emptyFieldErrorList
			case None => MustBeAlpha( v) :: Nil
		}).getOrElse( emptyFieldErrorList)

	def notAllZeros( value : Option[String]):List[FieldError] = 
		value.map( v => allZerosRegex findFirstIn v match {
			case Some(f) => CannotBeAllZeros( f) :: Nil
			case None => emptyFieldErrorList
	}).getOrElse( emptyFieldErrorList)

	def not666( value : Option[String]):List[FieldError] = 
		value.map( v => ValidationMethods.triple6 findFirstIn v match {
			case Some(f) => CannotContain666( f) :: Nil
			case None => emptyFieldErrorList
	}).getOrElse( emptyFieldErrorList)

	def maxLength(length:Integer)( value : Option[String]) :List[FieldError] = 
		value.map( v => if (v.length > length) CannotBeLongerThan( length, v) :: Nil else emptyFieldErrorList ).getOrElse( emptyFieldErrorList)

}
