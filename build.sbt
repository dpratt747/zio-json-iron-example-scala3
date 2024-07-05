ThisBuild / scalaVersion := "3.4.2"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val zioVersion = "2.1.5"
lazy val zioJsonVersion = "0.7.1"
lazy val ironVersion = "2.5.0"

lazy val root = (project in file("."))
  .settings(
    name := "zio-json-example-scala3",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-json" % zioJsonVersion,
      "io.github.iltotore" %% "iron" % ironVersion
    ) ++ testDependencies,
    scalacOptions ++= Seq("-Yretain-trees"),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

lazy val testDependencies = Seq(
  "dev.zio" %% "zio-test" % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
  "dev.zio" %% "zio-test-magnolia" % zioVersion % Test
)