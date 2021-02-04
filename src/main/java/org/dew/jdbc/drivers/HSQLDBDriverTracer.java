package org.dew.jdbc.drivers;

import org.dew.jdbc.*;

import java.util.Properties;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.DriverPropertyInfo;
import java.sql.Driver;
import java.sql.DriverManager;

public 
class HSQLDBDriverTracer implements Driver 
{
  protected static int iCount = 0;
  protected static final String sPREFIX   = "jdbc:whsqldb:";

  protected static Driver m_defaultDriver;

  static {
    try {
      m_defaultDriver = (Driver) Class.forName("org.hsqldb.jdbcDriver").newInstance();
      
      DriverManager.registerDriver(new HSQLDBDriverTracer());
    } 
    catch (Exception ex) {
      System.err.println("[HSQLDBDriverTracer] init: " + ex);
    }
  }

  public Connection connect(String sURL, Properties oInfo) throws java.sql.SQLException {
    Tracer tracer = TracerFactory.getTracer("hsqldb_trace.sql");
    tracer.traceRem("[HSQLDBDriverTracer.connect URL = " + sURL + ", oInfo = " + oInfo + "]");
    Connection conn = null;
    String sTag = null;
    try {
      String sRealURL = getRealURL(sURL);
      conn = m_defaultDriver.connect(sRealURL, oInfo);
      iCount++;
      sTag = "C" + iCount;
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
    return new TConnection(conn, sTag, tracer, "hsqldb");
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

  protected static String getRealURL(String sURL) {
    return "jdbc:hsqldb:" + sURL.substring(sPREFIX.length());
  }

  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return m_defaultDriver.getParentLogger();
  }
}