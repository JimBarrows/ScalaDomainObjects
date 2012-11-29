package sdo.core.infrastructure

trait Dao[T] {

	def create( newRow :T) : Either[ DataAccessError, T] 
}

class DataAccessError {
}
