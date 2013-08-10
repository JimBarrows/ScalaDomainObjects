name := "Scala Domain Objects"

version := "1.0.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.10.2"
  
libraryDependencies in ThisBuild ++= Seq("org.specs2" %% "specs2" % "2.1.1" % "test",
		"cc.co.scala-reactive" %% "reactive-core" % "0.3.2.1", 
		"org.scalastuff" % "scalabeans" % "0.3",
		"com.github.nscala-time" %% "nscala-time" % "0.5.0-SNAPSHOT",
		"com.h2database" % "h2" % "1.3.166" ,
		"org.slf4j" % "slf4j-nop" % "1.6.4",
		"com.typesafe.slick" %% "slick" % "1.0.1"
		)

resolvers in ThisBuild ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")
