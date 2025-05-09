# jdbc-tracer

Drivers for debugging jdbc applications.

## Add as dependency

```xml
<dependency>
    <groupId>io.github.giosil</groupId>
    <artifactId>jdbc-tracer</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Examples

### Start trace from application

```java
public static
Connection getConnection()
  throws Exception
{
  Context ctx = new InitialContext();

  DataSource ds = (DataSource) ctx.lookup("java:/jdbc/db_test");

  Connection conn = ds.getConnection();
  
  return TracerFactory.trace(conn);
}
```

### Analysis tool

*JDBCLogAnalyzer* shows Connection, PreparedStatement, Statement, ResultSet not closed and Exceptions.

```
java -cp ./target/jdbc-tracer-1.0.0.jar org.dew.jdbc.util.JDBCLogAnalyzer hsqldb_trace.sql
```

Programmatically:

```java
List<Integer> rows = JDBCLogAnalyzer.analyze("hsqldb_trace.sql");
```

### Oracle

```
oracle.jdbc.driver.OracleDriver
jdbc:oracle:thin:@localhost:1521:orcl

        becomes

org.dew.jdbc.drivers.OracleDriverTracer
jdbc:woracle:thin:@localhost:1521:orcl
```

**Log file:** $HOME/oracle_trace.sql

### MySQL

```
com.mysql.jdbc.Driver
jdbc:mysql://localhost/database

        becomes

org.dew.jdbc.drivers.MySQLDriverTracer
jdbc:wmysql://localhost/database
```

**Log file:** $HOME/mysql_trace.sql

### MariaDB

```
org.mariadb.jdbc.Driver
jdbc:mariadb://localhost/database

        becomes

org.dew.jdbc.drivers.MariaDBTracer
jdbc:wmariadb://localhost/database
```

**Log file:** $HOME/mariadb_trace.sql

### PostgreSQL

```
org.postgresql.Driver
jdbc:postgresql://host/database

        becomes

org.dew.jdbc.drivers.PostgreSQLDriverTracer
jdbc:wpostgresql://host/database
```

**Log file:** $HOME/postgresql_trace.sql

### HSQLDB

```
org.hsqldb.jdbcDriver
jdbc:hsqldb:res:test

        becomes

org.dew.jdbc.drivers.HSQLDBDriverTracer
jdbc:whsqldb:res:test
```

**Log file:** $HOME/hsqldb_trace.sql

### H2

```
org.h2.Driver
jdbc:h2:mem:test

        becomes

org.dew.jdbc.drivers.H2DriverTracer
jdbc:wh2:mem:test
```

**Log file:** $HOME/h2_trace.sql

### SQLServer

```
com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc:sqlserver://host;database=test

        becomes

org.dew.jdbc.drivers.SQLServerTracer
jdbc:wsqlserver://host;database=test
```

**Log file:** $HOME/sqls_trace.sql

### ODBC

```
sun.jdbc.odbc.JdbcOdbcDriver
jdbc:odbc:test

        becomes

org.dew.jdbc.drivers.ODBCDriverTracer
jdbc:wodbc:test
```

**Log file:** $HOME/odbc_trace.sql

## Build

- `git clone https://github.com/giosil/jdbc-tracer.git`
- `mvn clean install`

or 

- `mvn -f .\pom-giosil.xml clean install -DcreateChecksum=true`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
