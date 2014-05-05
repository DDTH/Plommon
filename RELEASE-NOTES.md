play-module-plommon
===================

0.5.1.1 - 2014-54-05
--------------------
- `BaseJdbcDao` and `BaseMysqlDao`: add new helper methods to perform SQL statements on a supplied `Connection`/`JdbcTemplate`.


0.5.0 - 2014-04-25
------------------
- Refactor package `plommon.bo`:
  - `plommon.bo.jdbc`: for JDBC-based DAOs.
  - `plommon.bo.nosql`: for NoSQL-based DAOs.
- Add class to support Cassandra Wide-row column family: `plommon.bo.nosql.engine.cassandra.WideRowJsonCassandraNosqlEngine`


0.4.7 - 2014-04-02
------------------
- New class: `plommon.utils.AkkUtils`.


0.4.6 - 2014-03-24
------------------
- New class: `plommon.utils.PlayAppUtils`.
- Dependencies reviewed, upgraded and cleaned up.


0.4.5 - 2013-12-31
------------------
- New class: `plommon.Activator`


0.4.4 - 2013-12-30
------------------
- Bug fixes


0.4.3 - 2013-12-07
------------------
- Stable release: version 0.4.3


0.4.3-SNAPSHOT - 2013-12-06
---------------------------
- New methods: `plommon.bo.BaseDao.select(String, String[][], String, Object[])`, `plommon.bo.BaseMysqlDao.select(String, String[][], String, Object[], int, int)`.


0.4.2-SNAPSHOT - 2013-11-25
---------------------------
- New method `plommon.utils.SessionUtils.removeSession(String)`.


0.4.1-SNAPSHOT - 2013-11-24
---------------------------
- New class `plommon.bo.ParamExpression`.


0.4.0-SNAPSHOT - 2013-11-21
---------------------------
- Removed classes `DPathUtils`, `HashUtils`, `IdGenerator`, `JsonUtils`, `UnsignedUtils` from package `plommon.utils`.
They are moved to [ddth-commons](https://github.com/DDTH/ddth-commons).
- Minor fix `plommon.bo.BaseBo`.


0.3.3 - 2013-11-21
------------------
- Stable release: version 0.3.3.


0.3.3-SNAPSHOT - 2013-11-20
---------------------------
- New class `plommon.utils.HashUtils`.


0.3.2-SNAPSHOT - 2013-09-27
---------------------------
- New class `plommon.bo.BaseMysqlDao`.
- New helper methods in class `plommon.bo.BaseDao` to execute common SQL statements (`INSERT`, `UPDATE`, `DELETE`, `SELECT`).


0.3.1-SNAPSHOT - 2013-09-26
---------------------------
- Upgrade to Playframework v2.2.0.
- New method `plommon.bo.BaseBo.isDirty()` and `plommon.bo.BaseBo.hashCode()`.


0.3.0-SNAPSHOT - 2013-09-10
---------------------------
- New package `plommon.bo`.


0.2.1-SNAPSHOT - 2013-09-01
---------------------------
- New method `JsonUtils.fromJsonString(String)` and minor enhancement in method `JsonUtils.fromJsonString(String, Class)`.
- Minor fix in method `SessionUtils.getSession(String, boolean)`.


0.2.0-SNAPSHOT - 2013-08-28
---------------------------
- New classes `UnsignedUtils`, and `IdGenerator`.


0.1.0 - 2013-08-26
------------------
- Stable release: version 0.1.0.


0.1.0-SNAPSHOT - 2013-08-20
---------------------------
- New classes `DPathsUtils`, `JsonUtils`, and `SessionUtils`.
