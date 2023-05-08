package repositories.organization

import model.organization.{Organization, Organizations}
import repositories.organization.PostgresOrganizationRepository.organizations
import repositories.user.PostgresUserRepository
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

class PostgresOrganizationRepository(db: Database)(implicit val ec: ExecutionContext) extends OrganizationRepository[Future] {
  override def getAllInRange(range: Double): Future[Seq[Organization]] = ???

  override def getAll: Future[Seq[Organization]] = {
    val action = organizations.sortBy(_.name).result
    db.run(action)
  }

  override def getByOwnerId(id: Long): Future[Organization] = ???

  override def getByName(name: String): Future[Organization] = ???
}

object PostgresOrganizationRepository {
  def apply(db: Database)(implicit ec: ExecutionContext): PostgresOrganizationRepository = {
    val tableName = "organizations"
    val existingTables = db.run(MTable.getTables).map(_.filter(_.name.name == tableName)).map(_.headOption)
    existingTables.map {
      case Some(_) =>
      case None =>
        val setupAction = DBIO.seq(organizations.schema.create)
        db.run(setupAction)
    }
    new PostgresOrganizationRepository(db)
  }

  val organizations = TableQuery[Organizations]
}
