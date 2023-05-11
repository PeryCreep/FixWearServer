package perycreep

import akka.actor.ActorSystem
import model.Users
import akka.http.scaladsl._
import akka.stream.ActorMaterializer
import routes.auth.AuthRoutes
import routes.organization.OrganizationRoutes
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery
import akka.http.scaladsl.server.Directives._

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  val host = "localhost"//todo сделать конфигурацию
  lazy val databaseName = "fixWearDb"
  val db = Database.forConfig(databaseName)
  val users = TableQuery[Users]

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  private val mainRoutes = AuthRoutes.routes ~ OrganizationRoutes.routes

  val bindingFuture = Http().bindAndHandle(mainRoutes, host, 8080)

  println(s"Server online at http://$host:8080/")
}
