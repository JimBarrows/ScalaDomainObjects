package sdo.core.domain

import scalaz._
import Scalaz._
import org.scalastuff.scalabeans.Preamble._
import reactive.Observing
import EntityValidationMethods._

/**
 * An object not fundamentally defined by it's attributes, but rather by a thread of continuity and identity
 */
trait Entity extends Observing with ChangeStateTracking {

  val id: EntityUuidIdField

  def descriptor = descriptorOf[Entity]

  def fieldList: List[Field[_]] = Nil

  /**
   * Sets up the object.  Since this trait is done before a subclass, the fieldList will a list of nulls,
   * which is not what we want, so we have to delay the setup of the obsersvers.
   */
  def setup {
    fieldList.foreach(field => if (field != null) field.change foreach updateChangeState)
  }

  def validate: ValidationNel[EntityError, Entity] = allFieldsValid( this)

  override def makeClean: Unit = {
    fieldList.foreach(f => f.makeClean)
    state = ChangeState.clean
  }

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



