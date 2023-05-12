package services.auth

import model.User
import repositories.user.UserRepository
import utils.WebTokensUtils.TokensUtils.{Token, generateToken}

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

  def authenticateUser(email: String, password: String): Future[Either[String, Token]] = {
    findUserByEmail(email).map {
      case Some(user) if password == user.hashedPassword => Right(generateToken(user.id.toString))
      case Some(_) => Left("Пароль неверный")
      case None => Left("Пользователь не найден")
    }
  }
}