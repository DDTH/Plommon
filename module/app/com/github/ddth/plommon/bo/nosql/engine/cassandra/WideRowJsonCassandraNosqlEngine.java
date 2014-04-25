package com.github.ddth.plommon.bo.nosql.engine.cassandra;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.github.ddth.commons.utils.SerializationUtils;

/**
 * Wide-row, Cassandra-specific NoSQL engine.
 * 
 * <p>
 * This engine assumes tables are created with the following schema:
 * </p>
 * 
 * <pre>
 * CREATE TABLE _table_name_ (
 *     id       text,
 *     key      text,
 *     value    text,
 *     PRIMARY KEY(id,key)
 * ) WITH COMPACT STORAGE;
 * </pre>
 * 
 * <p>
 * where each value is a JSON-encoded object
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.5.0
 */
public class WideRowJsonCassandraNosqlEngine extends BaseCassandraNosqlEngine {

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] load(String storageId, String entryId) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(String storageId, String entryId, byte[] data) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String tableName, String entryId) {
        final String CQL = MessageFormat.format("DELETE FROM {0} WHERE id=?", tableName);
        Session session = getSession();
        CassandraUtils.executeNonSelect(session, CQL, entryId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Object, Object> loadAsMap(String tableName, String entryId) {
        final String CQL = MessageFormat.format("SELECT id, key, value FROM {0} WHERE id=?",
                tableName);
        Session session = getSession();
        List<Row> rows = CassandraUtils.execute(session, CQL, entryId).all();
        if (rows == null || rows.size() == 0) {
            // not found
            return null;
        }
        Map<Object, Object> result = new HashMap<Object, Object>();
        for (Row row : rows) {
            String key = row.getString("key");
            String value = row.getString("value");
            result.put(key, SerializationUtils.fromJsonString(value, Object.class));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(String tableName, String entryId, Map<Object, Object> data) {
        final String CQL = MessageFormat.format("UPDATE {0} SET key=?, value=? WHERE id=?",
                tableName);
        Session session = getSession();
        PreparedStatement pstm = session.prepare(CQL);
        for (Entry<Object, Object> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = SerializationUtils.toJsonString(entry.getValue());
            CassandraUtils.executeNonSelect(session, pstm, key, value, entry);
        }
    }
}
