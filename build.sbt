import Dependencies.Versions._

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.github.aaabramov"

lazy val root = (project in file("."))
  .settings(
    name := "scala-slick-template",
    libraryDependencies ++= Seq(
      "com.typesafe"                % "config"              % typesafeConfig,
      "com.typesafe.slick"         %% "slick"               % slick,
      "com.typesafe.slick"         %% "slick-hikaricp"      % slick,
      "com.github.tminglei"        %% "slick-pg"            % slickPg,
      "com.github.tminglei"        %% "slick-pg_core"       % slickPg,
      "com.github.tminglei"        %% "slick-pg_circe-json" % slickPg,
      "org.postgresql"              % "postgresql"          % psql,
      "org.flywaydb"                % "flyway-core"         % flyway,
      "io.circe"                   %% "circe-core"          % circe,
      "io.circe"                   %% "circe-generic"       % circe,
      "io.circe"                   %% "circe-jawn"          % circe,
      "io.circe"                   %% "circe-numbers"       % circe,
      "io.circe"                   %% "circe-parser"        % circe,
      "com.beachape"               %% "enumeratum"          % enumeratum,
      "com.beachape"               %% "enumeratum-circe"    % enumeratum,
      "ch.qos.logback"              % "logback-core"        % logback,
      "ch.qos.logback"              % "logback-classic"     % logback,
      "com.typesafe.scala-logging" %% "scala-logging"       % scalaLogging,
      "org.scalatest"              %% "scalatest"           % scalaTest % Test
    )
  )
