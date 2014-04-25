Plommon - com.github.ddth.plommon.bo
====================================

BO/DAO library.

package `plommon.bo`
--------------------
### class `BaseBo` ###
Base class for application BOs.

### class `BaseDao` ###
Base class for application DAOs.


package `plommon.bo.jdbc`
-------------------------
### class `BaseJdbcDao` ###
Base class for JDBC-based DAOs.

Note: `BaseJdbcDao` utilizes Spring's `JdbcTemplate` to query data.

### class `BaseMysqlDao` extends `BaseJdbcDao` ###
MySQL-specific DAO.


package `plommon.bo.nosql`
-------------------------
### class `BaseNosqlDao` ###
Base class for NoSQL-based DAOs.

### interface `INosqlEngine` ###
APIs to access the underlying NoSQL storage.


package `plommon.bo.nosql.engine`
---------------------------------
Implementation of `INosqlEngine`
