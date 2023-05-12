package repositories.user

import model.{User, Users}
import repositories.user.PostgresUserRepository.users
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.{ExecutionContext, Future}
class PostgresUserRepository(db: Database)(implicit ec: ExecutionContext) extends UserRepository[Future] {

  def create(user: User): Future[User] = {
    println("create user")
    val action = (users returning users.map(_.id) into ((user, id) => user.copy(id = id))) += user
    db.run(action)
  }

  def findByEmail(email: String): Future[Option[User]] = {
    val action = users.filter(_.email === email).result.headOption
    db.run(action)
  }

  def findById(id: Long): Future[Option[User]] = {
    val action = users.filter(_.id === id).result.headOption
    db.run(action)
  }
}

object PostgresUserRepository {
  def apply(db: Database)(implicit ec: ExecutionContext): PostgresUserRepository = {
    val tableName = "users"
    val existingTables = db.run(MTable.getTables).map(_.filter(_.name.name == tableName)).map(_.headOption)
    existingTables.map {
      case Some(_) =>
      case None =>
        val setupAction = DBIO.seq(users.schema.create)
        db.run(setupAction)
    }
    new PostgresUserRepository(db)
  }

  val users = TableQuery[Users]
}

