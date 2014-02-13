package sdo.core.test

import scala.collection.mutable.HashMap
import sdo.core.infrastructure.{Dao, DataAccessError}
import sdo.core.domain.{Entity, EntityUuidIdField}

/** This dao implementation uses a list as it's data store, making it ideal for testing repositories without
  * normal backend infrastructure.
  */
trait ListDao extends Dao[ Entity, EntityUuidIdField] {

	val data = HashMap[EntityUuidIdField, Entity]()

	override def create( newRow :Entity) :Either[ DataAccessError, Entity]  = {
		data+= ((newRow.id, newRow))
		Right( newRow)
	}

	override def read( primaryKey: EntityUuidIdField) :Either[ DataAccessError, Option[Entity]]  = {
		Right(data.get(primaryKey))
	}

	override def update( row :Entity) :Either[ DataAccessError, Entity] = {
		data+= ((row.id, row))
		Right( row)
	}

	override def delete( primaryKey :EntityUuidIdField) :Option[ DataAccessError] = {
		data -=( primaryKey)
		None
	}


}
