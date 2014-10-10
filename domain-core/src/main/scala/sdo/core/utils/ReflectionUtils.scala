package sdo.core.utils

import scala.reflect.runtime.universe._

object ReflectionUtils {
	def getType[T: TypeTag](obj: T) = typeOf[T]
}