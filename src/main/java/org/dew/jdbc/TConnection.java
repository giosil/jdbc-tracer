package org.dew.jdbc;

import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.sql.Connection;

public class TConnection implements Connection {

  private static int iCount = 0;

  protected Connection conn;
  protected String sTag = null;
  protected Tracer tracer;
  protected String sDBMS;

  public TConnection(Connection conn, String sTag, Tracer tracer, String sDBMS) {
    this.conn = conn;
    this.sTag = sTag;
    this.tracer = tracer;
    this.sDBMS = sDBMS;
  }

  public void clearWarnings() throws java.sql.SQLException {
    conn.clearWarnings();
  }

  public void close() throws java.sql.SQLException {
    tracer.traceRem("[" + sTag + ".close()]");
    try {
      conn.close();
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public void commit() throws java.sql.SQLException {
    tracer.traceRem("[" + sTag + ".commit()]");
    tracer.trace("commit;");
    try {
      conn.commit();
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public Statement createStatement() throws java.sql.SQLException {
    try {
      Statement stm = conn.createStatement();
      iCount++;
      String sStmTag = getStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".createStatement()]");
      return new TStatement(stm, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
    try {
      Statement stm = conn.createStatement(resultSetType, resultSetConcurrency);
      iCount++;
      String sStmTag = getStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".createStatement(" + resultSetType + ","
          + resultSetConcurrency + ")]");
      return new TStatement(stm, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
      throws SQLException {
    try {
      Statement stm = conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
      iCount++;
      String sStmTag = getStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".createStatement(" + resultSetType + ","
          + resultSetConcurrency + "," + resultSetHoldability + ")]");
      return new TStatement(stm, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public boolean getAutoCommit() throws java.sql.SQLException {
    return conn.getAutoCommit();
  }

  public String getCatalog() throws java.sql.SQLException {
    return conn.getCatalog();
  }

  public int getHoldability() throws SQLException {
    return conn.getHoldability();
  }

  public DatabaseMetaData getMetaData() throws java.sql.SQLException {
    return conn.getMetaData();
  }

  public int getTransactionIsolation() throws java.sql.SQLException {
    return conn.getTransactionIsolation();
  }

  public Map<String, Class<?>> getTypeMap() throws java.sql.SQLException {
    return conn.getTypeMap();
  }

  public SQLWarning getWarnings() throws java.sql.SQLException {
    return conn.getWarnings();
  }

  public boolean isClosed() throws java.sql.SQLException {
    return conn.isClosed();
  }

  public boolean isReadOnly() throws java.sql.SQLException {
    return conn.isReadOnly();
  }

  public String nativeSQL(String sSQL) throws java.sql.SQLException {
    return conn.nativeSQL(sSQL);
  }

  public CallableStatement prepareCall(String sSQL) throws java.sql.SQLException {
    return conn.prepareCall(sSQL);
  }

  public CallableStatement prepareCall(String sSQL, int iRsType, int iRsConcurrency) throws java.sql.SQLException {
    return conn.prepareCall(sSQL, iRsType, iRsConcurrency);
  }

  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  public PreparedStatement prepareStatement(String sSQL) throws java.sql.SQLException {
    try {
      PreparedStatement pstm = conn.prepareStatement(sSQL);
      iCount++;
      String sStmTag = getPreparedStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".prepareStatement(\"" + sSQL + "\")]");
      return new TPreparedStatement(pstm, sSQL, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public PreparedStatement prepareStatement(String sSQL, int autoGeneratedKeys) throws SQLException {
    try {
      PreparedStatement pstm = conn.prepareStatement(sSQL, autoGeneratedKeys);
      iCount++;
      String sStmTag = getPreparedStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".prepareStatement(\"" + sSQL + "\","
          + autoGeneratedKeys + ")]");
      return new TPreparedStatement(pstm, sSQL, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public PreparedStatement prepareStatement(String sSQL, int iRsType, int iRsConcurrency)
      throws java.sql.SQLException {
    try {
      PreparedStatement pstm = conn.prepareStatement(sSQL, iRsType, iRsConcurrency);
      iCount++;
      String sStmTag = getPreparedStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".prepareStatement(\"" + sSQL + "\"," + iRsType
          + "," + iRsConcurrency + ")]");
      return new TPreparedStatement(pstm, sSQL, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public PreparedStatement prepareStatement(String sSQL, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    try {
      PreparedStatement pstm = conn.prepareStatement(sSQL, resultSetType, resultSetConcurrency,
          resultSetHoldability);
      iCount++;
      String sStmTag = getPreparedStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".prepareStatement(\"" + sSQL + "\","
          + resultSetType + "," + resultSetConcurrency + "," + resultSetHoldability + ")]");
      return new TPreparedStatement(pstm, sSQL, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public PreparedStatement prepareStatement(String sSQL, int[] columnIndexes) throws SQLException {
    try {
      PreparedStatement pstm = conn.prepareStatement(sSQL, columnIndexes);
      iCount++;
      String sStmTag = getPreparedStatementTag();
      tracer.traceRem("[" + sStmTag + " created by " + sTag + ".prepareStatement(\"" + sSQL + "\","
          + columnIndexes + ")]");
      return new TPreparedStatement(pstm, sSQL, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public PreparedStatement prepareStatement(String sSQL, String[] columnNames) throws SQLException {
    try {
      PreparedStatement pstm = conn.prepareStatement(sSQL, columnNames);
      iCount++;
      String sStmTag = getPreparedStatementTag();
      tracer.traceRem(
          "[" + sStmTag + " created by " + sTag + ".prepareStatement(\"" + sSQL + "\"," + columnNames + ")]");
      return new TPreparedStatement(pstm, sSQL, sStmTag, tracer, sDBMS);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    conn.releaseSavepoint(savepoint);
  }

  public void rollback() throws java.sql.SQLException {
    tracer.traceRem("[" + sTag + ".rollback()]");
    tracer.trace("rollback;");
    try {
      conn.rollback();
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public void rollback(Savepoint savepoint) throws SQLException {
    conn.rollback(savepoint);
  }

  public void setAutoCommit(boolean bAutoCommit) throws java.sql.SQLException {
    tracer.traceRem("[" + sTag + ".setAutoCommit(" + bAutoCommit + ")]");
    try {
      conn.setAutoCommit(bAutoCommit);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public void setCatalog(String sCatalog) throws java.sql.SQLException {
    conn.setCatalog(sCatalog);
  }

  public void setHoldability(int holdability) throws SQLException {
    conn.setHoldability(holdability);
  }

  public void setReadOnly(boolean bReadOnly) throws java.sql.SQLException {
    tracer.traceRem("[" + sTag + ".setReadOnly(" + bReadOnly + ")]");
    try {
      conn.setReadOnly(bReadOnly);
    } catch (SQLException ex) {
      tracer.traceException(ex);
      throw ex;
    }
  }

  public Savepoint setSavepoint() throws SQLException {
    return conn.setSavepoint();
  }

  public Savepoint setSavepoint(String name) throws SQLException {
    return conn.setSavepoint(name);
  }

  public void setTransactionIsolation(int iTransIsolation) throws java.sql.SQLException {
    conn.setTransactionIsolation(iTransIsolation);
  }

  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    conn.setTypeMap(map);
  }

  private String getPreparedStatementTag() {
    return "PS" + iCount + "@" + sTag;
  }

  private String getStatementTag() {
    return "S" + iCount + "@" + sTag;
  }

  public <T> T unwrap(Class<T> iface) throws SQLException {
    return conn.unwrap(iface);
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return conn.isWrapperFor(iface);
  }

  public Clob createClob() throws SQLException {
    return conn.createClob();
  }

  public Blob createBlob() throws SQLException {
    return conn.createBlob();
  }

  public NClob createNClob() throws SQLException {
    return conn.createNClob();
  }

  public SQLXML createSQLXML() throws SQLException {
    return conn.createSQLXML();
  }

  public boolean isValid(int timeout) throws SQLException {
    return conn.isValid(timeout);
  }

  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    conn.setClientInfo(name, value);
  }

  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    conn.setClientInfo(properties);
  }

  public String getClientInfo(String name) throws SQLException {
    return conn.getClientInfo(name);
  }

  public Properties getClientInfo() throws SQLException {
    return conn.getClientInfo();
  }

  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return conn.createArrayOf(typeName, elements);
  }

  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return conn.createStruct(typeName, attributes);
  }

  public void setSchema(String schema) throws SQLException {
    conn.setSchema(schema);
  }

  public String getSchema() throws SQLException {
    return conn.getSchema();
  }

  public void abort(Executor executor) throws SQLException {
    conn.abort(executor);
  }

  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    conn.setNetworkTimeout(executor, milliseconds);
  }

  public int getNetworkTimeout() throws SQLException {
    return conn.getNetworkTimeout();
  }
}