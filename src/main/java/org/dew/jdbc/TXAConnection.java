package org.dew.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public 
class TXAConnection implements XAConnection, XAResource 
{
  protected Connection conn;
  
  public TXAConnection()
  {
  }

  public TXAConnection(Connection conn)
  {
    this.conn = conn;
  }
  
  @Override
  public Connection getConnection() throws SQLException {
    return conn;
  }

  @Override
  public void close() throws SQLException {
    if(conn != null) conn.close();
  }

  @Override
  public void addConnectionEventListener(ConnectionEventListener listener) {
  }

  @Override
  public void removeConnectionEventListener(ConnectionEventListener listener) {
  }

  @Override
  public void addStatementEventListener(StatementEventListener listener) {
  }

  @Override
  public void removeStatementEventListener(StatementEventListener listener) {
  }

  @Override
  public void commit(Xid xid, boolean onePhase) throws XAException {
    if(conn == null) return;
    try {
      conn.commit();
    }
    catch(Exception ex) {
      throw new XAException(ex.toString());
    }
  }

  @Override
  public void end(Xid xid, int flags) throws XAException {
  }

  @Override
  public void forget(Xid xid) throws XAException {
  }

  @Override
  public int getTransactionTimeout() throws XAException {
    return 0;
  }

  @Override
  public boolean isSameRM(XAResource xaResource) throws XAException {
    return (xaResource == this);
  }

  @Override
  public int prepare(Xid xid) throws XAException {
    return 0;
  }

  @Override
  public Xid[] recover(int flag) throws XAException {
    return new Xid[0];
  }

  @Override
  public void rollback(Xid xid) throws XAException {
    if(conn == null) return;
    try {
      conn.rollback();
    }
    catch(Exception ex) {
      throw new XAException(ex.toString());
    }
  }

  @Override
  public boolean setTransactionTimeout(int seconds) throws XAException {
    return false;
  }

  @Override
  public void start(Xid xid, int flags) throws XAException {
  }

  @Override
  public XAResource getXAResource() throws SQLException {
    return this;
  }
  
}
