package sdo.workEffort.domain

import org.joda.time.Period
import sdo.core.domain.{ DateRange,
												Field,
												Money}

class Rate( effective: DateRange, amount: Money, per: Period)

case class AssignmentRate( effective: DateRange, amount: Money, per: Period) 
		extends Rate( effective, amount, per)

case class PartyRate( effective: DateRange, amount: Money, per: Period) 
		extends Rate( effective, amount, per)

case class PositionRate( effective: DateRange, amount: Money, per: Period) 
		extends Rate( effective, amount, per)
