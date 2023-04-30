package routes.auth

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import model.User
import model.UserJson.jsonFormat
import perycreep.Main.databaseName
import perycreep.Main.system.dispatcher
import repositories.PostgresUserRepository
import services.auth.UserService
import slick.jdbc.JdbcBackend.Database

import scala.util.{Failure, Success}


object authRoutes {
  val db = Database.forConfig(databaseName)
  lazy val userService = new UserService(PostgresUserRepository(db))
  val routes: Route = pathPrefix("users") {
    path("register") {
      post {
        entity(as[User]) { user =>
          val result = userService.createUser(user)
          onComplete(result) {
            case Success(_) => complete("Регистрация прошла успешно")
            case Failure(ex) => complete(ex.getMessage)
          }
        }
      }
    } ~
      path("login") {
        post {
          entity(as[User]) { user => //todo наверное надо сделать DTO
            onSuccess(userService.authenticateUser(user.email, user.hashedPassword)) { result =>
              complete(result)
            }
          }
        }
      }
  }
}
