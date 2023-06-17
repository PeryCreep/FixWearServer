ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "FixWearServer",
    libraryDependencies ++=
      akkaDependencies ++
      slickDependencies ++
      passwordHashDependencies ++
      catsDependencies ++
      webTokensDependencies ++
      configDependencies
  )

lazy val akkaVersion = "2.8.0"
lazy val catsVersion = "3.4.9"
lazy val catsCoreVersion = "2.9.0"
lazy val akkaHttpVersion = "10.5.1"
lazy val slickVersion = "3.4.1"
lazy val slf4jVersion = "2.0.5"



lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.1"
)

lazy val catsDependencies = Seq(
  "org.typelevel" %% "cats-core" % catsCoreVersion,
  "org.typelevel" %% "cats-effect" % catsVersion
)

lazy val testDependencies = Seq(

)

lazy val slickDependencies = Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % "42.5.4",
  "org.slf4j" % "slf4j-nop" % slf4jVersion,
  "com.typesafe" % "config" % "1.4.2"
)

lazy val passwordHashDependencies = Seq(
  "com.outr" %% "scalapass" % "1.2.5"
)

lazy val webTokensDependencies = Seq(
  "com.auth0" % "java-jwt" % "4.3.0"
)

lazy val configDependencies = Seq(
  "com.github.pureconfig" %% "pureconfig" % "0.17.4"
)

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}