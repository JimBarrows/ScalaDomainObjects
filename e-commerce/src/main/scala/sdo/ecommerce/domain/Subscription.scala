package sdo.ecommerce.domain

import sdo.core.domain._
import sdo.peopleAnOrganizations.domain._

class Subscription ( initialId :EntityUuidIdField) extends Entity {

	override val id = initialId

	val period = DateRangeField()	

	val fulfilledVia = new ListField[SubscriptionActivity]()

	val needType = NeedType()
	
	val orderedVia = new ListField[OrderItem] ()
	
	val originatingFrom = CommunicationEvent()
	
	val partyNeed = PartyNeed ()
	
	val product = Product ();
	
	val productCategory = ProductCategory()	
	
	val sendTo = ContactMechanism ();
	
	val subscriber = RoleField ()
	
	val subscriptionFor =RoleField()
}


class SubscriptionActivity {

}

case class NeedType() {}

case class OrderItem() {}

case class CommunicationEvent() {}

case class PartyNeed() {}

case class Product() {}

case class ProductCategory() {}

case class ContactMechanism() {}