package sdo.specs

import org.specs2.mutable.Specification
import org.specs2.execute.Pending
import sdo.core.{DomainObject}//, NumericField, AlphaField}

			class Test extends DomainObject {
//				val numeric = new NumericField()
//				val alpha = new AlphaField()
			}

class DomainObjectSpecs extends Specification {

	"The DomainObject trait " should {
		"provide a list of it's fields" in {
			Pending("Scala reflection is changing soon, and is way complex, so do this later")
		}
		"mark itself dirty when one of it's fields is dirty" in {
/*			val test = new Test()
			test.numeric.assign(Some("1"))
			test.isDirty must beTrue
			*/
		}
	}
}
