package com.github.ddth.plommon.bo.jdbc;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import play.db.DB;

import com.github.ddth.plommon.bo.BaseDao;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Base class for JDBC-based DAOs.
 * 
 * <p>
 * Note: {@link BaseJdbcDao} utilizes Spring's {@link JdbcTemplate} to query
 * data.
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.3.0
 * @since 0.5.0 renamed to BaseJdbcDao and moved in oto package
 *        {@code com.github.ddth.plommon.bo.jdbc.}
 */
public class BaseJdbcDao extends BaseDao {

    /*--------------------------------------------------------------------------------*/
    private static LoadingCache<String, JdbcTemplate> cachedjdbcTemplates = CacheBuilder
            .newBuilder().expireAfterAccess(3600, TimeUnit.SECONDS)
            .build(new CacheLoader<String, JdbcTemplate>() {
                @Override
                public JdbcTemplate load(String datasourceName) throws Exception {
                    return new JdbcTemplate(DB.getDataSource(datasourceName));
                }
            });

    /**
     * Called by Play's module activator routine.
     * 
     * @since 0.5.0
     */
    public static void activatePlugin() {
        cachedjdbcTemplates.invalidateAll();
    }

    /**
     * Called by Play's module activator routine.
     * 
     * @since 0.5.0
     */
    public static void inactivatePlugin() {
        cachedjdbcTemplates.invalidateAll();
    }

    /**
     * Gets {@link JdbcTemplate} instance for a given {@link Connection}.
     * 
     * @param conn
     * @return
     * @since 0.5.1
     */
    protected static JdbcTemplate jdbcTemplate(Connection conn) {
        DataSource ds = new SingleConnectionDataSource(conn, true);
        return new JdbcTemplate(ds);
    }

    /**
     * Gets {@link JdbcTemplate} instance mapped with the "default" datasource.
     * name.
     * 
     * @return
     */
    protected static JdbcTemplate jdbcTemplate() {
        return jdbcTemplate(DEFAULT_DATASOURCE_NAME);
    }

    /**
     * Gets {@link JdbcTemplate} instance mapped with a datasource name.
     * 
     * @param datasourceName
     * @return
     */
    protected static JdbcTemplate jdbcTemplate(String datasourceName) {
        try {
            return cachedjdbcTemplates.get(datasourceName);
        } catch (ExecutionException e) {
            return null;
        }
    }

    /*--------------------------------------------------------------------------------*/

    /**
     * Executes a DELETE statement.
     * 
     * @param sql
     * @param whereValues
     * @return number of deleted rows
     * @since 0.3.2
     * @since 0.4.1 add support of {@link ParamExpression}
     */
    protected static int delete(String sql, Object[] whereValues) {
        return delete(DEFAULT_DATASOURCE_NAME, sql, whereValues);
    }

    /**
     * Executes a DELETE statement.
     * 
     * @param datasourceName
     * @param sql
     * @param whereValues
     * @return number of deleted rows
     * @since 0.5.0
     */
    protected static int delete(String datasourceName, String sql, Object[] whereValues) {
        return delete(jdbcTemplate(datasourceName), sql, whereValues);
    }

    /**
     * Executes a DELETE statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param sql
     * @param whereValues
     * @return
     * @since 0.5.1
     */
    protected static int delete(Connection conn, String sql, Object[] whereValues) {
        return delete(jdbcTemplate(conn), sql, whereValues);
    }

    /**
     * Executes a DELETE statement.
     * 
     * @param jdbcTemplate
     * @param sql
     * @param whereValues
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int delete(JdbcTemplate jdbcTemplate, String sql, Object[] whereValues) {
        List<Object> params = new ArrayList<Object>();
        if (whereValues != null) {
            for (Object val : whereValues) {
                if (!(val instanceof ParamExpression)) {
                    params.add(val);
                }
            }
        }
        return params.size() > 0 ? jdbcTemplate.update(sql, params.toArray()) : jdbcTemplate
                .update(sql);
    }

    /**
     * Executes a DELETE statement (without WHERE clause).
     * 
     * @param tableName
     * @return number of deleted rows
     * @since 0.3.2
     */
    protected static int delete(String tableName) {
        return delete(DEFAULT_DATASOURCE_NAME, tableName);
    }

    /**
     * Executes a DELETE statement (without WHERE clause).
     * 
     * @param datasourceName
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.0
     */
    protected static int delete(String datasourceName, String tableName) {
        return delete(jdbcTemplate(datasourceName), tableName);
    }

    /**
     * Executes a DELETE statement (without WHERE clause).
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param tableName
     * @return
     * @since 0.5.1
     */
    protected static int delete(Connection conn, String tableName) {
        return delete(jdbcTemplate(conn), tableName);
    }

    /**
     * Executes a DELETE statement (without WHERE clause).
     * 
     * @param jdbcTemplate
     * @param tableName
     * @return
     * @since 0.5.1
     */
    private static int delete(JdbcTemplate jdbcTemplate, String tableName) {
        return delete(jdbcTemplate, tableName, null, null);
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
     * @since 0.4.1 add support of {@link ParamExpression}
     */
    protected static int delete(String tableName, String[] whereColumns, Object[] whereValues) {
        return delete(DEFAULT_DATASOURCE_NAME, tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of deleted rows
     * @since 0.5.0
     */
    protected static int delete(String datasourceName, String tableName, String[] whereColumns,
            Object[] whereValues) {
        return delete(jdbcTemplate(datasourceName), tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param tableName
     * @param whereColumns
     * @param whereValues
     * @return
     * @since 0.5.1
     */
    protected static int delete(Connection conn, String tableName, String[] whereColumns,
            Object[] whereValues) {
        return delete(jdbcTemplate(conn), tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of deleted rows
     * @since 0.5.1
     */
    private static int delete(JdbcTemplate jdbcTemplate, String tableName, String[] whereColumns,
            Object[] whereValues) {
        final String SQL_TEMPLATE_FULL = "DELETE FROM {0} WHERE {1}";
        final String SQL_TEMPLATE = "DELETE FROM {0}";

        final List<String> WHERE_CLAUSE = new ArrayList<String>();
        if (whereColumns != null && whereColumns.length > 0 && whereValues != null
                && whereValues.length > 0) {
            if (whereColumns.length != whereValues.length) {
                throw new IllegalArgumentException(
                        "Number of whereColumns must be equal to number of whereValues.");
            }
            for (int i = 0; i < whereColumns.length; i++) {
                if (whereValues[i] instanceof ParamExpression) {
                    WHERE_CLAUSE.add("(" + whereColumns[i] + "="
                            + ((ParamExpression) whereValues[i]).getExpression() + ")");
                } else {
                    WHERE_CLAUSE.add("(" + whereColumns[i] + "=?)");
                }
            }
        }

        String SQL;
        if (WHERE_CLAUSE.size() > 0) {
            SQL = MessageFormat.format(SQL_TEMPLATE_FULL, tableName,
                    StringUtils.join(WHERE_CLAUSE, " AND "));
        } else {
            SQL = MessageFormat.format(SQL_TEMPLATE, tableName);
        }
        return delete(jdbcTemplate, SQL, whereValues);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param sql
     * @param values
     * @return number of inserted rows
     * @since 0.3.2
     * @since 0.4.1 add support of {@link ParamExpression}
     */
    protected static int insert(String sql, Object[] values) {
        return insert(DEFAULT_DATASOURCE_NAME, sql, values);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param datasourceName
     * @param sql
     * @param values
     * @return number of inserted rows
     * @since 0.5.0
     */
    protected static int insert(String datasourceName, String sql, Object[] values) {
        return insert(jdbcTemplate(datasourceName), sql, values);
    }

    /**
     * Executes an INSERT statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param sql
     * @param values
     * @return
     * @since 0.5.1
     */
    protected static int insert(Connection conn, String sql, Object[] values) {
        return insert(jdbcTemplate(conn), sql, values);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param jdbcTemplate
     * @param sql
     * @param values
     * @return
     * @since 0.5.1
     */
    protected static int insert(JdbcTemplate jdbcTemplate, String sql, Object[] values) {
        List<Object> params = new ArrayList<Object>();
        if (values != null) {
            for (Object val : values) {
                if (!(val instanceof ParamExpression)) {
                    params.add(val);
                }
            }
        }
        return params.size() > 0 ? jdbcTemplate.update(sql, params.toArray()) : jdbcTemplate
                .update(sql);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.3.2
     * @since 0.4.1 add support of {@link ParamExpression}
     */
    protected static int insert(String tableName, String[] columnNames, Object[] values) {
        return insert(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.0
     */
    protected static int insert(String datasourceName, String tableName, String[] columnNames,
            Object[] values) {
        return insert(jdbcTemplate(datasourceName), tableName, columnNames, values);
    }

    /**
     * Executes an INSERT statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     * @since 0.5.1
     */
    protected static int insert(Connection conn, String tableName, String[] columnNames,
            Object[] values) {
        return insert(jdbcTemplate(conn), tableName, columnNames, values);
    }

    /**
     * Executes an INSERT statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.1
     */
    private static int insert(JdbcTemplate jdbcTemplate, String tableName, String[] columnNames,
            Object[] values) {
        if (columnNames.length != values.length) {
            throw new IllegalArgumentException(
                    "Number of columns must be equal to number of values.");
        }
        final String SQL_TEMPLATE = "INSERT INTO {0} ({1}) VALUES ({2})";
        final String SQL_PART_COLUMNS = StringUtils.join(columnNames, ',');
        final StringBuilder SQL_PART_VALUES = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof ParamExpression) {
                SQL_PART_VALUES.append(((ParamExpression) values[i]).getExpression());
            } else {
                SQL_PART_VALUES.append('?');
            }
            if (i < values.length - 1) {
                SQL_PART_VALUES.append(',');
            }
        }
        final String SQL = MessageFormat.format(SQL_TEMPLATE, tableName, SQL_PART_COLUMNS,
                SQL_PART_VALUES);
        return insert(jdbcTemplate, SQL, values);
    }

    /**
     * Executes a SELECT statement.
     * 
     * @param sql
     * @param paramValues
     * @return
     * @since 0.3.2
     * @since 0.4.1 add support of {@link ParamExpression}
     */
    protected static List<Map<String, Object>> select(String sql, Object[] paramValues) {
        return select(DEFAULT_DATASOURCE_NAME, sql, paramValues);
    }

    /**
     * Executes a SELECT statement.
     * 
     * @param datasourceName
     * @param sql
     * @param paramValues
     * @return
     * @since 0.5.0
     */
    protected static List<Map<String, Object>> select(String datasourceName, String sql,
            Object[] paramValues) {
        return select(jdbcTemplate(datasourceName), sql, paramValues);
    }

    /**
     * Executes a SELECT statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param sql
     * @param paramValues
     * @return
     * @since 0.5.1
     */
    protected static List<Map<String, Object>> select(Connection conn, String sql,
            Object[] paramValues) {
        return select(jdbcTemplate(conn), sql, paramValues);
    }

    /**
     * Executes a SELECT statement.
     * 
     * @param jdbcTemplate
     * @param sql
     * @param paramValues
     * @return
     * @since 0.5.1
     */
    protected static List<Map<String, Object>> select(JdbcTemplate jdbcTemplate, String sql,
            Object[] paramValues) {
        List<Object> params = new ArrayList<Object>();
        if (paramValues != null) {
            for (Object val : paramValues) {
                if (!(val instanceof ParamExpression)) {
                    params.add(val);
                }
            }
        }
        return params.size() > 0 ? jdbcTemplate.queryForList(sql, params.toArray()) : jdbcTemplate
                .queryForList(sql);
    }

    /**
     * Executes a simple SELECT statement.
     * 
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @return
     * @since 0.4.3
     */
    protected static List<Map<String, Object>> select(String table, String[][] columns,
            String whereClause, Object[] paramValues) {
        return select(DEFAULT_DATASOURCE_NAME, table, columns, whereClause, paramValues);
    }

    /**
     * Executes a simple SELECT statement.
     * 
     * @param datasourceName
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @return
     * @since 0.5.0
     */
    protected static List<Map<String, Object>> select(String datasourceName, String table,
            String[][] columns, String whereClause, Object[] paramValues) {
        return select(jdbcTemplate(datasourceName), table, columns, whereClause, paramValues);
    }

    /**
     * Executes a simple SELECT statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @return
     * @since 0.5.1
     */
    protected static List<Map<String, Object>> select(Connection conn, String table,
            String[][] columns, String whereClause, Object[] paramValues) {
        return select(jdbcTemplate(conn), table, columns, whereClause, paramValues);
    }

    /**
     * Executes a simple SELECT statement.
     * 
     * @param jdbcTemplate
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @return
     * @since 0.5.1
     */
    private static List<Map<String, Object>> select(JdbcTemplate jdbcTemplate, String table,
            String[][] columns, String whereClause, Object[] paramValues) {
        StringBuilder sql = new StringBuilder("SELECT ");

        for (String[] colDef : columns) {
            sql.append(colDef[0]);
            if (colDef.length > 1) {
                sql.append(" AS ").append(colDef[1]);
            }
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);

        sql.append(" FROM ").append(table);

        if (!StringUtils.isBlank(whereClause)) {
            sql.append(" WHERE ").append(whereClause);
        }

        return select(jdbcTemplate, sql.toString(), paramValues);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * @param sql
     * @param paramValues
     * @return number of affected rows
     * @since 0.3.2
     * @since 0.4.1 add support of {@link ParamExpression}
     */
    protected static int update(String sql, Object[] paramValues) {
        return update(DEFAULT_DATASOURCE_NAME, sql, paramValues);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * @param datasourceName
     * @param sql
     * @param paramValues
     * @return number of affected rows
     * @since 0.5.0
     */
    protected static int update(String datasourceName, String sql, Object[] paramValues) {
        return update(jdbcTemplate(datasourceName), sql, paramValues);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param sql
     * @param paramValues
     * @return number of affected rows
     * @since 0.5.1
     */
    protected static int update(Connection conn, String sql, Object[] paramValues) {
        return update(jdbcTemplate(conn), sql, paramValues);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * @param jdbcTemplate
     * @param sql
     * @param paramValues
     * @return number of affected rows
     * @since 0.5.1
     */
    protected static int update(JdbcTemplate jdbcTemplate, String sql, Object[] paramValues) {
        List<Object> params = new ArrayList<Object>();
        if (paramValues != null) {
            for (Object val : paramValues) {
                if (!(val instanceof ParamExpression)) {
                    params.add(val);
                }
            }
        }
        return params.size() > 0 ? jdbcTemplate.update(sql, params.toArray()) : jdbcTemplate
                .update(sql);
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
        return update(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values);
    }

    /**
     * Executes a UPDATE statement (without WHERE clause).
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of affected rows
     * @since 0.5.0
     */
    protected static int update(String datasourceName, String tableName, String[] columnNames,
            Object[] values) {
        return update(jdbcTemplate(datasourceName), tableName, columnNames, values);
    }

    /**
     * Executes a UPDATE statement (without WHERE clause).
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of affected rows
     * @since 0.5.1
     */
    protected static int update(Connection conn, String tableName, String[] columnNames,
            Object[] values) {
        return update(jdbcTemplate(conn), tableName, columnNames, values);
    }

    /**
     * Executes a UPDATE statement (without WHERE clause).
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of affected rows
     * @since 0.5.1
     */
    private static int update(JdbcTemplate jdbcTemplate, String tableName, String[] columnNames,
            Object[] values) {
        return update(jdbcTemplate, tableName, columnNames, values, null, null);
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
     * @since 0.4.1 add support of {@link ParamExpression}
     */
    protected static int update(String tableName, String[] columnNames, Object[] values,
            String[] whereColumns, Object[] whereValues) {
        return update(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values, whereColumns,
                whereValues);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of affected rows
     * @since 0.5.0
     */
    protected static int update(String datasourceName, String tableName, String[] columnNames,
            Object[] values, String[] whereColumns, Object[] whereValues) {
        return update(jdbcTemplate(datasourceName), tableName, columnNames, values, whereColumns,
                whereValues);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * Note: caller is responsible for closing the supplied connection.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of affected rows
     * @since 0.5.1
     */
    protected static int update(Connection conn, String tableName, String[] columnNames,
            Object[] values, String[] whereColumns, Object[] whereValues) {
        return update(jdbcTemplate(conn), tableName, columnNames, values, whereColumns, whereValues);
    }

    /**
     * Executes a UPDATE statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of affected rows
     * @since 0.5.1
     */
    private static int update(JdbcTemplate jdbcTemplate, String tableName, String[] columnNames,
            Object[] values, String[] whereColumns, Object[] whereValues) {
        final String SQL_TEMPLATE_FULL = "UPDATE {0} SET {1} WHERE {2}";
        final String SQL_TEMPLATE = "UPDATE {0} SET {1}";

        if (columnNames.length != values.length) {
            throw new IllegalArgumentException(
                    "Number of columns must be equal to number of values.");
        }
        final List<String> UPDATE_CLAUSE = new ArrayList<String>();
        for (int i = 0; i < columnNames.length; i++) {
            if (values[i] instanceof ParamExpression) {
                UPDATE_CLAUSE.add(columnNames[i] + "="
                        + ((ParamExpression) values[i]).getExpression());
            } else {
                UPDATE_CLAUSE.add(columnNames[i] + "=?");
            }
        }

        final List<String> WHERE_CLAUSE = new ArrayList<String>();
        if (whereColumns != null && whereColumns.length > 0 && whereValues != null
                && whereValues.length > 0) {
            if (whereColumns.length != whereValues.length) {
                throw new IllegalArgumentException(
                        "Number of whereColumns must be equal to number of whereValues.");
            }
            for (int i = 0; i < whereColumns.length; i++) {
                if (whereValues[i] instanceof ParamExpression) {
                    WHERE_CLAUSE.add("(" + whereColumns[i] + "="
                            + ((ParamExpression) whereValues[i]).getExpression() + ")");
                } else {
                    WHERE_CLAUSE.add("(" + whereColumns[i] + "=?)");
                }
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
        return update(jdbcTemplate, SQL, PARAM_VALUES);
    }

    /*--------------------------------------------------------------------------------*/
}
