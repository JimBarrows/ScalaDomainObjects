package sdo.peopleAndOrganizations

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

class SsnNumber extends NumericField {

	override def validations:List[ValidationFunction] = super.validations ::: notAllZeros _ :: Nil

}

case class AreaCannotBeBetween734And749( badValue:String) extends FieldError
case class CannotBeOver772( badValue:String) extends FieldError
case class CannotBeBetween987_65_4320To987_65_4329 ( badValue:SSN) extends FieldError

class AreaNumber extends SsnNumber {

	override def validations:List[ValidationFunction] = super.validations ::: not666 _ :: maxLength (3) _ :: notBetween734And749 _ :: notOver772 _  :: Nil

	def notBetween734And749( value : Option[String]):List[FieldError] = 
		value.map( v => """^7[34][4-9]$""".r findFirstIn v match {
			case Some(f) => AreaCannotBeBetween734And749( f) :: Nil
			case None => noErrors
		}).getOrElse( noErrors)

	def notOver772( value : Option[String]):List[FieldError] = 
		value.map( v => """^[8-9][8-9][3-9]$""".r findFirstIn v match {
			case Some(f) => CannotBeOver772( f) :: Nil
			case None => noErrors
		}).getOrElse( noErrors)

}

object AreaNumber {
	def apply( number:String) = {
		val n =new AreaNumber()
		n.assign( Some(number))
		n
	}

	def unapply( a:AreaNumber) = a.value
}

class GroupNumber extends SsnNumber {

	override def validations:List[ValidationFunction] = super.validations ::: maxLength (2) _ :: Nil

}

object GroupNumber {
	def apply( number:String) = {
		val n =new GroupNumber()
		n.assign( Some(number))
		n
	}

	def unapply( a:GroupNumber) = a.value
}
class SerialNumber extends SsnNumber {

	override def validations:List[ValidationFunction] = super.validations ::: maxLength (4) _ :: Nil

}

object SerialNumber {
	def apply( number:String) = {
		val n =new SerialNumber()
		n.assign( Some(number))
		n
	}

	def unapply( a:SerialNumber) = a.value
}

sealed case class SSN ( area:Option[AreaNumber], group:Option[GroupNumber], serial:SerialNumber)

class SocialSecurityNumberField extends Field[SSN] {

	override def validations:List[ValidationFunction] = not987_65_4320To987_65_4329 _ :: Nil

	def not987_65_4320To987_65_4329 ( value : Option[SSN]):List[FieldError] = 
		value.map( v => v match {
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4320")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4321")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4322")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4323")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4324")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4325")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4326")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4327")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4328")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case SSN( Some(AreaNumber("987")), Some(GroupNumber("65")), SerialNumber("4329")) => CannotBeBetween987_65_4320To987_65_4329 (v) :: Nil
			case _ => noErrors
		}).getOrElse( noErrors)

}


