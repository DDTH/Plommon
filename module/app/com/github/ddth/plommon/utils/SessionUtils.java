package com.github.ddth.plommon.utils;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Controller;

/**
 * Session utilities with enhanced functionality.
 * 
 * <ul>
 * <li>Complex data structure for session items. Session items are automatically
 * serialized/deserialized using JSON format.</li>
 * <li>TTL: session items can have expiry.</li>
 * </ul>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class SessionUtils {
	/**
	 * Gets a session item.
	 * 
	 * @param key
	 * @return the session item, or <code>null</code> if the item does not exist
	 *         or it has been expired
	 */
	@SuppressWarnings("unchecked")
	public static Object getSession(String key) {
		String sValue = Controller.session(key);
		if (sValue == null) {
			return null;
		}
		try {
			Map<String, Object> sEntry = JsonUtils.fromJsonString(sValue,
					Map.class);
			Long expiry = DPathUtils.getValue(sEntry, "expiry", Long.class);
			if (expiry == null
					|| expiry.longValue() > System.currentTimeMillis()) {
				return sEntry.get("value");
			} else {
				Controller.session().remove(key);
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Sets a session item.
	 * 
	 * @param key
	 * @param value
	 */
	public static void setSession(String key, Object value) {
		setSession(key, value, 0);
	}

	/**
	 * Sets a session item.
	 * 
	 * @param key
	 * @param value
	 * @param ttl
	 *            time-to-live in seconds
	 */
	public static void setSession(String key, Object value, long ttl) {
		Map<String, Object> sEntry = new HashMap<String, Object>();
		sEntry.put("value", value);
		if (ttl > 0) {
			sEntry.put("expiry", System.currentTimeMillis() + ttl * 1000);
		}
		Controller.session(key, JsonUtils.toJsonString(value));
	}
}
