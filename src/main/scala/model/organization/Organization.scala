package model.organization

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import spray.json.DefaultJsonProtocol.{DoubleJsonFormat, LongJsonFormat, StringJsonFormat, jsonFormat5, optionFormat}
import model.organization.OrganizationJsonFormat.jsonFormat

case class Organization(id: Option[Long], name: String, ownerId: Long, latitude: Double, longitude: Double){
  def toJson = jsonFormat.write(this)
}


class Organizations(tag: Tag) extends Table[Organization](tag, "organizations") {

  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def ownerId = column[Long]("ownerId")

  def latitude = column[Double]("latitude")

  def longitude = column[Double]("longitude")

  override def * : ProvenShape[Organization] = (id, name, ownerId, latitude, longitude) <> (Organization.tupled, Organization.unapply)
}

object OrganizationJsonFormat {
  implicit val jsonFormat = jsonFormat5(Organization.apply)
}
