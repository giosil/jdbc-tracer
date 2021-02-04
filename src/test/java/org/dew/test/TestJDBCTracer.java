package org.dew.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestJDBCTracer extends TestCase {
  
  public TestJDBCTracer(String testName) {
    super(testName);
  }
  
  public static Test suite() {
    return new TestSuite(TestJDBCTracer.class);
  }
  
  public void testApp() throws Exception {
    
    executeQuery();
    
  }
  
  public void executeQuery() throws Exception {
    Connection conn = null;
    Statement  stm  = null;
    ResultSet  rs   = null;
    try {
      conn = getConnection();
      stm  = conn.createStatement();
      rs   = stm.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
      while(rs.next()) {
        String s1 = rs.getString(1);
        String s2 = rs.getString(2);
        String s3 = rs.getString(3);
        System.out.println(s1 + ";" + s2 + ";" + s3);
      }
      
      stm.execute("SHUTDOWN COMPACT");
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ignore) {}
      if(stm  != null) try { stm.close();  } catch(Exception ignore) {}
      if(conn != null) try { conn.close(); } catch(Exception ignore) {}
    }
  }
  
  public static Connection getConnection() throws Exception {
    Class.forName("org.dew.jdbc.drivers.HSQLDBDriverTracer");
    return DriverManager.getConnection("jdbc:dew:mem:test", "SA", "");
  }
}
