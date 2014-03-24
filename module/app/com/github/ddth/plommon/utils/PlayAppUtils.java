package com.github.ddth.plommon.utils;

import java.util.Map;

import play.Configuration;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http;

/**
 * Play Application utilities.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.4.6
 */
public class PlayAppUtils {
    /* Request URL Helper */
    /**
     * Gets a query string from the current request url.
     * 
     * @param name
     * @return
     */
    public static String queryString(String name) {
        return Controller.request().getQueryString(name);
    }

    /**
     * Extract the domain/host name from the current request url.
     * 
     * @return
     */
    public static String siteDomain() {
        return Http.Context.current().request().host();
    }

    /* Application.conf Helper */

    /**
     * Gets an application configuration tree.
     * 
     * @param key
     * @return
     */
    public static Configuration appConfig(String key) {
        return Play.application().configuration().getConfig(key);
    }

    /**
     * Gets all application configurations as a map.
     * 
     * @return
     */
    public static Map<String, Object> appConfigMap() {
        return appConfigMap(Play.application().configuration());
    }

    /**
     * Gets all application configurations as a map.
     * 
     * @param config
     * @return
     */
    public static Map<String, Object> appConfigMap(Configuration config) {
        return config != null ? config.asMap() : null;
    }

    /**
     * Gets a configuration boolean from the {@code application.conf} file.
     * 
     * @param key
     * @return
     */
    public static Boolean appConfigBoolean(String key) {
        return appConfigBoolean(Play.application().configuration(), key);
    }

    /**
     * Gets a configuration boolean from the {@code application.conf} file.
     * 
     * @param config
     * @param key
     * @return
     */
    public static Boolean appConfigBoolean(Configuration config, String key) {
        return config != null ? config.getBoolean(key) : null;
    }

    /**
     * Gets a configuration double from the {@code application.conf} file.
     * 
     * @param key
     * @return
     */
    public static Double appConfigDouble(String key) {
        return appConfigDouble(Play.application().configuration(), key);
    }

    /**
     * Gets a configuration double from the {@code application.conf} file.
     * 
     * @param config
     * @param key
     * @return
     */
    public static Double appConfigDouble(Configuration config, String key) {
        return config != null ? config.getDouble(key) : null;
    }

    /**
     * Gets a configuration long from the {@code application.conf} file.
     * 
     * @param key
     * @return
     */
    public static Long appConfigLong(String key) {
        return appConfigLong(Play.application().configuration(), key);
    }

    /**
     * Gets a configuration long from the {@code application.conf} file.
     * 
     * @param config
     * @param key
     * @return
     */
    public static Long appConfigLong(Configuration config, String key) {
        return config != null ? config.getLong(key) : null;
    }

    /**
     * Gets a configuration integer from the {@code application.conf} file.
     * 
     * @param key
     * @return
     */
    public static Integer appConfigInteger(String key) {
        return appConfigInteger(Play.application().configuration(), key);
    }

    /**
     * Gets a configuration integer from the {@code application.conf} file.
     * 
     * @param config
     * @param key
     * @return
     */
    public static Integer appConfigInteger(Configuration config, String key) {
        return config != null ? config.getInt(key) : null;
    }

    /**
     * Gets a configuration string from the {@code application.conf} file.
     * 
     * @param key
     * @return
     */
    public static String appConfigString(String key) {
        return appConfigString(Play.application().configuration(), key);
    }

    /**
     * Gets a configuration string from the {@code application.conf} file.
     * 
     * @param config
     * @param key
     * @return
     */
    public static String appConfigString(Configuration config, String key) {
        return config != null ? config.getString(key) : null;
    }
}
