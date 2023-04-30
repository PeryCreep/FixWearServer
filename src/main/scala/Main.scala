package perycreep

import akka.actor.ActorSystem
import model.Users
import akka.http.scaladsl._
import akka.stream.ActorMaterializer
import routes.auth.authRoutes.routes
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  lazy val databaseName = "fixWearDb"
  val db = Database.forConfig(databaseName)
  val users = TableQuery[Users]

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  private val mainRoutes = routes

  val bindingFuture = Http().bindAndHandle(mainRoutes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/")
}
