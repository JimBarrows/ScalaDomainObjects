package sdo.core

trait DomainObject {

	private var dirty = false

	def fieldList : List[Field[_]] =  Nil

	def isDirty = dirty
}
