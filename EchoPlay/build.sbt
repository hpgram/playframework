import play.Project._

name := """EchoPlay"""

version := "1.0-SNAPSHOT"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

libraryDependencies ++= Seq(
	"org.webjars" %% "webjars-play" % "2.2.0", 
	"org.webjars" % "bootstrap" % "2.3.1")

playJavaSettings
