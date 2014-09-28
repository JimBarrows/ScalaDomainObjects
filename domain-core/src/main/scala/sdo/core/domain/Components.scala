package sdo.core.domain

import scala.math.BigInt
import scala.collection.mutable.MutableList
import java.util.UUID

import org.joda.time.DateMidnight

import ValidationMethods._

/**
 * Provides a common interface for validating an object.
 */

trait ValidationError

object ChangeState extends Enumeration {
  type ChangeState = Value
  var uninitialized, dirty, clean = Value
}

/**
 * Tracks if an object is initialized, clean or dirty.
 */
trait ChangeStateTracking {

  protected var state = ChangeState.uninitialized
  def dirty_? : Boolean = state == ChangeState.dirty
  def clean_? : Boolean = (state == ChangeState.clean || state == ChangeState.uninitialized)
  def initialized_? : Boolean = state != ChangeState.uninitialized

  def makeClean { state = ChangeState.clean }

  def makeDirty { state = ChangeState.dirty }

}