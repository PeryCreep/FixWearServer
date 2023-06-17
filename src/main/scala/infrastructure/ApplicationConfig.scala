package infrastructure

case class ApplicationConfig(host: String, port: Int)

object ApplicationConfig {
  lazy val default = ApplicationConfig("localhost", 8080)
}