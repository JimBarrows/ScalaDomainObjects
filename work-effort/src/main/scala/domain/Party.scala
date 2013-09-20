package sdo.workEffort.domain

import org.joda.time.Hours
import sdo.core.domain.{DateField,
												DateRangeField,
												DateTimeField,
												Field,
												Entity,
												EntityUuidIdField,
												IntegerField,
												ListField,
												MoneyField,
												TextField}

trait Facility

trait Party{

	def id: EntityUuidIdField

	def actingAs: List[PartyRole]
}

trait PartyRole{
}

trait Worker extends PartyRole{

	def submitting: List[TimeSheet]
}

trait Employee extends Worker{
}

trait Contractor extends Worker{
}
