package sdo.core.domain

import scala.math.BigInt
import scala.collection.mutable.MutableList
import java.util.UUID

import org.scala_tools.time.Imports._
import org.joda.time.DateMidnight

import reactive.{Signal, EventStream, EventSource, Observing, CanForward, Forwardable, NamedFunction}
import ValidationMethods._


trait Validation {

	type ValidationFunction = () => List[ValidationError]

	protected var validationErrorList :List[ValidationError] = Nil

	def validations :List[ValidationFunction] = Nil

	def validate :Unit =  validationErrorList = validations.flatMap( _() ).removeDuplicates
	
	def validationErrors = validationErrorList
}

trait ValidationError
