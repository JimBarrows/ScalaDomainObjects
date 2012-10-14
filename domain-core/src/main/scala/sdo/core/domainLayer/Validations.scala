package sdo.core.domain

import scala.util.matching._

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

	val noErrors = List[EntityError]()

	def onlyOneHasValue( fields : List[ Field[ _]])(e :Option[Entity]) :List[EntityError] = 
		fields.filter( f => 
			! f.value.isEmpty ).length match {
					case l if 0 until 2 contains l => noErrors
					case x => OnlyOneFieldCanHaveValue( fields) :: Nil
				}
		
}

object ValidationMethods {
	val numericStringRegex = """^\d+$""".r
	val alphaStringRegex = """^[a-zA-Z]*$""".r
	val allZerosRegex = """^0+$""".r
	val triple6 = """^666$""".r
	val noErrors = List[FieldError]()

	def allNumeric( field : Field[ String]) ( ) : List[FieldError] = 
		field.value.map( v => numericStringRegex findFirstIn v match {
			case Some(f) => noErrors
			case None => MustBeNumeric( v) :: Nil
		}).getOrElse( noErrors)

	def allAlpha( field : Field[ String]) ( ) : List[FieldError] = 
		field.value.map( v => alphaStringRegex findFirstIn v match {
			case Some(f) => noErrors
			case None => MustBeAlpha( v) :: Nil
		}).getOrElse( noErrors)

	def notAllZeros( field : Field[  String]) ( ):List[FieldError] = 
		field.value.map( v => allZerosRegex findFirstIn v match {
			case Some(f) => CannotBeAllZeros( f) :: Nil
			case None => noErrors
	}).getOrElse( noErrors)

	def not666( field : Field[  String]) ( ):List[FieldError] = 
		field.value.map( v => ValidationMethods.triple6 findFirstIn v match {
			case Some(f) => CannotContain666( f) :: Nil
			case None => noErrors
	}).getOrElse( noErrors)

	def maxLength(length:Integer, field :Field[  String]) ( ) :List[FieldError] = 
		field.value.map( v => 
			if (v.length > length) 
				CannotBeLongerThan( length, v) :: Nil 
			else noErrors ).getOrElse( noErrors)

	def minimum( minimum :BigInt, field :IntegerField) :List[FieldError] =
		field.value.map( v => if (v < minimum) LessThanMinimum( minimum, field.value.get) :: Nil else noErrors).getOrElse( noErrors)

}
