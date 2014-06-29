package com.github.ddth.plommon.bo;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import play.cache.Cache;

/**
 * Base class for application DAOs.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.5.0
 */
public abstract class BaseDao {

    protected final static Charset CHARSET = Charset.forName("UTF-8");

    public final static String DEFAULT_DATASOURCE_NAME = "default";

    /**
     * @since 0.5.1.2
     */
    private static ThreadLocal<List<ProfilingRecord>> profilingRecords = new ThreadLocal<List<ProfilingRecord>>() {
        @Override
        protected List<ProfilingRecord> initialValue() {
            return new LinkedList<ProfilingRecord>();
        }
    };

    /**
     * Initializes profiling data.
     * 
     * @since 0.5.1.2
     */
    public static void startProfiling() {
        profilingRecords.remove();
    }

    /**
     * Clears profiling data.
     * 
     * @since 0.5.1.2
     */
    public static void clearProfiling() {
        profilingRecords.remove();
    }

    /**
     * Gets current profiling data.
     * 
     * @return
     * @since 0.5.1.2
     */
    public static ProfilingRecord[] getProfiling() {
        return profilingRecords.get().toArray(ProfilingRecord.EMPTY_ARRAY);
    }

    /**
     * Adds a new profiling record.
     * 
     * @param execTimeMs
     * @param command
     * @return
     * @since 0.5.1.2
     */
    public static ProfilingRecord addProfiling(long execTimeMs, String command) {
        ProfilingRecord record = new ProfilingRecord(execTimeMs, command);
        profilingRecords.get().add(record);
        return record;
    }

    /**
     * Initializing method.
     */
    public void init() {
        // EMPTY
    }

    /**
     * Destroy method.
     */
    public void destroy() {
        // EMPTY
    }

    /*--------------------------------------------------------------------------------*/
    /**
     * Removes an entry from cache.
     * 
     * @param key
     */
    protected static void removeFromCache(String key) {
        Cache.remove(key);
    }

    /**
     * Puts an entry to cache.
     * 
     * @param key
     * @param value
     */
    protected static void putToCache(String key, Object value) {
        putToCache(key, value, 0);
    }

    /**
     * Puts an entry to cache, with specific TTL.
     * 
     * @param key
     * @param value
     * @param ttl
     *            TTL in seconds
     */
    protected static void putToCache(String key, Object value, int ttl) {
        if (value != null) {
            if (ttl > 0) {
                Cache.set(key, value, ttl);
            } else {
                Cache.set(key, value);
            }
        }
    }

    /**
     * Gets an entry from cache.
     * 
     * @param key
     * @return
     */
    protected static Object getFromCache(String key) {
        return Cache.get(key);
    }

    /**
     * Gets an entry from cache.
     * 
     * Note: if the object from cache is not assignable to clazz,
     * <code>null</code> is returned.
     * 
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    protected static <T> T getFromCache(String key, Class<T> clazz) {
        Object obj = getFromCache(key);
        if (obj == null) {
            return null;
        }
        if (clazz.isAssignableFrom(obj.getClass())) {
            return (T) obj;
        }
        return null;
    }
}
