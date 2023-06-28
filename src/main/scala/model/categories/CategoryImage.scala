package model.categories
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
case class CategoryImage(
            id: Long,
            data: Array[Byte]
          )

class CategoryImageTable(tag: Tag) extends Table[CategoryImage](tag, "category_image") {
  def id: Rep[Long] = column[Long]("id")

  def data: Rep[Array[Byte]] = column[Array[Byte]]("data")

  def * : ProvenShape[CategoryImage] = (id, data) <> (CategoryImage.tupled, CategoryImage.unapply)
}

object CategoryImageQueries {
  lazy val categoriesImages = TableQuery[CategoryImageTable]
}