package sdo.workEffort.domain

import sdo.core.domain.ListField

trait Position {

	val ratesOf = new ListField[PositionRate]()
}
