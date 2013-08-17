package sdo.peopleAndOrganizations

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

trait Classifications[ T <: Classification] {

	val classfications = new ListField[T]()
}

abstract class Classification 

abstract class PersonClassification extends Classification

case class IncomeClassification( incomeRange : Range[Money]) extends PersonClassification

abstract class OrganizationClassification extends Classification

case class MinorityClassification( value :String)  extends OrganizationClassification
