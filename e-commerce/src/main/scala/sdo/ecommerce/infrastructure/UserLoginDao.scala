package sdo.ecommerce.infrastructure

import scala.slick.driver.H2Driver.simple._
import sdo.ecommerce.domain.Url

case class UserLoginRecord(username: String, password: String, webAddress: Url)

object UserLoginTable extends Table[(String, String, String)]("UserLogins") {

  def name = column[String]("username")
  def password = column[String]("password")
  def web_address = column[String]("web_address")

  def * = name ~ password ~ web_address

}

