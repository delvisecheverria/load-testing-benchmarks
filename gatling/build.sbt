ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
    name := "GatlingBenchmarkProject",
    version := "0.1",
    libraryDependencies ++= Seq(
      "io.gatling" % "gatling-test-framework" % "3.9.5",
      "io.gatling" % "gatling-app" % "3.9.5"
    )
  )

