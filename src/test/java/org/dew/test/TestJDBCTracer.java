package org.dew.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.dew.jdbc.util.JDBCLogAnalyzer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestJDBCTracer extends TestCase {
  
  protected static final String DB_NAME = "test";
  
  public TestJDBCTracer(String testName) {
    super(testName);
  }
  
  public static Test suite() {
    return new TestSuite(TestJDBCTracer.class);
  }
  
  public void testApp() throws Exception {
    
    executeQuery();
    
    List<Integer> rows = JDBCLogAnalyzer.analyze("hsqldb_trace.sql");
    
    System.out.println("analyze -> " + rows);
    
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
    
    File fileP = new File(DB_NAME + ".properties");
    if(fileP.exists()) {
      boolean resDelete = fileP.delete();
      String removed = resDelete ? "removed." : "NOT removed.";
      System.out.println(fileP.getAbsolutePath() + " " + removed);
    }
    
    File fileS = new File(DB_NAME + ".script");
    if(fileS.exists()) {
      boolean resDelete = fileS.delete();
      String removed = resDelete ? "removed." : "NOT removed.";
      System.out.println(fileS.getAbsolutePath() + " " + removed);
    }
  }
  
  public static Connection getConnection() throws Exception {
    Class.forName("org.dew.jdbc.drivers.HSQLDBDriverTracer");
    return DriverManager.getConnection("jdbc:dew:mem:" + DB_NAME, "SA", "");
  }
}
