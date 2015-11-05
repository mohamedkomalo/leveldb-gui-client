import com.typesafe.sbt.SbtProguard.ProguardKeys._

lazy val root = (project in file(".")).
  settings(
    name := "leveldb-gui-client",
    organization := "com.github.mohamedkomalo",
    version := "0.2",
    scalaVersion := "2.11.7",
    crossPaths := false,
    javacOptions in (Compile, compile) := Seq("-target", "1.7", "-source", "1.7"),
    fork := true
  )

libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.7"
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

proguardSettings

proguardVersion in Proguard := "5.2.1"

// include only java runtime jar, exclude all project libraries
libraries in Proguard := (libraries in Proguard).value.filter(_.getName.contains("rt.jar"))

// use the assembled version from sbt-assembly instead which has all the classes of the dependencies
inputs in Proguard := Seq(assembly.value)

outputs in Proguard  := Seq((target in Proguard).value / ((artifactPath in packageBin in Compile).value.base + "-standalone.jar"))

// remove the default filter because it will exclude "META-INF/MANIFEST" which is required
defaultInputFilter in Proguard := None

options in Proguard ++= Seq("-dontoptimize", "-dontobfuscate", "-dontnote", "-dontwarn", "-ignorewarnings")

options in Proguard += ProguardOptions.keepMain("com.github.mohamedkomalo.leveldbgui.MainWindow")

lazy val standaloneJar = taskKey[Unit]("st")
standaloneJar := {
  (proguard in Proguard).value
}