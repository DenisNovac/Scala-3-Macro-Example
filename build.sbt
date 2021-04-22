
ThisBuild / scalaVersion := "3.0.0-RC3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-macro-example",
    version := "0.1.0",
    scalacOptions ++= Seq( "-unchecked", "-Ycheck:macros" )
  )
