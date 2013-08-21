Plommon - com.github.ddth.plommon.utils
=======================================

`JsonUtils`
-----------
Many times you just want to serialize your Java object to JSON-string and vice-versa, without touching any `JsonNode` object. This utility class provides methods to do just that.

```java
import com.github.ddth.plommon.utils.JsonUtils;
. . .

List\<String\> objList = new ArrayList\<String\>();
. . .

String jsonString = JsonUtils.toJsonString(objList);
. . .

List\<?\> anotherObj = JsonUtils.fromJsonString(jsonString, List.class);
```
