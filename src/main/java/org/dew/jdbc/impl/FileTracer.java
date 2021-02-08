package org.dew.jdbc.impl;

import org.dew.jdbc.Tracer;
import org.dew.jdbc.TracerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public 
class FileTracer implements Tracer 
{
  protected PrintStream ps;
  protected String comment = "-- ";
  
  public 
  FileTracer(String fileName) 
    throws Exception 
  {
    if(fileName == null || fileName.length() == 0) {
      fileName = "trace.sql";
    }
    
    ps = new PrintStream(new FileOutputStream(fileName, true));
  }
  
  public 
  FileTracer(String fileName, String comment) 
    throws Exception 
  {
    this(fileName);
    
    if(comment == null) comment = "";
    this.comment = comment;
  }
  
  public 
  FileTracer(File file) 
    throws Exception 
  {
    if(file == null) file = new File("trace.sql");
    
    ps = new PrintStream(new FileOutputStream(file, true));
  }
  
  public 
  FileTracer(File file, String comment) 
    throws Exception 
  {
    this(file);
    
    if(comment == null) comment = "";
    this.comment = comment;
  }
  
  public 
  FileTracer(OutputStream os) 
    throws Exception 
  {
    ps = new PrintStream(os);
  }
  
  public 
  FileTracer(OutputStream os, String comment) 
    throws Exception 
  {
    this(os);
    
    if(comment == null) comment = "";
    this.comment = comment;
  }
  
  public 
  FileTracer(PrintStream printStream) 
    throws Exception 
  {
    ps = printStream;
  }
  
  public 
  FileTracer(PrintStream printStream, String comment) 
    throws Exception 
  {
    ps = printStream;
    
    if(comment == null) comment = "";
    this.comment = comment;
  }
  
  @Override
  public void info(String text) {
    if(!TracerFactory.INFO || !TracerFactory.ENABLED) return;
    
    ps.println(comment + text);
    ps.flush();
  }
  
  @Override
  public void debug(String text) {
    if(!TracerFactory.DEBUG || !TracerFactory.ENABLED) return;
    
    ps.println(text);
    ps.flush();
  }
  
  @Override
  public void error(Throwable throwable) {
    if(!TracerFactory.ERROR || !TracerFactory.ENABLED) return;
    
    String message = comment + "Exception";
    if (throwable != null) {
      message += ": " + throwable.getMessage();
    }
    ps.println(message);
    ps.flush();
  }
  
  public 
  void finalize() 
  {
    try {
      if(ps  != null) ps.close();
    } 
    catch (Exception ex) {
    }
  }
}