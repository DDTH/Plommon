play-module-plommon
===================

Common utilities as a module for Play! Framework 2.x.

By Thanh Ba Nguyen (btnguyen2k (at) gmail.com)

Project home:
[https://github.com/DDTH/Plommon](https://github.com/DDTH/Plommon)


Release-notes
-------------

Latest stable release: v0.5.1.3. See [RELEASE-NOTES.md](RELEASE-NOTES.md).


Installation
------------

* add `"com.github.ddth" %% "play-module-plommon" % "<version>"` to your dependencies (e.g. `project/Build.scala`):

Example:
```scala
val appDependencies = Seq(
  // Add your project dependencies here,
  "com.github.ddth" %% "play-module-plommon" % "0.5.1.3",
  javaCore,
  javaJdbc
)
```


Activate Plommon In Play Applications
-------------------------------------
Add the following line in `play.plugins` file:

`1000:com.github.ddth.plommon.Activator`


BO
--
BO/DAO library.

* See: [com.github.ddth.plommon.bo](module/app/com/github/ddth/plommon/bo/README.md)


Utilities
---------
Some utility classes.

* See: [com.github.ddth.plommon.utils](module/app/com/github/ddth/plommon/utils/README.md)


License
-------

Distributed under the [MIT License](http://opensource.org/licenses/mit-license.php). Copyright (c) 2013-2014 Thanh Ba Nguyen (aka btnguyen2k).

Third party libraries are distributed under their own license(s).
