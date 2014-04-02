Plommon - com.github.ddth.plommon.utils
=======================================

AkkUtils
--------

Akka helper class to schedule jobs.


PlayAppUtils
------------

Play Application Uitlities.

* Request URL & query string helper methods.
* `application.conf` helper methods.


SessionUtils
--------------

Session utilities with enhanced functionality:

* Complex data structure for session items (Session items are automatically serialized/deserialized using JSON format). 
* TTL (time-to-live): session items can have expiry.

Sample usage:

```java
import com.github.ddth.plommon.utils.SessionUtils;
. . .

/*
 * <code>true</code> (default): session item's expiry is automatically refreshed everytime it is accessed.
 */
SessionUtils.autoTouch = true;
. . .


Map<String, Object> user = new HashMap<String, Object>();
user.put("first_name", "Thanh");
user.put("last_name", "Nguyen"); 
. . .

//time-to-live = 300 seconds
SessionUtils.setSession("user", user, 300);
. . .

Object obj = SessionUtils.getSession("user");
. . .

//remove a session entry
SessionUtils.removeSession("user");
...
```
