package repositories.categories

import model.categories.Category

import scala.concurrent.Future

trait CategoryRepository {

  def getAll: Future[Seq[Category]]
}
