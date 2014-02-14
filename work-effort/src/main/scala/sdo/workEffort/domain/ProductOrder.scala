package sdo.workEffort.domain

import sdo.core.domain.{
  DateField,
  IntegerField,
  ListField,
  MoneyField,
  TextField
}

trait OrderItem {
  def seqId: IntegerField
  def estimatedDeliveryDate: DateField
  def quantity: IntegerField
  def unitPrice: MoneyField
  def shippingInstructions: TextField
  def comment: TextField
  def itemDescription: TextField
  val fulfillerOf = new ListField[OrderRequirementCommitment]()
}

