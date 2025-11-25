// Plugin oficial de Gatling SBT
addSbtPlugin("io.gatling" % "gatling-sbt" % "4.3.2")

// Repos necesarios para resolver dependencias
resolvers += "Gatling OSS" at "https://repository.gatling.io/maven"
resolvers += "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/releases/"
resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"
