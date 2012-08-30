package sdo.core

import org.scalastuff.scalabeans.Preamble._
import reactive.Observing

trait DomainObject extends Observing 	{

	println("Domain object Begin")
	type ValidationFunction = DomainObject => List[DomainObjectError]

	protected var validationErrorList : List[DomainObjectError] = Nil

	def descriptor =  descriptorOf[DomainObject]

	def fieldList : List[Field[_]] = {
		println("Generating fieldList")
		descriptor.properties.map( p =>
			descriptor.get(this, p.name) match {
				case f:Field[_] => f
   	   case _ =>
			}
		).asInstanceOf[List[Field[_]]]
	}


	def dirty_? = fieldList.exists( f => f.dirty_?)

	def clean:Unit = fieldList.foreach( f => f.makeClean)

	def validationErrors [T <: ValidationError] : List[ValidationError] = validationErrorList ++ fieldList.flatMap( _.validationErrors)

	def validatorList : List[ValidationFunction] = Nil 

	def runValidations(domainObject: Any):Unit  = validationErrorList = validatorList.flatMap(_(this))

	def setup() {
	println("fieldList is " + fieldList)
	fieldList.foreach( f =>  {
		println("f is " + f)
		f.change foreach runValidations 
		})
		}
	println("Domain object End")
}
