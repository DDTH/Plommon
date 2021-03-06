// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
// addSbtPlugin("play" % "sbt-plugin" % "2.1.3")
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")

// for PGP signing
addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8")
