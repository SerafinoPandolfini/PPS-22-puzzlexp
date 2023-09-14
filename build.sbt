ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.3"

val circeVersion = "0.14.3"

lazy val root = (project in file("."))
  .settings(
    name := "PPS-22-puzzlexp",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.16" % "test",
    libraryDependencies += "it.unibo.alice.tuprolog" % "2p-core" % "4.1.1",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion)
  )

jacocoReportSettings := JacocoReportSettings()
  .withThresholds(
    JacocoThresholds(instruction = 0, method = 0, branch = 0, complexity = 0, line = 70, clazz = 70)
  )
Test / jacocoExcludes := Seq("controller.*", "view.*", "model.game.*")
Test / parallelExecution := false
