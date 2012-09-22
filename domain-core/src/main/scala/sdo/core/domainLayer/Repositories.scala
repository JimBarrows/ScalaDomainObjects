package sdo.core.domain

/**Represents all objects fo a certain type as a conceptual set.  It acts like a collection,
except with more elaborate querying capability.  Handles the transition to and from storage.
*/
trait Repository[T] {

	/** An option containing the first element in the repository that matches the id. */
	def find( id : EntityIdField[_]) :Option[T]

	/** An option containing the first element in the repository that matches the predicate. */
	def find( predicate :Predicate[T]) :Option[T]

	/** The collection consisting of those elements of xs that satisfy the predicate */
	def filter( predicate :Predicate[T]) :List[T]

	/** The collection consisting of those elements of xs that fail to satisfy the predicate */
	def filterNot( predicate :Predicate[T]) :List[T]

	/**Returns a map of the repository grouped by the function provided*/
	def groupBy[K] (f: (T) => K):Map[K, T]

	/**Determine if there is a value in the repository that matches the predicated. */
	def exists( predicate :Predicate[T]) :Boolean

	/**Count hoe many items int he repository match the predicate. */
	def count( predicate :Predicate[T]) :Long

	/**Apply a binary operation <code>op</code> between successive elements of the repository going left to right and starting with z*/
	def foldLeft[B](z :B)(op :(B,T) => B) :B

	/**Apply a binary operation <code>op</code> between successive elements of the repository going right to left and starting with z*/
	def foldRight[B](z :B)(op :(B,T) => B) :B

	def toList :List[T]

}
