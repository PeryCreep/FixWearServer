package utils.WebTokensUtils

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import utils.WebTokensUtils.TokensUtils.verifyToken

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

object AuthMiddleware {
  def authMiddleware: Directive1[String] = { //todo че делать с globalContext-ом вопрос хороший)
      optionalHeaderValueByName("Authorization").flatMap {
        case Some(token) =>
          onComplete(
            Future(
              verifyToken(token)
            )
          ).flatMap {
            case Success(Some(userId)) => provide(userId)
            case _ => complete(StatusCodes.Unauthorized)
          }
        case None =>
          complete(StatusCodes.Unauthorized)
    }
  }
}
