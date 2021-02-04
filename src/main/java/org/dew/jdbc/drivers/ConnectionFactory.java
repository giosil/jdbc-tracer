package org.dew.jdbc.drivers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public 
class ConnectionFactory 
{
  public static 
  Connection getConnection(String url, String username, String password) 
    throws java.sql.SQLException 
  {
    if(url == null || url.length() == 0) {
      throw new SQLException("Invalid jdbc URL");
    }
    
    try {
      if(url.startsWith("jdbc:wh2:")) {
        Class.forName(H2DriverTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
      else if(url.startsWith("jdbc:whsqldb:")) {
        Class.forName(HSQLDBDriverTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
      else if(url.startsWith("jdbc:wmariadb:")) {
        Class.forName(MariaDBTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
      else if(url.startsWith("jdbc:wmysql:")) {
        Class.forName(MySQLDriverTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
      else if(url.startsWith("jdbc:wodbc:")) {
        Class.forName(ODBCDriverTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
      else if(url.startsWith("jdbc:woracle:")) {
        Class.forName(OracleDriverTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
      else if(url.startsWith("jdbc:wpostgresql:")) {
        Class.forName(PostgreSQLDriverTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
      else if(url.startsWith("jdbc:wsqlserver:")) {
        Class.forName(SQLServerTracer.class.getName());
        return DriverManager.getConnection(url, username, password);
      }
    }
    catch(Exception ex) {
      throw new SQLException(ex);
    }
    
    throw new SQLException("Unknow jdbc URL " + url);
  }
}
