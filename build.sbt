lazy val root = (project in file(".")).
  settings(
    name := "leveldb-gui-client",
    organization := "com.github.mohamedkomalo",
    version := "0.1",
    scalaVersion := "2.11.7"
  )

libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.7"
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

fork := true