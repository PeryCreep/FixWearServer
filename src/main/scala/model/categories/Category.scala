package model.categories
import model.categories.CategoryImageQueries.categoriesImages
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
case class Category(id: Long = 0L, name: String, imageId: Long)

class CategoryTable(tag: Tag) extends Table[Category](tag, "categories") {

  def id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
  def name: Rep[String] = column[String]("name")

  def imageId: Rep[Long] = column[Long]("imageId")
  def * : ProvenShape[Category] = (id, name, imageId) <> (Category.tupled, Category.unapply)

  def imageFk = foreignKey("imageFk", imageId, categoriesImages)(_.id)
}

object CategoryQueries {
  lazy val categories = TableQuery[CategoryTable]
}