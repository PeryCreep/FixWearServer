package model
import slick.jdbc.PostgresProfile.api._
import spray.json.DefaultJsonProtocol.{BooleanJsonFormat, LongJsonFormat, StringJsonFormat, jsonFormat3, jsonFormat5}
import spray.json.RootJsonFormat
case class User(id: Long, name: String, email: String, hashedPassword: String, isOrganization: Boolean)

case class UserProfileInfo(name: String, email: String, isOrganization: Boolean)
object UserProfileInfo {
  def fromUser(user: User) = {
    UserProfileInfo(user.name, user.email, user.isOrganization)
  }
}

object UserJson {
  implicit val jsonFormat: RootJsonFormat[User] = jsonFormat5(User.apply)
  implicit val userInfoJsonFormat: RootJsonFormat[UserProfileInfo] = jsonFormat3(UserProfileInfo.apply)
}

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email", O.Unique)
  def password = column[String]("password")

  def isOrganization = column[Boolean]("isOrganization")

  def * = (id, name, email, password, isOrganization) <> (User.tupled, User.unapply)
}

