package repositories.organization

import model.organization.Organization

trait OrganizationRepository[F[_]] {
  def getAllInRange(range: Double): F[Seq[Organization]]
  def getAll: F[Seq[Organization]]
  def getByOwnerId(id: Long):F[Organization]
  def getByName(name: String): F[Organization]
}
