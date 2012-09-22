package sdo.specs

import org.specs2.mutable._
import sdo.core.domain.{ValueObject, Field, NumericField}

class ValueObjectSpecs extends Specification {

	class TestVo( val foo :NumericField, val bar :NumericField) extends ValueObject {
		override def fieldList :List[Field[_]] = foo :: bar :: Nil
	}

	"A value object" should {
		
		"only have readonly fields" in {
			val testVo = new TestVo( NumericField(5), NumericField(6))
			testVo.fieldList.foreach( _.writable_?  must beFalse)
		}
	}	
}
