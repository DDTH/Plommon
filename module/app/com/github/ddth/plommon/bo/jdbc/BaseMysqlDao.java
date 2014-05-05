package com.github.ddth.plommon.bo.jdbc;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * MySQL-specific DAO.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.3.2
 */
public class BaseMysqlDao extends BaseJdbcDao {

    /**
     * Since 0.5.0
     */
    public static enum ExDelete {
        IGNORE, LOW_PRIORITY, QUICK
    }

    /**
     * Since 0.5.0
     */
    public static enum ExInsert {
        IGNORE, LOW_PRIORITY, DELAYED
    }

    /**
     * Since 0.5.0
     */
    public static enum ExUpdate {
        IGNORE, LOW_PRIORITY
    }

    /**
     * Executes a DELETE IGNORE statement (without WHERE clause).
     * 
     * @param tableName
     * @return number of deleted rows
     */
    protected static int deleteIgnore(String tableName) {
        return deleteIgnore(DEFAULT_DATASOURCE_NAME, tableName);
    }

    /**
     * Executes a DELETE IGNORE statement (without WHERE clause).
     * 
     * @param datasourceName
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.0
     */
    protected static int deleteIgnore(String datasourceName, String tableName) {
        return deleteEx(ExDelete.IGNORE, jdbcTemplate(datasourceName), tableName, null, null);
    }

    /**
     * Executes a DELETE IGNORE statement (without WHERE clause).
     * 
     * @param conn
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteIgnore(Connection conn, String tableName) {
        return deleteEx(ExDelete.IGNORE, jdbcTemplate(conn), tableName, null, null);
    }

    /**
     * Executes a DELETE IGNORE statement (without WHERE clause).
     * 
     * @param jdbcTemplate
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteIgnore(JdbcTemplate jdbcTemplate, String tableName) {
        return deleteEx(ExDelete.IGNORE, jdbcTemplate, tableName, null, null);
    }

    /**
     * Executes a DELETE IGNORE statement.
     * 
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number deleted rows
     */
    protected static int deleteIgnore(String tableName, String[] whereColumns, Object[] whereValues) {
        return deleteIgnore(DEFAULT_DATASOURCE_NAME, tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE IGNORE statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number deleted rows
     * @since 0.5.0
     */
    protected static int deleteIgnore(String datasourceName, String tableName,
            String[] whereColumns, Object[] whereValues) {
        return deleteEx(ExDelete.IGNORE, jdbcTemplate(datasourceName), tableName, whereColumns,
                whereValues);
    }

    /**
     * Executes a DELETE IGNORE statement.
     * 
     * @param conn
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number deleted rows
     * @since 0.5.1
     */
    protected static int deleteIgnore(Connection conn, String tableName, String[] whereColumns,
            Object[] whereValues) {
        return deleteEx(ExDelete.IGNORE, jdbcTemplate(conn), tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE IGNORE statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number deleted rows
     * @since 0.5.1
     */
    protected static int deleteIgnore(JdbcTemplate jdbcTemplate, String tableName,
            String[] whereColumns, Object[] whereValues) {
        return deleteEx(ExDelete.IGNORE, jdbcTemplate, tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param tableName
     * @return number of deleted rows
     */
    protected static int deleteLowPriority(String tableName) {
        return deleteLowPriority(DEFAULT_DATASOURCE_NAME, tableName);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param datasourceName
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.0
     */
    protected static int deleteLowPriority(String datasourceName, String tableName) {
        return deleteEx(ExDelete.LOW_PRIORITY, jdbcTemplate(datasourceName), tableName, null, null);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param conn
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteLowPriority(Connection conn, String tableName) {
        return deleteEx(ExDelete.LOW_PRIORITY, jdbcTemplate(conn), tableName, null, null);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param jdbcTemplate
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteLowPriority(JdbcTemplate jdbcTemplate, String tableName) {
        return deleteEx(ExDelete.LOW_PRIORITY, jdbcTemplate, tableName, null, null);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement.
     * 
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of deleted rows
     */
    protected static int deleteLowPriority(String tableName, String[] whereColumns,
            Object[] whereValues) {
        return deleteLowPriority(DEFAULT_DATASOURCE_NAME, tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement.
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
    protected static int deleteLowPriority(String datasourceName, String tableName,
            String[] whereColumns, Object[] whereValues) {
        return deleteEx(ExDelete.LOW_PRIORITY, jdbcTemplate(datasourceName), tableName,
                whereColumns, whereValues);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement.
     * 
     * @param conn
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteLowPriority(Connection conn, String tableName,
            String[] whereColumns, Object[] whereValues) {
        return deleteEx(ExDelete.LOW_PRIORITY, jdbcTemplate(conn), tableName, whereColumns,
                whereValues);
    }

    /**
     * Executes a DELETE LOW_PRIORITY statement.
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
    protected static int deleteLowPriority(JdbcTemplate jdbcTemplate, String tableName,
            String[] whereColumns, Object[] whereValues) {
        return deleteEx(ExDelete.LOW_PRIORITY, jdbcTemplate, tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE QUICK statement (without WHERE clause).
     * 
     * @param tableName
     * @return number of deleted rows
     */
    protected static int deleteQuick(String tableName) {
        return deleteQuick(DEFAULT_DATASOURCE_NAME, tableName);
    }

    /**
     * Executes a DELETE QUICK statement (without WHERE clause).
     * 
     * @param datasourceName
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.0
     */
    protected static int deleteQuick(String datasourceName, String tableName) {
        return deleteEx(ExDelete.QUICK, jdbcTemplate(datasourceName), tableName, null, null);
    }

    /**
     * Executes a DELETE QUICK statement (without WHERE clause).
     * 
     * @param conn
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteQuick(Connection conn, String tableName) {
        return deleteEx(ExDelete.QUICK, jdbcTemplate(conn), tableName, null, null);
    }

    /**
     * Executes a DELETE QUICK statement (without WHERE clause).
     * 
     * @param jdbcTemplate
     * @param tableName
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteQuick(JdbcTemplate jdbcTemplate, String tableName) {
        return deleteEx(ExDelete.QUICK, jdbcTemplate, tableName, null, null);
    }

    /**
     * Executes a DELETE QUICK statement.
     * 
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of deleted rows
     */
    protected static int deleteQuick(String tableName, String[] whereColumns, Object[] whereValues) {
        return deleteQuick(DEFAULT_DATASOURCE_NAME, tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE QUICK statement.
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
    protected static int deleteQuick(String datasourceName, String tableName,
            String[] whereColumns, Object[] whereValues) {
        return deleteEx(ExDelete.QUICK, jdbcTemplate(datasourceName), tableName, whereColumns,
                whereValues);
    }

    /**
     * Executes a DELETE QUICK statement.
     * 
     * @param conn
     * @param tableName
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number of deleted rows
     * @since 0.5.1
     */
    protected static int deleteQuick(Connection conn, String tableName, String[] whereColumns,
            Object[] whereValues) {
        return deleteEx(ExDelete.QUICK, jdbcTemplate(conn), tableName, whereColumns, whereValues);
    }

    /**
     * Executes a DELETE QUICK statement.
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
    protected static int deleteQuick(JdbcTemplate jdbcTemplate, String tableName,
            String[] whereColumns, Object[] whereValues) {
        return deleteEx(ExDelete.QUICK, jdbcTemplate, tableName, whereColumns, whereValues);
    }

    /*
     * @since 0.4.1 add support of {@link ParamExpression}
     * 
     * @since 0.5.0 add parameter {@code datasourceName} and change type of
     * parameter {@code ex} to {@code ExDelete}
     * 
     * @since 0.5.1 change parameter {@code datasourceName} to {@code
     * jdbcTemplate}.
     */
    private static int deleteEx(ExDelete ex, JdbcTemplate jdbcTemplate, String tableName,
            String[] whereColumns, Object[] whereValues) {
        final String SQL_TEMPLATE_FULL_IGNORE = "DELETE IGNORE FROM {0} WHERE {1}";
        final String SQL_TEMPLATE_IGNORE = "DELETE IGNORE FROM {0}";
        final String SQL_TEMPLATE_FULL_LOW_PRIORITY = "DELETE LOW_PRIORITY FROM {0} WHERE {1}";
        final String SQL_TEMPLATE_LOW_PRIORITY = "DELETE LOW_PRIORITY FROM {0}";
        final String SQL_TEMPLATE_FULL_QUICK = "DELETE QUICK FROM {0} WHERE {1}";
        final String SQL_TEMPLATE_QUICK = "DELETE QUICK FROM {0}";

        final String SQL_TEMPLATE_FULL = ex == ExDelete.IGNORE ? SQL_TEMPLATE_FULL_IGNORE
                : (ex == ExDelete.QUICK ? SQL_TEMPLATE_FULL_QUICK : SQL_TEMPLATE_FULL_LOW_PRIORITY);
        final String SQL_TEMPLATE = ex == ExDelete.IGNORE ? SQL_TEMPLATE_IGNORE
                : (ex == ExDelete.QUICK ? SQL_TEMPLATE_QUICK : SQL_TEMPLATE_LOW_PRIORITY);

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
     * Executes an INSERT DELAYED statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     */
    protected static int insertDelayed(String tableName, String[] columnNames, Object[] values) {
        return insertDelayed(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values);
    }

    /**
     * Executes an INSERT DELAYED statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.0
     */
    protected static int insertDelayed(String datasourceName, String tableName,
            String[] columnNames, Object[] values) {
        return insertEx(ExInsert.DELAYED, jdbcTemplate(datasourceName), tableName, columnNames,
                values);
    }

    /**
     * Executes an INSERT DELAYED statement.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.1
     */
    protected static int insertDelayed(Connection conn, String tableName, String[] columnNames,
            Object[] values) {
        return insertEx(ExInsert.DELAYED, jdbcTemplate(conn), tableName, columnNames, values);
    }

    /**
     * Executes an INSERT DELAYED statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.1
     */
    protected static int insertDelayed(JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values) {
        return insertEx(ExInsert.DELAYED, jdbcTemplate, tableName, columnNames, values);
    }

    /**
     * Executes an INSERT IGNORE statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     */
    protected static int insertIgnore(String tableName, String[] columnNames, Object[] values) {
        return insertIgnore(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values);
    }

    /**
     * Executes an INSERT IGNORE statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.0
     */
    protected static int insertIgnore(String datasourceName, String tableName,
            String[] columnNames, Object[] values) {
        return insertEx(ExInsert.IGNORE, jdbcTemplate(datasourceName), tableName, columnNames,
                values);
    }

    /**
     * Executes an INSERT IGNORE statement.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.1
     */
    protected static int insertIgnore(Connection conn, String tableName, String[] columnNames,
            Object[] values) {
        return insertEx(ExInsert.IGNORE, jdbcTemplate(conn), tableName, columnNames, values);
    }

    /**
     * Executes an INSERT IGNORE statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.1
     */
    protected static int insertIgnore(JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values) {
        return insertEx(ExInsert.IGNORE, jdbcTemplate, tableName, columnNames, values);
    }

    /**
     * Executes an INSERT LOW_PRIORITY statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     */
    protected static int insertLowPriority(String tableName, String[] columnNames, Object[] values) {
        return insertLowPriority(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values);
    }

    /**
     * Executes an INSERT LOW_PRIORITY statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.0
     */
    protected static int insertLowPriority(String datasourceName, String tableName,
            String[] columnNames, Object[] values) {
        return insertEx(ExInsert.LOW_PRIORITY, jdbcTemplate(datasourceName), tableName,
                columnNames, values);
    }

    /**
     * Executes an INSERT LOW_PRIORITY statement.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.1
     */
    protected static int insertLowPriority(Connection conn, String tableName, String[] columnNames,
            Object[] values) {
        return insertEx(ExInsert.LOW_PRIORITY, jdbcTemplate(conn), tableName, columnNames, values);
    }

    /**
     * Executes an INSERT LOW_PRIORITY statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @return number of inserted rows
     * @since 0.5.1
     */
    protected static int insertLowPriority(JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values) {
        return insertEx(ExInsert.LOW_PRIORITY, jdbcTemplate, tableName, columnNames, values);
    }

    /*
     * @since 0.4.1 add support of {@link ParamExpression}
     * 
     * @since 0.5.0 add parameter {@code datasourceName} and change type of
     * parameter {@code ex} to {@code ExInsert}
     * 
     * @since 0.5.1 change parameter {@code datasourceName} to {@code
     * jdbcTemplate}.
     */
    private static int insertEx(ExInsert ex, JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values) {
        if (columnNames.length != values.length) {
            throw new IllegalArgumentException(
                    "Number of columns must be equal to number of values.");
        }
        final String SQL_TEMPLATE_DELAYED = "INSERT DELAYED INTO {0} ({1}) VALUES ({2})";
        final String SQL_TEMPLATE_IGNORE = "INSERT IGNORE INTO {0} ({1}) VALUES ({2})";
        final String SQL_TEMPLATE_LOW_PRIORITY = "INSERT LOW_PRIORITY INTO {0} ({1}) VALUES ({2})";

        final String SQL_TEMPLATE = ex == ExInsert.DELAYED ? SQL_TEMPLATE_DELAYED
                : (ex == ExInsert.IGNORE ? SQL_TEMPLATE_IGNORE : SQL_TEMPLATE_LOW_PRIORITY);
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
     * Executes a simple SELECT statement with LIMIT.
     * 
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @param limitOffset
     * @param limitRowCount
     * @return
     * @since 0.4.3
     */
    protected static List<Map<String, Object>> select(String table, String[][] columns,
            String whereClause, Object[] paramValues, int limitOffset, int limitRowCount) {
        return select(DEFAULT_DATASOURCE_NAME, table, columns, whereClause, paramValues,
                limitOffset, limitRowCount);
    }

    /**
     * Executes a simple SELECT statement with LIMIT.
     * 
     * @param datasourceName
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @param limitOffset
     * @param limitRowCount
     * @return
     * @since 0.5.0
     */
    protected static List<Map<String, Object>> select(String datasourceName, String table,
            String[][] columns, String whereClause, Object[] paramValues, int limitOffset,
            int limitRowCount) {
        return select(jdbcTemplate(datasourceName), table, columns, whereClause, paramValues,
                limitOffset, limitRowCount);
    }

    /**
     * Executes a simple SELECT statement with LIMIT.
     * 
     * @param conn
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @param limitOffset
     * @param limitRowCount
     * @return
     * @since 0.5.1
     */
    protected static List<Map<String, Object>> select(Connection conn, String table,
            String[][] columns, String whereClause, Object[] paramValues, int limitOffset,
            int limitRowCount) {
        return select(jdbcTemplate(conn), table, columns, whereClause, paramValues, limitOffset,
                limitRowCount);
    }

    /**
     * Executes a simple SELECT statement with LIMIT.
     * 
     * @param jdbcTemplate
     * @param table
     * @param columns
     * @param whereClause
     * @param paramValues
     * @param limitOffset
     * @param limitRowCount
     * @return
     * @since 0.5.1
     */
    protected static List<Map<String, Object>> select(JdbcTemplate jdbcTemplate, String table,
            String[][] columns, String whereClause, Object[] paramValues, int limitOffset,
            int limitRowCount) {
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

        sql.append(" LIMIT ").append(limitOffset).append(",").append(limitRowCount);

        return select(jdbcTemplate, sql.toString(), paramValues);
    }

    /**
     * Executes a UPDATE IGNORE statement (without WHERE clause).
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     */
    protected static int updateIgnore(String tableName, String[] columnNames, Object[] values) {
        return updateIgnore(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values);
    }

    /**
     * Executes a UPDATE IGNORE statement (without WHERE clause).
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     * @since 0.5.0
     */
    protected static int updateIgnore(String datasourceName, String tableName,
            String[] columnNames, Object[] values) {
        return updateEx(ExUpdate.IGNORE, jdbcTemplate(datasourceName), tableName, columnNames,
                values, null, null);
    }

    /**
     * Executes a UPDATE IGNORE statement (without WHERE clause).
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     * @since 0.5.1
     */
    protected static int updateIgnore(Connection conn, String tableName, String[] columnNames,
            Object[] values) {
        return updateEx(ExUpdate.IGNORE, jdbcTemplate(conn), tableName, columnNames, values, null,
                null);
    }

    /**
     * Executes a UPDATE IGNORE statement (without WHERE clause).
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     * @since 0.5.1
     */
    protected static int updateIgnore(JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values) {
        return updateEx(ExUpdate.IGNORE, jdbcTemplate, tableName, columnNames, values, null, null);
    }

    /**
     * Executes a UPDATE IGNORE statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     */
    protected static int updateIgnore(String tableName, String[] columnNames, Object[] values,
            String[] whereColumns, Object[] whereValues) {
        return updateIgnore(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values, whereColumns,
                whereValues);
    }

    /**
     * Executes a UPDATE IGNORE statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     * @since 0.5.0
     */
    protected static int updateIgnore(String datasourceName, String tableName,
            String[] columnNames, Object[] values, String[] whereColumns, Object[] whereValues) {
        return updateEx(ExUpdate.IGNORE, jdbcTemplate(datasourceName), tableName, columnNames,
                values, whereColumns, whereValues);
    }

    /**
     * Executes a UPDATE IGNORE statement.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     * @since 0.5.1
     */
    protected static int updateIgnore(Connection conn, String tableName, String[] columnNames,
            Object[] values, String[] whereColumns, Object[] whereValues) {
        return updateEx(ExUpdate.IGNORE, jdbcTemplate(conn), tableName, columnNames, values,
                whereColumns, whereValues);
    }

    /**
     * Executes a UPDATE IGNORE statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     * @since 0.5.1
     */
    protected static int updateIgnore(JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values, String[] whereColumns, Object[] whereValues) {
        return updateEx(ExUpdate.IGNORE, jdbcTemplate, tableName, columnNames, values,
                whereColumns, whereValues);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     */
    protected static int updateLowPriority(String tableName, String[] columnNames, Object[] values) {
        return updateLowPriority(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     * @since 0.5.0
     */
    protected static int updateLowPriority(String datasourceName, String tableName,
            String[] columnNames, Object[] values) {
        return updateEx(ExUpdate.LOW_PRIORITY, jdbcTemplate(datasourceName), tableName,
                columnNames, values, null, null);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     * @since 0.5.0
     */
    protected static int updateLowPriority(Connection conn, String tableName, String[] columnNames,
            Object[] values) {
        return updateEx(ExUpdate.LOW_PRIORITY, jdbcTemplate(conn), tableName, columnNames, values,
                null, null);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement (without WHERE clause).
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @return number affected rows
     * @since 0.5.0
     */
    protected static int updateLowPriority(JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values) {
        return updateEx(ExUpdate.LOW_PRIORITY, jdbcTemplate, tableName, columnNames, values, null,
                null);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     */
    protected static int updateLowPriority(String tableName, String[] columnNames, Object[] values,
            String[] whereColumns, Object[] whereValues) {
        return updateLowPriority(DEFAULT_DATASOURCE_NAME, tableName, columnNames, values,
                whereColumns, whereValues);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement.
     * 
     * @param datasourceName
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     * @since 0.5.0
     */
    protected static int updateLowPriority(String datasourceName, String tableName,
            String[] columnNames, Object[] values, String[] whereColumns, Object[] whereValues) {
        return updateEx(ExUpdate.LOW_PRIORITY, jdbcTemplate(datasourceName), tableName,
                columnNames, values, whereColumns, whereValues);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement.
     * 
     * @param conn
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     * @since 0.5.1
     */
    protected static int updateLowPriority(Connection conn, String tableName, String[] columnNames,
            Object[] values, String[] whereColumns, Object[] whereValues) {
        return updateEx(ExUpdate.LOW_PRIORITY, jdbcTemplate(conn), tableName, columnNames, values,
                whereColumns, whereValues);
    }

    /**
     * Executes a UPDATE LOW_PRIORITY statement.
     * 
     * @param jdbcTemplate
     * @param tableName
     * @param columnNames
     * @param values
     * @param whereColumns
     *            supply {@code null} to ignore WHERE clause
     * @param whereValues
     *            supply {@code null} to ignore WHERE clause
     * @return number affected rows
     * @since 0.5.1
     */
    protected static int updateLowPriority(JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values, String[] whereColumns, Object[] whereValues) {
        return updateEx(ExUpdate.LOW_PRIORITY, jdbcTemplate, tableName, columnNames, values,
                whereColumns, whereValues);
    }

    /*
     * @since 0.4.1 add support of {@link ParamExpression}
     * 
     * @since 0.5.0 add parameter {@code datasourceName} and change type of
     * parameter {@code ex} to {@code ExUpdate}
     * 
     * @since 0.5.1 change parameter {@code datasourceName} to {@code
     * jdbcTemplate}.
     */
    private static int updateEx(ExUpdate ex, JdbcTemplate jdbcTemplate, String tableName,
            String[] columnNames, Object[] values, String[] whereColumns, Object[] whereValues) {
        final String SQL_TEMPLATE_FULL_IGNORE = "UPDATE IGNORE {0} SET {1} WHERE {2}";
        final String SQL_TEMPLATE_IGNORE = "UPDATE IGNORE {0} SET {1}";
        final String SQL_TEMPLATE_FULL_LOW_PRIORITY = "UPDATE LOW_PRIORITY {0} SET {1} WHERE {2}";
        final String SQL_TEMPLATE_LOW_PRIORITY = "UPDATE LOW_PRIORITY {0} SET {1}";

        final String SQL_TEMPLATE_FULL = ex == ExUpdate.IGNORE ? SQL_TEMPLATE_FULL_IGNORE
                : SQL_TEMPLATE_FULL_LOW_PRIORITY;
        final String SQL_TEMPLATE = ex == ExUpdate.IGNORE ? SQL_TEMPLATE_IGNORE
                : SQL_TEMPLATE_LOW_PRIORITY;

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
        if (whereColumns != null && whereColumns.length > 0 && whereValues != null
                && whereValues.length > 0) {
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

}
