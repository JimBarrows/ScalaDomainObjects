package sdo.core.domain

/**Represents all objects fo a certain type as a conceptual set.  It acts like a collection,
except with more elaborate querying capability.  Handles the transition to and from storage.
*/
trait Repository[T, E] {

	/** An option containing the first element in the repository that matches the id. */
	def find[E <: EntityIdField[_]]( id : E) :Option[T]

	/** An option containing the first element in the repository that matches the predicate. */
	def find( predicate :Predicate[T]) :Option[T]

	/** The collection consisting of those elements of xs that satisfy the predicate */
	def filter( predicate :Predicate[T]) :List[T]

	/** The collection consisting of those elements of xs that fail to satisfy the predicate */
	def filterNot( predicate :Predicate[T]) :List[T]

	/**Determine if there is a value in the repository that matches the predicated. */
	def exists( predicate :Predicate[T]) :Boolean

	/**Count hoe many items int he repository match the predicate. */
	def count( predicate :Predicate[T]) :Long

	def toList :List[T]

}

/**Allows a client to add new data to the repository. */
trait WritableRepository[T] {

	def save( entity :T) : Unit

	def saveAll( entities :List[T]) : Unit
}

/**Allows a client to remove data from the repository. */
trait DestructibleRepository[T] {

	def remove( entity :T) : Unit

	def removeAll( entities :List[T]) : Unit
}
