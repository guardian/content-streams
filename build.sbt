
organization := "com.gu"

name := "content-streams"

version := "0.1"

scalaVersion := "2.10.2"

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Guardian Github Releases" at "http://guardian.github.com/maven/repo-releases"
)

libraryDependencies ++= Seq(
  "org.scalaz.stream" %% "scalaz-stream" % "0.2-SNAPSHOT",
  "com.gu.openplatform" %% "content-api-client" % "2.0",
  "com.gu" %% "flexible-content-body-parser" % "0.1",
  "com.github.nscala-time" %% "nscala-time" % "0.6.0",
  "org.specs2" %% "specs2" % "2.3.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.10.1" % "test"
)

publishTo <<= (version) { version: String =>
  val publishType = if (version.endsWith("SNAPSHOT")) "snapshots" else "releases"
  Some(
    Resolver.file(
      "guardian github " + publishType,
      file(System.getProperty("user.home") + "/guardian.github.com/maven/repo-" + publishType)
    )
  )
}
