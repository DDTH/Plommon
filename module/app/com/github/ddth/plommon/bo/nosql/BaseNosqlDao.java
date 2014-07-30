package com.github.ddth.plommon.bo.nosql;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.github.ddth.plommon.bo.BaseDao;
import com.github.ddth.plommon.bo.nosql.engine.cassandra.WideRowJsonCassandraNosqlEngine;
import com.github.ddth.plommon.utils.PlayAppUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Base class for NoSQL-based DAOs.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.5.0
 */
public abstract class BaseNosqlDao extends BaseDao {

    /*--------------------------------------------------------------------------------*/
    private static LoadingCache<String, INosqlEngine> cachedNosqlEngines = CacheBuilder
            .newBuilder().expireAfterAccess(3600, TimeUnit.SECONDS)
            .build(new CacheLoader<String, INosqlEngine>() {
                @Override
                public INosqlEngine load(String datasourceName) throws Exception {
                    return _createNosqlEngine(datasourceName);
                }
            });

    /**
     * Called by Play's module activator routine.
     */
    public static void activatePlugin() {
        cachedNosqlEngines.invalidateAll();
    }

    /**
     * Called by Play's module activator routine.
     */
    public static void inactivatePlugin() {
        cachedNosqlEngines.invalidateAll();
    }

    public final static String NOSQL_ENGINE_CASSANDRA_WIDEROW = "cassandra_widerow";
    public final static String CONF_KEY_NOSQL_ENGINE = "plommon.bo.nosql.{0}.engine";

    private static INosqlEngine _createNosqlEngine(String datasourceName) {
        String engineType = PlayAppUtils.appConfigString(MessageFormat.format(
                CONF_KEY_NOSQL_ENGINE, datasourceName));
        if (NOSQL_ENGINE_CASSANDRA_WIDEROW.equalsIgnoreCase(engineType)) {
            return new WideRowJsonCassandraNosqlEngine().setDatasourceName(datasourceName).init();
        }
        String msg = "Unsupport NoSQL engine type [{0}] (datasource: {1})";
        throw new IllegalArgumentException(MessageFormat.format(msg,
                engineType != null ? engineType : "null", datasourceName));
    }

    /**
     * Gets {@link INosqlEngine} instance mapped with a datasource name.
     * 
     * @param datasourceName
     * @return
     */
    protected static INosqlEngine nosqlEngine(String datasourceName) {
        try {
            return cachedNosqlEngines.get(datasourceName);
        } catch (ExecutionException e) {
            return null;
        }
    }

    /**
     * Gets {@link INosqlEngine} instance mapped with the "default" datasource
     * name.
     * 
     * @return
     */
    protected static INosqlEngine nosqlEngine() {
        return nosqlEngine("default");
    }

    /*--------------------------------------------------------------------------------*/
    /**
     * Deletes an entry from storage.
     * 
     * @param storageId
     * @param entryId
     */
    protected static void delete(String storageId, String entryId) {
        delete(BaseDao.DEFAULT_DATASOURCE_NAME, storageId, entryId);
    }

    /**
     * Deletes an entry from storage.
     * 
     * @param datasourceName
     * @param storageId
     * @param entryId
     */
    protected static void delete(String datasourceName, String storageId, String entryId) {
        INosqlEngine nosqlEngine = nosqlEngine(datasourceName);
        nosqlEngine.delete(storageId, entryId);
    }

    /**
     * Loads an entry from storage.
     * 
     * @param storageId
     * @param entryId
     * @return
     */
    protected static byte[] load(String storageId, String entryId) {
        return load(BaseDao.DEFAULT_DATASOURCE_NAME, storageId, entryId);
    }

    /**
     * Loads an entry from storage.
     * 
     * @param datasourceName
     * @param storageId
     * @param entryId
     * @return
     */
    protected static byte[] load(String datasourceName, String storageId, String entryId) {
        INosqlEngine nosqlEngine = nosqlEngine(datasourceName);
        return nosqlEngine.load(storageId, entryId);
    }

    /**
     * Loads an entry from storage as a JSON string.
     * 
     * @param storageId
     * @param entryId
     * @return
     */
    protected static String loadAsJson(String storageId, String entryId) {
        return loadAsJson(BaseDao.DEFAULT_DATASOURCE_NAME, storageId, entryId);
    }

    /**
     * Loads an entry from storage as a JSON string.
     * 
     * @param datasourceName
     * @param storageId
     * @param entryId
     * @return
     */
    protected static String loadAsJson(String datasourceName, String storageId, String entryId) {
        INosqlEngine nosqlEngine = nosqlEngine(datasourceName);
        return nosqlEngine.loadAsJson(storageId, entryId);
    }

    /**
     * Loads an entry from storage as a Map.
     * 
     * @param storageId
     * @param entryId
     * @return
     */
    protected static Map<Object, Object> loadAsMap(String storageId, String entryId) {
        return loadAsMap(BaseDao.DEFAULT_DATASOURCE_NAME, storageId, entryId);
    }

    /**
     * Loads an entry from storage as a Map.
     * 
     * @param datasourceName
     * @param storageId
     * @param entryId
     * @return
     */
    protected static Map<Object, Object> loadAsMap(String datasourceName, String storageId,
            String entryId) {
        INosqlEngine nosqlEngine = nosqlEngine(datasourceName);
        return nosqlEngine.loadAsMap(storageId, entryId);
    }

    /**
     * Stores an entry to storage.
     * 
     * @param storageId
     * @param entryId
     * @param data
     */
    protected static void store(String storageId, String entryId, byte[] data) {
        store(BaseDao.DEFAULT_DATASOURCE_NAME, storageId, entryId, data);
    }

    /**
     * Stores an entry to storage.
     * 
     * @param datasourceName
     * @param storageId
     * @param entryId
     * @param data
     */
    protected static void store(String datasourceName, String storageId, String entryId, byte[] data) {
        INosqlEngine nosqlEngine = nosqlEngine(datasourceName);
        nosqlEngine.store(storageId, entryId, data);
    }

    /**
     * Stores an entry to storage.
     * 
     * @param storageId
     * @param entryId
     * @param jsonData
     */
    protected static void store(String storageId, String entryId, String jsonData) {
        store(BaseDao.DEFAULT_DATASOURCE_NAME, storageId, entryId, jsonData);
    }

    /**
     * Stores an entry to storage.
     * 
     * @param datasourceName
     * @param storageId
     * @param entryId
     * @param jsonData
     */
    protected static void store(String datasourceName, String storageId, String entryId,
            String jsonData) {
        INosqlEngine nosqlEngine = nosqlEngine(datasourceName);
        nosqlEngine.store(storageId, entryId, jsonData);
    }

    /**
     * Stores an entry to storage.
     * 
     * @param storageId
     * @param entryId
     * @param data
     */
    protected static void store(String storageId, String entryId, Map<Object, Object> data) {
        store(BaseDao.DEFAULT_DATASOURCE_NAME, storageId, entryId, data);
    }

    /**
     * Stores an entry to storage.
     * 
     * @param datasourceName
     * @param storageId
     * @param entryId
     * @param data
     */
    protected static void store(String datasourceName, String storageId, String entryId,
            Map<Object, Object> data) {
        INosqlEngine nosqlEngine = nosqlEngine(datasourceName);
        nosqlEngine.store(storageId, entryId, data);
    }
}
