package sdo.workEffort.domain


import org.joda.time.{ Hours,
												DateTime}

import org.joda.time.Hours.hoursBetween

import sdo.core.domain.{ DateRangeField,
												Field,
												Entity,
												EntityUuidIdField,
												IntegerField,
												ListField,
												MoneyField,
												TextField}

case class TimeEntry( from: DateTime, thru: DateTime, comment: TextField) {

	def hours: Hours = hoursBetween( from, thru)
}

class TimeEntryListField extends ListField[TimeEntry]

object TimeEntryListField {

	def apply = new TimeEntryListField()
}


class TimeSheet (initialId: EntityUuidIdField, forTheHoursOf: Worker) extends Entity {

	override val id = initialId

	val period = DateRangeField

	val comment = TextField

	val composedOf = TimeEntryListField

	val involving = TimeSheetRoleList
}


class TimeSheetRole( forTimeSheet: TimeSheet, of: Party)

case class Approver( forTimeSheet: TimeSheet, of: Party) extends TimeSheetRole( forTimeSheet, of)
case class Manager( forTimeSheet: TimeSheet, of: Party) extends TimeSheetRole( forTimeSheet, of)

class TimeSheetRoleList extends ListField[ TimeSheetRole]

object TimeSheetRoleList {
	def apply = new TimeSheetRoleList()
	}
