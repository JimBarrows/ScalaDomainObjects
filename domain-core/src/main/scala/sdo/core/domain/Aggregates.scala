package sdo.core.domain

/** A cluster of associated objects that we treat as a unit for the purpose of data changes.
*/
trait Aggregate[T] {

	val root :T 

}
