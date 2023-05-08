package repositories.user

import model.User

trait UserRepository[F[_]] {
  def create(user: User): F[User]
  def findByEmail(email: String):F[Option[User]]
  def findById(id: Long): F[Option[User]]
}
