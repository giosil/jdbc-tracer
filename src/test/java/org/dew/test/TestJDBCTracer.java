package org.dew.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
      conn = getConnection();
      pstm = conn.prepareStatement("SELECT ID,NAME,PHONE,EMAIL,BIRTHDATE FROM CONTACTS WHERE ID=?");
      pstm.setInt(1, 1);
      rs = pstm.executeQuery();
      if(rs.next()) {
        int iId         = rs.getInt("ID");
        String sName    = rs.getString("NAME");
        String sPhone   = rs.getString("PHONE");
        Date dBirthDate = rs.getDate("BIRTHDATE");
        System.out.println("ID=" + iId + ", NAME=" + sName + ", PHONE=" + sPhone + ", BIRTHDATE=" + dBirthDate);
      }
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ignore) {}
      if(pstm != null) try { pstm.close(); } catch(Exception ignore) {}
      if(conn != null) try { conn.close(); } catch(Exception ignore) {}
    }
  }
  
  public static Connection getConnection() throws Exception {
    Class.forName("org.dew.jdbc.drivers.HSQLDBDriverTracer");
    return DriverManager.getConnection("jdbc:dew:res:test", "SA", "");
  }
}
