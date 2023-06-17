package repositories.organization

import model.organization.{Organization, Organizations}
import repositories.organization.PostgresOrganizationRepository.organizations
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}
import scala.math._

class PostgresOrganizationRepository(db: Database)(implicit val ec: ExecutionContext) extends OrganizationRepository[Future] {
  override def getAllInRange(range: Double, latitude: Double, longitude: Double): Future[Seq[Organization]] = ???
//  {//todo нужно вынести в класс Point
//      val action = organizations.filter { org =>
//      val first = org.latitude - latitude
//      val second = org.longitude - longitude
////      val result = Functions.sqrt(pow(first.toDouble, 2) + second)
//    }
//  }

  override def getAll: Future[Seq[Organization]] = {
    val action = organizations.sortBy(_.name).result
    db.run(action)
  }

  override def getByOwnerId(id: Long): Future[Option[Organization]] = {
    val action = organizations.filter(_.ownerId === id).result.headOption
    db.run(action)
  }

  override def getByName(name: String): Future[Option[Organization]] = {
    val action = organizations.filter(_.name === name).result.headOption
    db.run(action)
  }
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
