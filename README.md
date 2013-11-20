play-module-plommon
===================

Common utilities as a module for Play! Framework 2.x.

By Thanh Ba Nguyen (btnguyen2k (at) gmail.com)

Project home:
[https://github.com/DDTH/Plommon](https://github.com/DDTH/Plommon)

Release-notes
-------------

Latest stable release: v0.3.2.

Latest SNAPSHOT release: v0.3.3-SNAPSHOT.

See [RELEASE-NOTES.md](RELEASE-NOTES.md).

Installation
------------

##### SNAPSHOT Releases #####

* add `"com.github.ddth" %% "play-module-plommon" % "<version>-SNAPSHOT"` to your dependencies (`project/Build.scala`):

Example:
```scala
val appDependencies = Seq(
  // Add your project dependencies here,
  "com.github.ddth" %% "play-module-plommon" % "0.1.0-SNAPSHOT",
  javaCore,
  javaJdbc
)
```

* add `Sonatype snapshots repository` to resolver list.

Example:
```scala
val main = play.Project(appName, appVersion, appDependencies).settings(
  // Add your own project settings here
  resolvers += "Sonatype snapshots repository" at "http://oss.sonatype.org/content/repositories/snapshots/"
)
```

##### Stable Releases #####

* add `"com.github.ddth" %% "play-module-plommon" % "<version>"` to your dependencies (`project/Build.scala`):

Example:
```scala
val appDependencies = Seq(
  // Add your project dependencies here,
  "com.github.ddth" %% "play-module-plommon" % "0.1.0",
  javaCore,
  javaJdbc
)
```


Utilities
=========

* [com.github.plommon.utils](module/app/com/github/ddth/plommon/utils/README.md)

License
-------

Distributed under the [MIT License](http://opensource.org/licenses/mit-license.php). Copyright (c) 2013 Thanh Ba Nguyen (aka btnguyen2k).

Third party libraries are distributed under their own license(s).
