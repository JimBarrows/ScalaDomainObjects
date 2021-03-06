package sdo.core.domain

import scalaz._
import Scalaz._

import EntityValidationMethods._

/**
 * An object not fundamentally defined by it's attributes, but rather by a thread of continuity and identity
 */
trait Entity  extends ChangeStateTracking {

  val id: EntityUuidIdField

  def fieldList: List[Field[_]] = Nil

  /**
   * Sets up the object.  Since this trait is done before a subclass, the fieldList will a list of nulls,
   * which is not what we want, so we have to delay the setup of the obsersvers.
   */
  def setup {
    
  }

  def validate(e: Entity): ValidationNel[EntityError, Entity] =  e.successNel[EntityError]

  override def makeClean: Unit = {
    fieldList.foreach(f => f.makeClean)
    state = ChangeState.clean
  }

  override def dirty_? = fieldList.exists(_.dirty_?)

  override def equals(that: Any) = that match {
    case entity: Entity => entity.id == this.id
  }

  /**
   * Updates the current state based on the fields state. Any field that is dirty, the Entity is dirty, otherwise
   * it's clean.
   */
  protected def updateChangeState(d: Any): Unit =
    if (fieldList.exists(_.dirty_?)) state = ChangeState.dirty else state = ChangeState.clean

}



