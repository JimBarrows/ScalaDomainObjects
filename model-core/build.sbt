name := "Model Core"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.12" % "test",
		"cc.co.scala-reactive" %% "reactive-core" % "0.2-SNAPSHOT", 
		"org.scalastuff" % "scalabeans" % "0.3")
  

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")
