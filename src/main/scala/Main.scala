package perycreep

import akka.actor.ActorSystem
import model.Users
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import infrastructure.ApplicationConfig
import pureconfig._
import pureconfig.generic.auto._
import routes.auth.AuthRoutes
import routes.organization.OrganizationRoutes
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  lazy val config = ConfigSource.default.at("application").load[ApplicationConfig].getOrElse(ApplicationConfig.default)
  lazy val databaseName = "fixWearDb"
  val db = Database.forConfig(databaseName)
  val users = TableQuery[Users]
  lazy val host = config.host
  lazy val port = config.port

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  private val mainRoutes = AuthRoutes.routes ~ OrganizationRoutes.routes

  val bindingFuture = Http().bindAndHandle(mainRoutes, host, port)

  println(s"Server online at http://$host:$port/")
}
