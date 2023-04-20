ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "FixWearServer"
  )

lazy val akkaVersion = "2.8.0"
lazy val catsVersion = "3.4.9"
lazy val akkaHttpVersion = "10.5.1"
lazy val slickVersion = "3.4.1"
lazy val slf4jVersion = "2.0.5"

libraryDependencies ++= akkaDependencies ++ catsDependencies ++ slickDependencies


lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
)

lazy val catsDependencies = Seq(
  "org.typelevel" %% "cats-effect" % catsVersion
)

lazy val testDependencies = Seq(

)

lazy val slickDependencies = Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "org.slf4j" % "slf4j-nop" % slf4jVersion
)