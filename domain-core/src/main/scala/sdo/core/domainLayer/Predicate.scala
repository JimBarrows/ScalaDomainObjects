package sdo.core.domain

trait Predicate[T] {
	def isSatisfiedBy( candidate :T) = false

}

class CompositePredicate[T]( val left :Predicate[T], val right :Predicate[T] ) extends Predicate[T] {


}

class AndPredicate[T](	override val left :Predicate[T], 
														override val right :Predicate[T]) 
		extends CompositePredicate[T](left, right) {

	override def isSatisfiedBy( candidate: T) = left.isSatisfiedBy( candidate) && right.isSatisfiedBy( candidate)
}

class OrPredicate[T]( override val left :Predicate[T], 
													override val right :Predicate[T]) 
		extends CompositePredicate[T](left, right) {

	override def isSatisfiedBy( candidate: T) = left.isSatisfiedBy( candidate) || right.isSatisfiedBy( candidate)
}

class NotPredicate[T](val spec :Predicate[T]) extends Predicate[T] {

	override def isSatisfiedBy( candidate: T) = ! spec.isSatisfiedBy( candidate) 
}
