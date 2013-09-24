package sdo.workEffort.domain

import org.joda.time.Hours
import sdo.core.domain.Field

trait Estimate

case class HoursEstimate( hours: Hours) extends Estimate
case class PointsEstiamte( points: Int) extends Estimate

class EstimateField extends Field[Estimate]

object EstimateField {
	
	def apply = new EstimateField
}


