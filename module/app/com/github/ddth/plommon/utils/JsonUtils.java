package com.github.ddth.plommon.utils;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;

/**
 * Json-related utilities
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JsonUtils {
	/**
	 * Serializes an object to Json string.
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		return Json.stringify(Json.toJson(obj));
	}

	/**
	 * Deserializes a Json string.
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
		JsonNode jsonNode = Json.parse(jsonString);
		return jsonNode != null ? Json.fromJson(jsonNode, clazz) : null;
	}
}
