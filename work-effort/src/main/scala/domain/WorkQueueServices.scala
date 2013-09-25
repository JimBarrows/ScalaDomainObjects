package sdo.workEffort.domain

object WorkQueueServices {

	def assign( party: Party, to: WorkQueue) {
		if( ! party.assignedTo.contains( to)) {
			party.assignedTo += to
		}
		if( ! to.processors.contains(party)) {
			to.processors += party
			}
	}
}
