package services.organization

import model.organization.Organization
import repositories.organization.OrganizationRepository

import scala.concurrent.Future

class OrgService(orgRepository: OrganizationRepository[Future]) {
  def getAll(): Future[Seq[Organization]] = {
    orgRepository.getAll
  }

  def getByOwnerId(id: Long) = {
    orgRepository.getByOwnerId(id)
  }
}
