// Plugin oficial de Gatling SBT
addSbtPlugin("io.gatling" % "gatling-sbt" % "3.9.5")

// Repos necesarios para resolver dependencias
resolvers += "Gatling OSS" at "https://repository.gatling.io/maven"
resolvers += "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/releases/"
resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"
