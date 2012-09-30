package sdo.core.domain

/** An object that describes things, for which we only care about the attributes of an element of the model.
  * It must be immutable.
*/
trait ValueObject {

	def fieldList : List[Field[_]] = Nil

	fieldList.foreach( field => field.makeReadOnly)

	override def equals( that :Any) = that  match {
		case vo :ValueObject => (vo.getClass == getClass) && (vo.fieldList == fieldList)
		case _ => false
	}
}
