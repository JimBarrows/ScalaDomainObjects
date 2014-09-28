name := "Scala Domain Objects"

version := "1.0.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.2"
  
libraryDependencies in ThisBuild ++= Seq(
		    "org.specs2" %% "specs2" % "2.4.4" % "test",
		    "com.github.nscala-time" %% "nscala-time" % "1.4.0",
		    "com.h2database" % "h2" % "1.4.181" ,
		    "org.slf4j" % "slf4j-nop" % "1.7.7",
		    "com.typesafe.slick" %% "slick" % "2.1.0",
		    "commons-codec" % "commons-codec" % "1.9",
		    "org.scalaz" %% "scalaz-core" % "7.1.0"
		)

resolvers in ThisBuild ++= Seq(
	  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
	  "releases"  at "http://oss.sonatype.org/content/repositories/releases")

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"