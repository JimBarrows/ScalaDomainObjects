package sdo.peopleAndOrganizations

import sdo.core.domain._
import sdo.core.domain.ValidationMethods._

trait Classifications {

	val classfications = new ListField[ClassificationField]()
}

abstract class Classification 

class ClassificationField extends Field[Classification]

abstract class PersonClassification extends Classification

case class IncomeClassification( incomeRange : Range[Money]) extends PersonClassification

abstract class OrganizationClassification extends Classification

case class MinorityClassification( value :String)  extends OrganizationClassification
