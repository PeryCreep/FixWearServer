package services.auth

import model.UserJson.userInfoJsonFormat
import model.{User, UserProfileInfo}
import repositories.user.UserRepository
import services.auth.Responses.LoginResponse
import spray.json.DefaultJsonProtocol.{StringJsonFormat, jsonFormat2}
import spray.json.RootJsonFormat
import utils.WebTokensUtils.TokensUtils.generateToken

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserService(userRepository: UserRepository[Future]) {
  def createUser(newUser: User): Future[User] = {
    findUserByEmail(newUser.email).flatMap {
        case Some(_) =>
          Future.failed(new RuntimeException("Пользователь уже существует"))
        case None =>
          val user = User(
            id = null.asInstanceOf[Long],
            email = newUser.email,
            hashedPassword = newUser.hashedPassword,
            name = newUser.name,
            isOrganization = newUser.isOrganization
          )
          userRepository.create(user)
    }
  }

  def findUserByEmail(email: String): Future[Option[User]] = {
    userRepository.findByEmail(email)
  }

  def findUserById(id: Long): Future[Option[User]] = {
    userRepository.findById(id)
  }

  def authenticateUser(email: String, password: String): Future[Either[String, LoginResponse]] = {
    findUserByEmail(email).map {
      case Some(user) if password == user.hashedPassword =>
        Right(LoginResponse(generateToken(user.id.toString), UserProfileInfo.fromUser(user)))
      case Some(_) => Left("Пароль неверный")
      case None => Left("Пользователь не найден")
    }
  }
}

object Responses {
  case class LoginResponse(token: String, user: UserProfileInfo)

  implicit val loginResponseJsonFormat: RootJsonFormat[LoginResponse] = jsonFormat2(LoginResponse.apply)
}