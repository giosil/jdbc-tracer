package org.dew.jdbc;

import java.io.File;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.dew.jdbc.impl.FileTracer;
import org.dew.jdbc.impl.LoggerTracer;
import org.dew.jdbc.impl.NullTracer;
import org.dew.jdbc.impl.SystemOutTracer;

public 
class TracerFactory 
{
  public static Map<String, Tracer> instances = new HashMap<String, Tracer>();
  
  public final static String TYPE_FILE   = "file";
  public final static String TYPE_NULL   = "null";
  public final static String TYPE_SYSTEM = "system";
  public final static String TYPE_LOGGER = "logger";
  
  public static String DEFAULT_TYPE = TYPE_FILE;
  
  public static boolean ENABLED = true;
  
  public static boolean INFO    = true;
  public static boolean DEBUG   = true;
  public static boolean ERROR   = true;
  
  public static int traceCount  = 0;
  
  public static 
  Tracer getTracer(String fileName) 
  {
    return getTracer(fileName, null);
  }
  
  public static 
  Tracer getTracer(String fileName, String type) 
  {
    Tracer tracer = instances.get(fileName);
    
    if(tracer != null) return tracer;
    
    if(type == null || type.length() == 0 || type.equals("default")) {
      type = DEFAULT_TYPE;
    }
    
    char c0 = 'f';
    if(type != null && type.length() > 0) {
      c0 = type.charAt(0);
    }
    
    String filePath = fileName;
    int sep = fileName.indexOf('/');
    if(sep < 0) sep = fileName.indexOf('\\');
    if(sep < 0) {
      filePath = System.getProperty("user.home") + File.separator + fileName;
    }
    
    try {
      if(c0 == 'f' || c0 == 'F') {
        tracer = new FileTracer(filePath);
      }
      else if(c0 == 'n' || c0 == 'N') {
        tracer = new NullTracer();
      }
      else if(c0 == 's' || c0 == 'S') {
        tracer = new SystemOutTracer();
      }
      else if(c0 == 'l' || c0 == 'L') {
        tracer = new LoggerTracer(Logger.getLogger(TracerFactory.class.getName()));
      }
      else {
        tracer = new FileTracer(filePath);
      }
    } 
    catch (Exception ex) {
      tracer = new NullTracer();
    }
    
    instances.put(fileName, tracer);
    
    return tracer;
  }
  
  public static 
  TConnection trace(Connection conn)
  {
    return trace(conn, null, null);
  }

  public static 
  TConnection trace(Connection conn, String fileName)
  {
    return trace(conn, fileName, null);
  }
  
  public static 
  TConnection trace(Connection conn, String fileName, String type)
  {
    if(conn == null) return null;
    
    if(conn instanceof TConnection) {
      return (TConnection) conn;
    }
    
    String     url  = null;
    String     dbms = null;
    Properties info = null;
    boolean autoCommit = false;
    
    // Retrieve url...
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      if(metadata != null) url = metadata.getURL();
    }
    catch(Exception ex) {
    }
    if(url == null || url.length() == 0) url = "jdbc";
    
    // Retrieve dbms...
    int sep = url.indexOf(':', 5);
    if(sep > 0) {
      dbms = url.substring(5, sep).toLowerCase();
    }
    if(dbms == null || dbms.length() == 0) dbms = "sql";
    
    // Retrieve info...
    try {
      info = conn.getClientInfo();
    }
    catch(Exception ex) {
    }
    if(info == null) info = new Properties();
    
    // Retrieve autocommit...
    try {
      autoCommit = conn.getAutoCommit();
    }
    catch(Exception ex) {
    }
    
    if(fileName == null || fileName.length() == 0) {
      fileName = dbms + "_trace.sql";
    }
    
    Tracer tracer = getTracer(fileName, type);
    
    traceCount++;
    String tag = "C" + traceCount;
    
    String traceInfo = "[Connection " + tag;
    traceInfo += " opened at " + new java.sql.Timestamp(System.currentTimeMillis());
    traceInfo += " URL = " + url;
    traceInfo += ", Info = " + info;
    traceInfo += ", AutoCommit = " + autoCommit;
    traceInfo += "]";
    tracer.info(traceInfo);
    
    return new TConnection(conn, tag, tracer, dbms);
  }
}
