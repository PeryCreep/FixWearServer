package repositories.categories

import model.categories.Category
import model.categories.CategoryQueries.categories
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}
class PostgresCategoryRepository(db: Database)(implicit val ec: ExecutionContext) extends CategoryRepository{
  override def getAll: Future[Seq[Category]] = db.run(categories.result)
}
