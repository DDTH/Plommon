Plommon - com.github.ddth.plommon.utils
=======================================

DPathUtils
------------

Utility to access data from a hierarchy structure.

DPath notation:

* `.` (dot character): path separator.
* `name`: access a map's attribute specified by `name`.
* `[i]`: access i'th element of a list/map (0-based).

(example: `employees.[1].first_name`).

Sample usage: assuming you have the following data structure:

```java
Map<String, Object> company = new HashMap<String, Object>();
company.put("name", "Monster Corp.");
company.put("year", 2003);
 
List<Map<String, Object>> employees = new ArrayList<Map<String, Object>>();
company.put("employees", employees);
 
Map<String, Object> employee1 = new HashMap<String, Object>();
employee1.put("first_name", "Mike");
employee1.put("last_name", "Wazowski");
employee1.put("email", "mike.wazowski@monster.com");
employee1.put("age", 29);
employees.add(employee1);
 
Map<String, Object> employee2 = new HashMap<String, Object>();
employee2.put("first_name", "Sulley");
employee2.put("last_name", "Sullivan");
employee2.put("email", "sulley.sullivan@monster.com");
employee2.put("age", 30);
employees.add(employee2);
```

You can access company's attributes:

```java
String companyName = DPathUtils.getValue(company, "name", String.class);
//got string "Monster Corp."
 
Integer companyYear = DPathUtils.getValue(company, "year", Integer.class);
//got integer 2003 
```

You can access the two employees:

```java
Object user1 = DPathUtils.getValue(company, "employees.[0]");
//got map {first_name=Mike, email=mike.wazowski@monster.com, age=29, last_name=Wazowski}

Map<String, Object> user2 = DPathUtils.getValue(company, "employees.[1]", Map.class);
//got map {first_name=Sulley, email=sulley.sullivan@monster.com, age=30, last_name=Sullivan}
```

Or, employee's attributes:

```java
String firstName1 = DPathUtils.getValue(company, "employees.[0].first_name", String.class);

Object email2 = DPathUtils.getValue(company, "employees.[1].email");
//got string "sulley.sullivan@monster.com"

Long age2 = DPathUtils.getValue(company, "employees.[1].age", Long.class);
//got a Long value of 30
```


JsonUtils
-----------

Many times you just want to serialize your Java object to JSON-string and vice-versa, without touching any `JsonNode` object. This utility class provides methods to do just that.

Sample usage:

```java
import com.github.ddth.plommon.utils.JsonUtils;
. . .

List<String> objList = new ArrayList<String>();
. . .

String jsonString = JsonUtils.toJsonString(objList);
. . .

List<?> anotherObj = JsonUtils.fromJsonString(jsonString, List.class);
. . .
```


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
```
