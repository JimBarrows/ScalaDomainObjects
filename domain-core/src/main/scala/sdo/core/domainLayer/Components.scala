package sdo.core.domain

import scala.math.BigInt
import scala.collection.mutable.MutableList
import java.util.UUID

import org.scala_tools.time.Imports._
import org.joda.time.DateMidnight

import reactive.{Signal, EventStream, EventSource, Observing, CanForward, Forwardable, NamedFunction}
import ValidationMethods._


/**Provides a common interface for validating an object.
*/
trait Validation {

	type ValidationFunction = () => List[ValidationError]

	protected var validationErrorList :List[ValidationError] = Nil

	def validations :List[ValidationFunction] = Nil

	def validate :Unit =  validationErrorList = validations.flatMap( _() ).removeDuplicates
	
	def validationErrors = validationErrorList
}

trait ValidationError

/** Tracks if an object is initialized, clean or dirty.
  */
trait ChangeStateTracking {

	object ChangeState extends Enumeration {
		type ChangeState = Value
		var uninitialized, dirty, clean = Value
	}

	protected var state = ChangeState.uninitialized
	def dirty_? :Boolean = state == ChangeState.dirty
	def clean_? :Boolean = state == ChangeState.clean
	def initialized_? :Boolean = state != ChangeState.uninitialized

	def makeClean { state = ChangeState.clean}

	def makeDirty { state = ChangeState.dirty}

}
