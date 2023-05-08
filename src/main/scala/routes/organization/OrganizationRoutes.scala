package routes.organization

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import perycreep.Main.system.dispatcher
import repositories.organization.PostgresOrganizationRepository
import services.organization.OrgService
import spray.json.JsArray
import utils.DBUtils.DBUtils.db

object OrganizationRoutes {
  val orgService = new OrgService(PostgresOrganizationRepository(db = db))
  val routes: Route = {
    pathPrefix("organizations") {
      path("all") {
        get {
          onSuccess(orgService.getAll()){ organizations =>
            complete(JsArray(organizations.map(_.toJson).toVector))
          }
        }
      }
    }
  }
}
