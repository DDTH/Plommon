play-module-plommon
===================

Common utilities as a module for Play! Framework 2.x.

By Thanh Ba Nguyen (btnguyen2k (at) gmail.com)

Project home:
[https://github.com/DDTH/Plommon](https://github.com/DDTH/Plommon)

Release-notes
-------------

See [RELEASE-NOTES.md](RELEASE-NOTES.md).

Installation
------------

Play framework 2.x:

* add `"com.github.ddth" % "play-module-plommon" % "0.1.0-SNAPSHOT"` to your dependencies (`project/Build.scala`):

```scala
val appDependencies = Seq(
  // Add your project dependencies here,
  "com.github.ddth" % "play-module-plommon" % "0.1.0-SNAPSHOT",
  javaCore,
  javaJdbc
)
```

* add `Sonatype snapshots repository` to resolver list.

```scala
val main = play.Project(appName, appVersion, appDependencies).settings(
  // Add your own project settings here
  resolvers += "Sonatype snapshots repository" at "http://oss.sonatype.org/content/repositories/snapshots/"
)
```

Utilities
---------

License
-------

Distributed under the [MIT License](http://opensource.org/licenses/mit-license.php). Copyright (c) 2013 Thanh Ba Nguyen (aka btnguyen2k).

Third party libraries are distributed under their own license(s).
