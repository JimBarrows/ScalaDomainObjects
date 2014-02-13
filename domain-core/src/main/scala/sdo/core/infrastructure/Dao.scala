package sdo.core.infrastructure

/**  This represents one table which can be accessed. T is the type representing the table, and K represents the type of the primary key*/
trait Dao[T, K] {

	def create( newRow :T) :Either[ DataAccessError, T] 
	def read( primaryKey :K) :Either[ DataAccessError, Option[T]] 
	def update( row :T) :Either[ DataAccessError, T]
	def delete( primaryKey :K) :Option[ DataAccessError]
}

trait DataAccessError {
}
