package repositories.organization

import model.organization.Organization

trait OrganizationRepository[F[_]] {
  def getAllInRange(range: Double, latitude: Double, longitude: Double): F[Seq[Organization]]
  def getAll: F[Seq[Organization]]
  def getByOwnerId(id: Long):F[Option[Organization]]
  def getByName(name: String): F[Option[Organization]]
}
