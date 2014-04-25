package com.github.ddth.plommon.bo.nosql.engine.cassandra;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import play.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.github.ddth.commons.utils.SerializationUtils;
import com.github.ddth.plommon.bo.BaseDao;
import com.github.ddth.plommon.bo.nosql.engine.BaseNosqlEngine;
import com.github.ddth.plommon.utils.PlayAppUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Base class for Cassandra-specific NoSQL engines.
 * 
 * <p>
 * This engine utilizes Datastax's CQL driver (http://www.datastax.com/) to
 * access Cassandra.
 * </p>
 * 
 * <p>
 * This base Cassandra engine assumes sub-class will implement two method
 * {@link #load(String, String)} and {@link #store(String, String, byte[])}.
 * Hence, default implementation of {@link #loadAsMap(String, String)} delegates
 * the task to {@link #loadAsJson(String, String)}, which in turn delegates
 * method calls to {@link #load(String, String)}. Likewise, default
 * implementation of {@link #store(String, String, Map)} delegates the task to
 * {@link #store(String, String, String)}, which in turn delegates method calls
 * to {@link #store(String, String, byte[])}.
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.5.0
 */
public abstract class BaseCassandraNosqlEngine extends BaseNosqlEngine {

    private String datasourceName = BaseDao.DEFAULT_DATASOURCE_NAME;
    private List<String> hosts = new ArrayList<String>();
    private int port;
    private String keyspace = "";

    private Cluster cluster;
    private LoadingCache<String, Session> sessions = CacheBuilder.newBuilder()
            .expireAfterAccess(3600, TimeUnit.SECONDS)
            .removalListener(new RemovalListener<String, Session>() {
                @Override
                public void onRemoval(RemovalNotification<String, Session> entry) {
                    entry.getValue().close();
                }
            }).build(new CacheLoader<String, Session>() {
                @Override
                public Session load(String keyspace) throws Exception {
                    return _newSession(keyspace);
                }
            });

    public final static String CONF_KEY_HOSTS = "plommon.bo.cassandra.{0}.hosts";
    public final static String CONF_KEY_PORT = "plommon.bo.cassandra.{0}.port";
    public final static String CONF_KEY_KEYSPACE = "plommon.bo.cassandra.{0}.keyspace";

    private Session _newSession(String keyspace) {
        return StringUtils.isEmpty(keyspace) ? cluster.connect() : cluster.connect(keyspace);
    }

    public BaseCassandraNosqlEngine init() {
        super.init();

        _initConfig(datasourceName);
        _initCluster();

        return this;
    }

    public void destroy() {
        _destroyCluster();

        super.destroy();
    }

    private void _initConfig(String datasourceName) {
        String confHosts = PlayAppUtils.appConfigString(MessageFormat.format(CONF_KEY_HOSTS,
                datasourceName));
        String[] tokens = confHosts.split("[,\\s]+");
        for (String host : tokens) {
            hosts.add(host);
        }

        Integer confPort = PlayAppUtils.appConfigInteger(MessageFormat.format(CONF_KEY_PORT,
                datasourceName));
        port = confPort != null ? confPort.intValue() : 0;

        String confKeyspace = PlayAppUtils.appConfigString(MessageFormat.format(CONF_KEY_KEYSPACE,
                datasourceName));
        if (confKeyspace != null) {
            keyspace = confKeyspace;
        }
    }

    private final static String[] EMPTY_STRING_ARR = new String[0];

    private void _initCluster() {
        cluster = Cluster.builder().addContactPoints(hosts.toArray(EMPTY_STRING_ARR))
                .withPort(port != 0 ? port : 9042).build();
    }

    private void _destroyCluster() {
        try {
            if (cluster != null) {
                cluster.close();
                cluster = null;
            }
        } catch (Exception e) {
            Logger.warn(e.getMessage(), e);
        }
    }

    protected Session getSession() {
        return getSession(keyspace);
    }

    protected Session getSession(String keyspace) {
        try {
            return sessions.get(keyspace);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /*------------------------------------------------------------*/

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This method simply returns {@code null}. Sub-class overrides this method
     * to implement its own business.
     * </p>
     */
    @Override
    public byte[] load(String storageId, String entryId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String loadAsJson(String storageId, String entryId) {
        byte[] data = load(storageId, entryId);
        return data != null ? new String(data, CHARSET) : "null";
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<Object, Object> loadAsMap(String storageId, String entryId) {
        String json = loadAsJson(storageId, entryId);
        return SerializationUtils.fromJsonString(json, Map.class);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This method is a no-op. Sub-class overrides this method to implement its
     * own business logic.
     * </p>
     */
    @Override
    public void store(String storageId, String entryId, byte[] data) {
        // EMPTY
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(String storageId, String entryId, String jsonData) {
        byte[] data = jsonData != null ? jsonData.getBytes(CHARSET) : null;
        store(storageId, entryId, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(String storageId, String entryId, Map<Object, Object> data) {
        String json = SerializationUtils.toJsonString(data);
        store(storageId, entryId, json);
    }
}
