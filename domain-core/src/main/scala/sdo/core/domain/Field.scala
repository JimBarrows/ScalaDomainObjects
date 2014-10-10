package sdo.core.domain

import scala.math.BigInt
import scala.collection.mutable.MutableList
import scalaz._
import std.AllInstances._
import std.list._
import Scalaz._
import java.util.{ Currency, Locale, UUID }

import com.github.nscala_time.time.Imports._
import org.joda.time.DateMidnight

import ValidationMethods._

class Field[T] extends ChangeStateTracking {

  protected var data: Option[T] = None

  private var writable = true

  def writable_? = writable

  def value: Option[T] = data

  import std.list._

  def validations :List[Option[T] => Validation[FieldError, Option[T]]] = Nil

  def assign(newValue: Option[T]): ValidationNel[FieldError, Field[T]]={

    if ( data.equals(newValue)) {

      this.successNel[FieldError]

    } else  if (writable_? || ! initialized_?) {  

      
      validations.traverseU(_ andThen (_.toValidationNel) apply newValue) map { 
        case c :: _ => {
      
          makeDirty
      
          data = newValue
          this
        }
        case Nil => {
      
          makeDirty
      
          data = newValue
          this
        }
      }
    } else if (! writable_? && initialized_?){
      
      FieldIsReadOnly(newValue).failNel[Field[T]]  
          
    } else {
      throw new IllegalStateException ("Field is in weird state.")
    }
  }

  //def value_=(newValue: Option[T]) = assign(newValue)

  def value_=(newValue: T) = if (newValue == null) assign(None) else assign(Some(newValue))

  override def equals(that: Any) = {
    (that.getClass == this.getClass) && (that.asInstanceOf[Field[T]].value == this.value)
  }

  def makeReadOnly: Unit = writable = false

  override def toString = "Field[T]( %s)".format(data)
}

class EntityIdField[T](id: T) extends Field[T] {
  override def writable_? = false

  value = (id)
}

class EntityUuidIdField(val id: UUID) extends EntityIdField[UUID](id) {
  override def toString = "EntityUuidIdField( %s)".format(data)
}

object EntityUuidIdField {
  def apply(): EntityUuidIdField = {
    val i = UUID.randomUUID()
    new EntityUuidIdField(i)
  }
}

/**
 * A Field consisting entirely of numbers
 */
class NumericField extends Field[String] {          
  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  List( allNumeric _ )
  override def toString = "NumericField( %s)".format(data)
}

object NumericField {

  def apply = new NumericField()

  def apply(value: String) = {
    val nf = new NumericField()
    nf.value = value    
  }

  def apply(value: Integer) = {
    val nf = new NumericField()
    nf.value = (value.toString)
    nf
  }
 
}

/**
 * A Field consisting entirely of alphabetic characters, and punctuation
 */
class AlphaField extends Field[String] {

  override def validations :List[Option[String] => Validation[FieldError, Option[String]]]  =  List(allAlpha _)
    
  override def toString = "AlphaField( %s)".format(data)
}

/**
 * A Field that is either true or false.
 */
class BooleanField extends Field[Boolean] {
  override def toString = "BooleanField( %s)".format(data)
}

object BooleanField {

  def apply = new BooleanField()

  def apply(init: Boolean) = {
    val bf = new BooleanField()
    bf.value = (init)
    bf
  }
}

/**
 * A class representing the mathematical concept of Integers.
 */
class IntegerField extends Field[Int] {
  override def toString = "IntegerField( %s)".format(data)
}

object IntegerField {
  def apply(value: Int) = {
    val intField = new IntegerField()
    intField value = value
    intField
  }
}

/** A field that can be anything that will fit in a string, but isn't that long.*/
class ShortTextField extends Field[String] {
  //override def validations :List[ValidationFunction] = maxLength( 140) _:: Nil
  override def toString = "ShortTextField( %s)".format(data)
}

object ShortTextField {
  def apply(text: String) = {
    val shortTextField = new ShortTextField()
    shortTextField.value = text
    shortTextField
  }

  def apply() = new ShortTextField()
}

/**A field that represents notes and other large text.*/
class TextField extends Field[String] {
  override def toString = "TextField( %s)".format(data)
}

object TextField {
  def apply(): TextField = new TextField()
}

class DateTimeField extends Field[DateTime] {
  override def toString = "DateTimeField( %s)".format(data)
}

object DateTimeField {
  def apply(): DateTimeField = new DateTimeField()
}

class DateField extends Field[DateMidnight]

object DateField {
  def apply = new DateField()
}

class ListField[T] extends Field[MutableList[T]] {

  data = Some(new MutableList[T]())

  def +=(newValue: T): Unit = add(newValue)

  def add(newValue: T): Unit = data.map(l => {

    if (writable_? || !initialized_?) {
      l += newValue
      makeDirty
    }
  })

  def length: Int = list.length

  def find(p: (T) => Boolean): Option[T] = data.map(_.find(p)).getOrElse(None)

  def list: List[T] = data.getOrElse(new MutableList[T]).toList

  def exists(predicate: T => Boolean): Boolean = data.map(_.exists(predicate)).getOrElse(false)

  def contains(value: T): Boolean = data.map(_.contains(value)).getOrElse(false)

  def remove(value: T): Unit = data = data.map(l => {
    if (writable_? || !initialized_?) {
      val newList = l.diff(value :: Nil)
      makeDirty
      newList
    } else {
      l
    }
  })

  override def toString = "ListField( %s)".format(data)
}

case class Range[T](from: Option[T], thru: Option[T])

class RangeField[T] extends Field[Range[T]] {
}

case class DateRange(from: DateMidnight, thru: Option[DateMidnight])

class DateRangeField extends Field[DateRange] {
  def isValid() = value.map( v => 
    ((v.from.isBefore( DateMidnight.now) || 
      v.from.isEqual(DateMidnight.now)) && 
    v.thru.map( t => t.isBefore( DateMidnight.now) || 
      t.isEqual( DateMidnight.now)).getOrElse(true))).getOrElse(true)
}

object DateRangeField {

  def apply(): DateRangeField = {
    val drf = new DateRangeField()
    drf.value = new DateRange(DateMidnight.now, None)
    drf
  }
}

case class DateTimeRange(from: DateTime, thru: Option[DateTime])

class DateTimeRangeField extends Field[DateTimeRange] {
}

/**
 * Immutable money class.
 */
case class Money(amount: BigDecimal, currency: Currency) {

  /**
   * Convience method for creating the result of an operation.
   */
  private def newMoney(amount: BigDecimal) = Money(amount, this.currency)

  def +(money: Money) = {
    require(money.currency.equals(currency), "Currencies must be the same. this: %s that:%s".format(this.currency, money.currency))
    newMoney(amount + money.amount)
  }

  def -(money: Money) = {
    require(money.currency.equals(currency), "Currencies must be the same. this: %s that:%s".format(this.currency, money.currency))
    newMoney(amount - money.amount)
  }
}

object Money {

  def $(amount: BigDecimal) = Money(amount, Currency.getInstance(Locale.US))
}

class MoneyField extends Field[Money] {
}

object MoneyField {
  def apply() = new MoneyField()
}

