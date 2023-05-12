package utils.WebTokensUtils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

import java.util.Date
import scala.util.{Failure, Success, Try}

object TokensUtils {

  type Token = String

  private val secret = "fix-wear-session-token"
  private val algorithm = Algorithm.HMAC256(secret)
  def generateToken(userId: String): String = {
    val now = new Date()
    val expireAt = new Date(now.getTime + 1000 * 60 * 60 * 24 * 7)
    val builder = JWT.create()
      .withIssuer("fix-wear")
      .withIssuedAt(now)
      .withExpiresAt(expireAt)
      .withClaim("userId", userId)
    builder.sign(algorithm)
  }

  def verifyToken(token: String) : Option[String] = {
    Try {
      val verifier = JWT.require(algorithm)
        .withIssuer("fix-wear")
        .build()
      val decoded = verifier.verify(token)
      val userId = decoded.getClaim("userId").toString
      userId
    } match {
      case Failure(_) => None
      case Success(value) => Some(value)
    }
  }
}
