package com.github.ddth.plommon.bo;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.jdbc.core.JdbcTemplate;

import play.cache.Cache;
import play.db.DB;

/**
 * Base class for application DAOs.
 * 
 * <p>
 * Note: {@link BaseDao} utilizes Spring's {@link JdbcTemplate} to query data.
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.3.0
 */
public class BaseDao {

    /*--------------------------------------------------------------------------------*/
    private static ConcurrentMap<String, JdbcTemplate> jdbcTemplates = new ConcurrentHashMap<String, JdbcTemplate>();

    /**
     * This method should be called once within
     * {@code Global.onStart(Application)} method.
     */
    public static void init() {
        jdbcTemplates.clear();
    }

    /**
     * Gets {@link JdbcTemplate} instance for the "default" datasource.
     * 
     * @return
     */
    protected static JdbcTemplate jdbcTemplate() {
        return jdbcTemplate("default");
    }

    /**
     * Gets {@link JdbcTemplate} instance for a specified datasource.
     * 
     * @param datasourceName
     * @return
     */
    protected static JdbcTemplate jdbcTemplate(String datasourceName) {
        JdbcTemplate jdbcTemplate = jdbcTemplates.get(datasourceName);
        if (jdbcTemplate == null) {
            synchronized (jdbcTemplates) {
                // test again to make sure no other thread has created the
                // object
                jdbcTemplate = jdbcTemplates.get(datasourceName);
                if (jdbcTemplate == null) {
                    jdbcTemplate = new JdbcTemplate(DB.getDataSource(datasourceName));
                    jdbcTemplates.putIfAbsent(datasourceName, jdbcTemplate);
                }
            }
        }
        return jdbcTemplate;
    }

    /*--------------------------------------------------------------------------------*/

    /*--------------------------------------------------------------------------------*/
    /**
     * Executes a DELETE statement.
     * 
     * @param sql
     * @param whereValues
     * @return number of deleted rows
     * @since 0.3.2
     */
    protected static int delete(String sql, Object[] whereValues) {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        return whereValues != null && whereValues.length > 0 ? jdbcTemplate
                .update(sql, whereValues) : jdbcTemplate.update(sql);
    }

    /**
     * Executes a DELETE statement (without WHERE clause).
     * 
     * @param tableName
     * @return number of deleted rows
     * @since 0.3.2
     */
    protected static int delete(String tableName) {
        return delete(tableName, null, null);
    }

    /**
     * Executes a DELETE statement.
     * 
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of deleted rows
     * @since 0.3.2
     */
    protected static int delete(String tableName, String[] whereColumns, Object[] whereValues) {
        final String SQL_TEMPLATE_FULL = "DELETE FROM {0} WHERE {1}";
        final String SQL_TEMPLATE = "DELETE FROM {0}";

        final List<String> WHERE_CLAUSE = new ArrayList<String>();
        if (whereColumns != null && whereColumns.length > 0 && whereValues != null
                && whereValues.length > 0) {
            for (int i = 0; i < whereColumns.length; i++) {
                WHERE_CLAUSE.add("(" + whereColumns[i] + "=?)");
            }
        }

        String SQL;
        if (WHERE_CLAUSE.size() > 0) {
            SQL = MessageFormat.format(SQL_TEMPLATE_FULL, tableName,
                    StringUtils.join(WHERE_CLAUSE, " AND "));
        } else {
            SQL = MessageFormat.format(SQL_TEMPLATE, tableName);
        }
        return delete(SQL, whereValues);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param sql
     * @param values
     * @return number of inserted rows
     * @since 0.3.2
     */
    protected static int insert(String sql, Object[] values) {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        return values != null && values.length > 0 ? jdbcTemplate.update(sql, values)
                : jdbcTemplate.update(sql);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.3.2
     */
    protected static int insert(String tableName, String[] columnNames, Object[] values) {
        final String SQL_TEMPLATE = "INSERT INTO {0} ({1}) VALUES ({2})";
        final String SQL_PART_COLUMNS = StringUtils.join(columnNames, ',');
        final String SQL_PART_VALUES = StringUtils.repeat("?", ",", columnNames.length);
        final String SQL = MessageFormat.format(SQL_TEMPLATE, tableName, SQL_PART_COLUMNS,
                SQL_PART_VALUES);
        return insert(SQL, values);
    }

    /**
     * Executes a SELECT statement.
     * 
     * @param sql
     * @param paramValues
     * @return
     * @since 0.3.2
     */
    protected static List<Map<String, Object>> select(String sql, Object[] paramValues) {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        return paramValues != null && paramValues.length > 0 ? jdbcTemplate.queryForList(sql,
                paramValues) : jdbcTemplate.queryForList(sql);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * @param sql
     * @param paramValues
     * @return number of affected rows
     * @since 0.3.2
     */
    protected static int update(String sql, Object[] paramValues) {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        return paramValues != null && paramValues.length > 0 ? jdbcTemplate
                .update(sql, paramValues) : jdbcTemplate.update(sql);
    }

    /**
     * Executes a UPDATE statement (without WHERE clause).
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of affected rows
     * @since 0.3.2
     */
    protected static int update(String tableName, String[] columnNames, Object[] values) {
        return update(tableName, columnNames, values, null, null);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of affected rows
     * @since 0.3.2
     */
    protected static int update(String tableName, String[] columnNames, Object[] values,
            String[] whereColumns, Object[] whereValues) {
        final String SQL_TEMPLATE_FULL = "UPDATE {0} SET {1} WHERE {2}";
        final String SQL_TEMPLATE = "UPDATE {0} SET {1}";

        final List<String> UPDATE_CLAUSE = new ArrayList<String>();
        for (int i = 0; i < columnNames.length; i++) {
            UPDATE_CLAUSE.add(columnNames[i] + "=?");
        }

        final List<String> WHERE_CLAUSE = new ArrayList<String>();
        if (whereColumns != null && whereColumns.length > 0 && whereValues != null
                && whereValues.length > 0) {
            for (int i = 0; i < whereColumns.length; i++) {
                WHERE_CLAUSE.add("(" + whereColumns[i] + "=?)");
            }
        }

        String SQL;
        Object[] PARAM_VALUES;
        if (WHERE_CLAUSE.size() > 0) {
            SQL = MessageFormat.format(SQL_TEMPLATE_FULL, tableName,
                    StringUtils.join(UPDATE_CLAUSE, ','), StringUtils.join(WHERE_CLAUSE, " AND "));
            PARAM_VALUES = ArrayUtils.addAll(values, whereValues);
        } else {
            SQL = MessageFormat.format(SQL_TEMPLATE, tableName,
                    StringUtils.join(UPDATE_CLAUSE, ','));
            PARAM_VALUES = values;
        }
        return update(SQL, PARAM_VALUES);
    }

    /*--------------------------------------------------------------------------------*/

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

    /*--------------------------------------------------------------------------------*/
}
