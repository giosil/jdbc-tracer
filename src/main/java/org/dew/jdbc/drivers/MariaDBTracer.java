package org.dew.jdbc.drivers;

import org.dew.jdbc.*;
import org.dew.jdbc.impl.*;

import java.io.File;

import java.util.Properties;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.DriverPropertyInfo;
import java.sql.Driver;
import java.sql.DriverManager;

public 
class MariaDBTracer implements Driver 
{
  private static int iCount = 0;
  private static final String sPREFIX = "jdbc:wmariadb:";
  private static String sDefFileName = System.getProperty("user.home") + File.separator + "mariadb_trace.sql";
  private static Driver m_defaultDriver;

  static {
    try {
      m_defaultDriver = (Driver) Class.forName("org.mariadb.jdbc.Driver").newInstance();;
      DriverManager.registerDriver(new MySQLDriverTracer());
    }
    catch (Exception ex) {
      System.err.println("[MariaDBTracer] init: " + ex);
    }
  }

  public Connection connect(String sURL, Properties oInfo) throws java.sql.SQLException {
    Tracer tracer = getDefaultTracer();
    tracer.traceRem("[MariaDBTracer.connect URL = " + sURL + ", oInfo = " + oInfo + "]");
    Connection conn = null;
    String sTag = null;
    try {
      String sRealURL = getRealURL(sURL);
      conn = m_defaultDriver.connect(sRealURL, oInfo);
      iCount++;
      sTag = getTag(sRealURL);
      String sTextRem = "[Connection " + sTag;
      sTextRem += " opened at " + new java.sql.Timestamp(System.currentTimeMillis());
      sTextRem += " URL = " + sRealURL;
      sTextRem += ", Info = " + oInfo;
      sTextRem += ", AutoCommit = " + conn.getAutoCommit();
      sTextRem += "]";
      tracer.traceRem(sTextRem);
    } 
    catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
    return new TConnection(conn, sTag, tracer, "mariadb");
  }

  public boolean acceptsURL(String sURL) throws java.sql.SQLException {
    if (sURL == null) return false;
    return sURL.startsWith(sPREFIX);
  }

  public DriverPropertyInfo[] getPropertyInfo(String sURL, Properties oInfo) throws java.sql.SQLException {
    return m_defaultDriver.getPropertyInfo(getRealURL(sURL), oInfo);
  }

  public int getMajorVersion() {
    return m_defaultDriver.getMajorVersion();
  }

  public int getMinorVersion() {
    return m_defaultDriver.getMinorVersion();
  }

  public boolean jdbcCompliant() {
    return true;
  }

  private static String getRealURL(String sURL) {
    return "jdbc:mariadb:" + sURL.substring(sPREFIX.length());
  }

  private String getTag(String sUrl) {
    return "C" + iCount;
  }

  private Tracer getDefaultTracer() {
    Tracer tracer = null;
    try {
      tracer = new FileTracer(sDefFileName, "-- ");
    } 
    catch (Exception ex) {
      tracer = new NullTracer();
    }
    return tracer;
  }

  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return m_defaultDriver.getParentLogger();
  }
}
