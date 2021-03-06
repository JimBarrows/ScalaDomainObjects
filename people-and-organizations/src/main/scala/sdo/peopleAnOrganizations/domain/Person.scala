package sdo.peopleAnOrganizations.domain

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._



class Person(initialId: EntityUuidIdField) extends Party(initialId)
                                            with RolesToPlay[PersonRole]{

  val gender = GenderField
  val birthday = DateField

}

object Gender extends Enumeration {
  type Gender = Value
  val Male, Femail = Value
} 

import Gender._
class GenderField extends Field[Gender]

object GenderField {
  def apply = new GenderField()
}
