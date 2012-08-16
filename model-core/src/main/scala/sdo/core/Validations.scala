package sdo.core

import scala.util.matching._

case class MustBeNumeric( badValue :String) extends FieldError
case class SsnFieldCannotBeAllZeros( badValue:String) extends FieldError
case class SsnFieldCannotContain666( badValue:String) extends FieldError
case class CannotBeLongerThan( length:Integer, badValue:String) extends FieldError

object ValidationMethods {
	val numericStringRegex = """^\d+$""".r
	val allZerosRegex = """^0+$""".r
	val triple6 = """^666$""".r
	val emptyFieldErrorList = List[FieldError]()

	def allNumeric( value : Option[String]) : List[FieldError] = 
		value.map( v => numericStringRegex findFirstIn v match {
			case None => MustBeNumeric( v) :: Nil
		}).getOrElse( emptyFieldErrorList)

	def notAllZeros( value : Option[String]):List[FieldError] = 
		value.map( v => allZerosRegex findFirstIn v match {
			case Some(f) => SsnFieldCannotBeAllZeros( f) :: Nil
			case None => emptyFieldErrorList
	}).getOrElse( emptyFieldErrorList)

	def not666( value : Option[String]):List[FieldError] = 
		value.map( v => ValidationMethods.triple6 findFirstIn v match {
			case Some(f) => SsnFieldCannotBeAllZeros( f) :: Nil
			case None => emptyFieldErrorList
	}).getOrElse( emptyFieldErrorList)

	def maxLength(length:Integer)( value : Option[String]) :List[FieldError] = 
		value.map( v => if (v.length > length) CannotBeLongerThan( length, v) :: Nil else emptyFieldErrorList ).getOrElse( emptyFieldErrorList)

}
