name := "fibonacci-service"

organization in ThisBuild := "com.zarina"
scalaVersion in ThisBuild := "2.12.1"
version in ThisBuild := "0.0.1"


lazy val root = (project in file("."))
  .aggregate(
    common,
    proxyService,
    fibonacciServer
  )
  .settings(
    commonSettings,
    libraryDependencies ++= commonDependencies
  )
  .disablePlugins(ScroogeSBT)
  .disablePlugins(AssemblyPlugin)

lazy val common = (project in file("common"))
  .settings(
    commonSettings,
    libraryDependencies ++= commonDependencies,
    libraryDependencies ++= Seq(
      "org.apache.thrift" % "libthrift" % "0.10.0",
      "com.twitter" %% "scrooge-core" % "19.10.0" exclude("com.twitter", "libthrift"),
      "com.twitter" %% "finagle-thrift" % "19.10.0" exclude("com.twitter", "libthrift")
    )
//    scroogeThriftOutputFolder in Compile := baseDirectory.value / "src_gen"
  )
  .enablePlugins(ScroogeSBT)
  .disablePlugins(AssemblyPlugin)

lazy val fibonacciServer = (project in file("fibonacci-server"))
  .settings(
    commonSettings
  )
  .dependsOn(common % "compile->compile")
  .disablePlugins(ScroogeSBT)

lazy val proxyService = (project in file("proxy-service"))
  .settings(
    commonSettings,
    libraryDependencies ++= commonDependencies,
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-http" % "19.10.0"
    )
  )
  .dependsOn(common)
  .disablePlugins(ScroogeSBT)


lazy val commonSettings = Seq(
)


lazy val commonDependencies = Seq(
  "com.twitter" %% "finagle-core" % "19.10.0",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.slf4j" % "slf4j-log4j12" % "1.7.25"

)