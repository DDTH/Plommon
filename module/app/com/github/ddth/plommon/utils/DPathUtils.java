package com.github.ddth.plommon.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility to access data from a hierarchy structure.
 * 
 * <p>
 * Assuming you have a complex data structure like the following:
 * </p>
 * 
 * <pre>
 * Map&lt;String, Object&gt; company = new HashMap&lt;String, Object&gt;();
 * company.put(&quot;name&quot;, &quot;Monster Corp.&quot;);
 * company.put(&quot;year&quot;, 2003);
 * 
 * List&lt;Map&lt;String, Object&gt;&gt; employees = new ArrayList&lt;Map&lt;String, Object&gt;&gt;();
 * company.put(&quot;employees&quot;, employees);
 * 
 * Map&lt;String, Object&gt; employee1 = new HashMap&lt;String, Object&gt;();
 * employee1.put(&quot;first_name&quot;, &quot;Mike&quot;);
 * employee1.put(&quot;last_name&quot;, &quot;Wazowski&quot;);
 * employee1.put(&quot;email&quot;, &quot;mike.wazowski@monster.com&quot;);
 * employee1.put(&quot;age&quot;, 29);
 * employees.add(employee1);
 * 
 * Map&lt;String, Object&gt; employee2 = new HashMap&lt;String, Object&gt;();
 * employee2.put(&quot;first_name&quot;, &quot;Sulley&quot;);
 * employee2.put(&quot;last_name&quot;, &quot;Sullivan&quot;);
 * employee2.put(&quot;email&quot;, &quot;sulley.sullivan@monster.com&quot;);
 * employee2.put(&quot;age&quot;, 30);
 * employees.add(employee2);
 * </pre>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class DPathUtils {

	private final static Pattern PATTERN_INDEX = Pattern
			.compile("^\\[(\\d+)\\]$");

	/**
	 * Extracts a value from the target object using DPath expression (generic
	 * version).
	 * 
	 * @param target
	 * @param dPath
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(final Object target, final String dPath,
			final Class<T> clazz) {
		Object temp = getValue(target, dPath);
		if (temp == null) {
			return null;
		}
		if (clazz.isAssignableFrom(temp.getClass())) {
			return (T) temp;
		}
		if (clazz == String.class) {
			return (T) temp.toString();
		}
		return null;
	}

	/**
	 * Extracts a value from the target object using DPath expression.
	 * 
	 * @param target
	 * @param dPath
	 */
	public static Object getValue(final Object target, final String dPath) {
		String[] paths = dPath.split("\\.");
		Object result = target;
		for (String path : paths) {
			result = extractValue(result, path);
		}
		return result;
	}

	/**
	 * Sets a value to the target object using DPath expression.
	 * 
	 * @param target
	 * @param dPath
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public static void setValue(final Object target, final String dPath,
			final Object value) {
		String[] paths = dPath.split("\\.");
		Object cursor = target;
		// "seek"to the correct position
		for (int i = 0; i < paths.length - 1; i++) {
			cursor = extractValue(cursor, paths[i]);
		}
		String index = paths[paths.length - 1];
		Matcher m = PATTERN_INDEX.matcher(index);
		if (m.matches() || "[]".equals(index)) {
			int i = "[]".equals(index) ? Integer.MAX_VALUE : Integer.parseInt(m
					.group(1));
			if (cursor instanceof List<?>) {
				List<Object> temp = (List<Object>) cursor;
				if (i < 0) {
					throw new IllegalArgumentException("Invalid index [" + i
							+ "]!");
				}
				if (i >= temp.size()) {
					temp.add(value);
				} else {
					temp.remove(i);
					temp.add(i, value);
				}
			} else {
				throw new IllegalArgumentException(
						"Target object is not a list or readonly!");
			}
		} else if (cursor instanceof Map<?, ?>) {
			((Map<Object, Object>) cursor).put(index, value);
		} else {
			throw new IllegalArgumentException("Target object is not writable!");
		}
	}

	private static Object extractValue(Object target, String index) {
		if (target == null) {
			return null;
		}
		Matcher m = PATTERN_INDEX.matcher(index);
		if (m.matches()) {
			int i = Integer.parseInt(m.group(1));
			if (target instanceof Object[]) {
				return ((Object[]) target)[i];
			}
			if (target instanceof List<?>) {
				return ((List<?>) target).get(i);
			}
			throw new IllegalArgumentException("Expect an array or list!");
		}
		if (target instanceof Map<?, ?>) {
			return ((Map<?, ?>) target).get(index);
		}
		throw new IllegalArgumentException();
	}

	public static void main(String[] args) {
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

		System.out.println(DPathUtils.getValue(company, "name"));
		System.out.println(DPathUtils.getValue(company, "year"));
	}
}
