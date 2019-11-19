# jdbc-tracer

Drivers for debugging jdbc applications.

## Examples

### Oracle

```
oracle.jdbc.driver.OracleDriver
jdbc:oracle:thin:@localhost:1521:orcl

        becomes

org.dew.jdbc.drivers OracleDriverTracer
jdbc:dew:thin:@localhost:1521:orcl
```

### MySQL
```
com.mysql.jdbc.Driver
jdbc:mysql://localhost/database

        becomes

org.dew.jdbc.drivers MySQLDriverTracer
jdbc:dew://localhost/database
```

### PostgreSQL
```
org.postgresql.Driver
jdbc:postgresql://host/database

        becomes

org.dew.jdbc.drivers PostgreSQLDriverTracer
jdbc:dew://host/database
```

## Build

- `git clone https://github.com/giosil/jdbc-tracer.git`
- `mvn clean install`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
