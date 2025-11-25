ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
    name := "GatlingBenchmarkProject",
    version := "0.1",

    libraryDependencies ++= Seq(
      "io.gatling" % "gatling-core" % "3.9.5" % Test,
      "io.gatling" % "gatling-http" % "3.9.5" % Test,
      "io.gatling" %% "gatling-test-framework" % "3.9.5" % Test,
      "io.gatling" % "gatling-app" % "3.9.5" % Test
    )
  )
