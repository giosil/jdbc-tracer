package org.dew.jdbc;

import java.io.PrintWriter;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import org.dew.jdbc.drivers.ConnectionFactory;

public 
class TDataSource implements XADataSource, DataSource, ConnectionPoolDataSource, Serializable, Referenceable
{
  private static final long serialVersionUID = -5894636772842344062L;
  
  protected transient PrintWriter logWriter;

  protected int loginTimeout = 0;
  protected String userName = "";
  protected char[] passwordChars = new char[0];
  protected String url = "";
  protected String description = "";
  
  public TDataSource()
  {
  }
  
  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return logWriter;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    this.logWriter = out;
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException("Unsupported TDataSource.unwrap");
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new SQLException("Unsupported TDataSource.unwrap");
  }

  @Override
  public Reference getReference() throws NamingException {
    String factoryClassName = TObjectFactory.class.getName();
    Reference reference = new Reference(getClass().getName(), factoryClassName, null);
    reference.add(new StringRefAddr("url",  this.url));
    reference.add(new StringRefAddr("user", this.userName));
    reference.add(new StringRefAddr("password", convertToString(this.passwordChars)));
    reference.add(new StringRefAddr("loginTimeout", String.valueOf(this.loginTimeout)));
    return reference;
  }

  @Override
  public PooledConnection getPooledConnection() throws SQLException {
    return new TXAConnection(ConnectionFactory.getConnection(this.url, this.userName, convertToString(this.passwordChars)));
  }

  @Override
  public PooledConnection getPooledConnection(String username, String password) throws SQLException {
    return new TXAConnection(ConnectionFactory.getConnection(this.url, username, password));
  }

  @Override
  public Connection getConnection() throws SQLException {
    return ConnectionFactory.getConnection(this.url, this.userName, convertToString(this.passwordChars));
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return ConnectionFactory.getConnection(this.url, username, password);
  }

  @Override
  public XAConnection getXAConnection() throws SQLException {
    return new TXAConnection(ConnectionFactory.getConnection(this.url, this.userName, convertToString(this.passwordChars)));
  }

  @Override
  public XAConnection getXAConnection(String username, String password) throws SQLException {
    return new TXAConnection(ConnectionFactory.getConnection(this.url, username, password));
  }
  
  public String getURL() {
    return this.url;
  }
  
  public void setURL(String url) {
    this.url = url;
  }
  
  public void setPassword(String password) {
    this.passwordChars = convertToCharArray(password);
  }
  
  public String getPassword() {
    return convertToString(this.passwordChars);
  }
  
  public String getUser() {
    return this.userName;
  }
  
  public void setUser(String user) {
    this.userName = user;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  protected static char[] convertToCharArray(String s) {
    return (s == null) ? null : s.toCharArray();
  }
  
  protected static String convertToString(char[] a) {
    return (a == null) ? null : new String(a);
  }
}
