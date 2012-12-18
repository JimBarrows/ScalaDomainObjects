name := "Domain Core"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.12" % "test",
		"cc.co.scala-reactive" %% "reactive-core" % "0.2-SNAPSHOT", 
		"org.scalastuff" % "scalabeans" % "0.3",
		"org.scalaj" %% "scalaj-time" % "0.6",
		"com.h2database" % "h2" % "1.2.140" ,
		"org.xerial" % "sqlite-jdbc" % "3.6.20" ,
		"com.typesafe" % "slick_2.10.0-RC1" % "0.11.2"
		)
  

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")
