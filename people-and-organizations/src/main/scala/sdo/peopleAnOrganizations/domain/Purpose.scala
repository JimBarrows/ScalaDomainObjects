package sdo.peopleAnOrganizations.domain

import sdo.core.domain.DateRangeField
import sdo.core.domain.Field

case class Purpose(activePeriod: DateRangeField)

class PurposeField extends Field[Purpose] {
}

object PurposeField {
  def apply() = new PurposeField
}
