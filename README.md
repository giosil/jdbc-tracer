# jdbc-tracer

Drivers for debugging jdbc applications.

## Examples

### Oracle

```
oracle.jdbc.driver.OracleDriver
jdbc:oracle:thin:@localhost:1521:orcl

        becomes

org.dew.jdbc.drivers OracleDriverTracer
jdbc:woracle:thin:@localhost:1521:orcl
```

### MySQL
```
com.mysql.jdbc.Driver
jdbc:mysql://localhost/database

        becomes

org.dew.jdbc.drivers MySQLDriverTracer
jdbc:wmysql://localhost/database
```

### MariaDB
```
org.mariadb.jdbc.Driver
jdbc:mariadb://localhost/database

        becomes

org.dew.jdbc.drivers MariaDBTracer
jdbc:wmariadb://localhost/database
```

### PostgreSQL
```
org.postgresql.Driver
jdbc:postgresql://host/database

        becomes

org.dew.jdbc.drivers PostgreSQLDriverTracer
jdbc:wpostgresql://host/database
```

### HSQLDB
```
org.hsqldb.jdbcDriver
jdbc:hsqldb:res:test

        becomes

org.dew.jdbc.drivers.HSQLDBDriverTracer
jdbc:whsqldb:res:test
```

### H2
```
org.h2.Driver
jdbc:h2:mem:test

        becomes

org.dew.jdbc.drivers.H2DriverTracer
jdbc:wh2:mem:test
```

### SQLServer
```
com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc:sqlserver://host;database=test

        becomes

org.dew.jdbc.drivers.SQLServerTracer
jdbc:wsqlserver://host;database=test
```

### ODBC
```
sun.jdbc.odbc.JdbcOdbcDriver
jdbc:odbc:test

        becomes

org.dew.jdbc.drivers.ODBCDriverTracer
jdbc:wodbc:test
```

## Build

- `git clone https://github.com/giosil/jdbc-tracer.git`
- `mvn clean install`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
