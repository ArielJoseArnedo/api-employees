name := """api-employee"""
organization := "co.com.employee"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "co.com.employee",
    buildInfoOptions += BuildInfoOption.ToJson
  )

scalaVersion := "2.13.5"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  guice,
  "com.gitlab.ArielJose55"    % "api-concurrency"         % "develop-SNAPSHOT",
  "org.projectlombok"         % "lombok"                  % "1.18.12"                         % Provided,
  "io.github.openfeign"       % "feign-core"              % "11.0",
  "io.github.openfeign.form"  % "feign-form"              % "3.8.0",
  "io.github.openfeign"       % "feign-jackson"           % "11.0",
  "io.github.openfeign"       % "feign-slf4j"             % "11.0",
  "net.aichler"               % "jupiter-interface"       % JupiterKeys.jupiterVersion.value  % Test,
  "org.mockito"               % "mockito-junit-jupiter"   % "2.28.2"                          % Test,
  "org.junit.platform"        % "junit-platform-runner"   % "1.6.2"                           % Test,
  "org.assertj"               % "assertj-core"            % "3.16.1"                          % Test
)

PlayKeys.devSettings := Seq("play.server.http.port" -> "9300")

testOptions += Tests.Argument(jupiterTestFramework, "-v")
