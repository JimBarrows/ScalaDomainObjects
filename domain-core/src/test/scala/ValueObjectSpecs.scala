package sdo.core.specs

import org.specs2.mutable._
import sdo.core.domain.{ ValueObject, Field, NumericField }

class ValueObjectSpecs extends Specification {

  class TestVo(val foo: NumericField, val bar: NumericField) extends ValueObject {
    override def fieldList: List[Field[_]] = foo :: bar :: Nil
    setup
  }

  "A value object" should {

    "only have readonly fields" in {
      val testVo = new TestVo(NumericField(5), NumericField(6))
      testVo.fieldList must contain((f: Field[_]) => !f.writable_?)

    }

    "be equal to another value object if their fields are the same and the types are the same" in {
      new TestVo(NumericField(5), NumericField(6)) must be_==(new TestVo(NumericField(5), NumericField(6)))

    }
    "be unequal to another value object if their fields are the same, but they're different types" in {
      class TestVo2(override val foo: NumericField, override val bar: NumericField) extends TestVo(foo, bar) {
        override def fieldList: List[Field[_]] = foo :: bar :: Nil
      }
      new TestVo(NumericField(5), NumericField(6)) must not be_== (new TestVo2(NumericField(5), NumericField(6)))
    }
  }
}
