package sdo.core

trait Specification[T] {
	def isSatisfiedBy( candidate :T) = false

}

class CompositeSpecification[T]( val left :Specification[T], val right :Specification[T] ) extends Specification[T] {


}

class AndSpecification[T](	override val left :Specification[T], 
														override val right :Specification[T]) 
		extends CompositeSpecification[T](left, right) {

	override def isSatisfiedBy( candidate: T) = left.isSatisfiedBy( candidate) && right.isSatisfiedBy( candidate)
}

class OrSpecification[T]( override val left :Specification[T], 
													override val right :Specification[T]) 
		extends CompositeSpecification[T](left, right) {

	override def isSatisfiedBy( candidate: T) = left.isSatisfiedBy( candidate) || right.isSatisfiedBy( candidate)
}

class NotSpecification[T](val spec :Specification[T]) extends Specification[T] {

	override def isSatisfiedBy( candidate: T) = ! spec.isSatisfiedBy( candidate) 
}
