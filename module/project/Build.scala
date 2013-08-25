import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play-module-plommon"
  val appVersion      = "0.1.0"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
	organization := "com.github.ddth",
    organizationName := "DDTH on GitHub",
    organizationHomepage := Some(new URL("https://github.com/DDTH")),
    
    // required for publishing artifact to maven central via sonatype
    publishArtifact in Test := false,
    publishMavenStyle := true,
    publishTo <<= version { v: String =>
	  val nexus = "https://oss.sonatype.org/"
	  if (v.trim.endsWith("SNAPSHOT"))
	    Some("snapshots" at nexus + "content/repositories/snapshots")
	  else
	    Some("releases" at nexus + "service/local/staging/deploy/maven2")
	},
	
	// in order to pass sonatype's requirements the following properties are required as well
	startYear := Some(2013),
	description := "Play framework 2.x module: common libaries and utilities",
    licenses := Seq("MIT License" -> url("http://opensource.org/licenses/mit-license.php")),
    homepage := Some(url("https://github.com/DDTH/Plommon")),
    scmInfo := Some(ScmInfo(url("https://github.com/DDTH/Plommon"), "https://github.com/DDTH/Plommon.git")),
    pomExtra := (
      <developers>
        <developer>
    	  <name>Thanh Nguyen</name>
          <email>btnguyen2k@gmail.com</email>
        </developer>
      </developers>
    )
  )

}
