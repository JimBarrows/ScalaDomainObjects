name := "Scala Domain Objects"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies += "org.specs2" %% "specs2" % "1.12" % "test"
  

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")
