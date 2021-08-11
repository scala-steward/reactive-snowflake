val akkaVersion = "2.6.15"
val baseName    = "reactive-snowflake"

val baseSettings = Seq(
  organization := "com.github.yoshiyoshifujii",
  scalaVersion := "2.13.6"
)

lazy val reactiveSnowflakeCore = project
  .in(file(s"$baseName-core"))
  .settings(baseSettings)
  .settings(
    name := s"$baseName-core",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j"       % akkaVersion,
      "ch.qos.logback"     % "logback-classic"  % "1.2.5" excludeAll (
        ExclusionRule(organization = "org.slf4j")
      ),
      "org.scalatest"     %% "scalatest"                % "3.2.9"     % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test
    )
  )

lazy val reactiveSnowflakeCluster = project
  .in(file(s"$baseName-cluster"))
  .settings(baseSettings)
  .settings(
    name := s"$baseName-cluster",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-cluster-typed"          % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-serialization-jackson"  % akkaVersion,
      "com.typesafe.akka" %% "akka-multi-node-testkit"     % akkaVersion % Test
    )
  ).dependsOn(reactiveSnowflakeCore)

lazy val root = project
  .in(file("."))
  .settings(baseSettings)
  .settings(
    name := s"$baseName"
  )
  .aggregate(reactiveSnowflakeCore)
