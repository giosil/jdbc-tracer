package org.dew.jdbc.impl;

import org.dew.jdbc.Tracer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public 
class FileTracer implements Tracer 
{
  protected PrintWriter  printWriter;
  protected OutputStream outputStream;
  protected String sCommentTag = "-- ";
  
  public 
  FileTracer(String sFileName) 
    throws Exception 
  {
    outputStream = new FileOutputStream(sFileName, true);
    printWriter  = new PrintWriter(outputStream);
  }
  
  public 
  FileTracer(String sFileName, String sCommentTag) 
    throws Exception 
  {
    this(sFileName);
    this.sCommentTag = sCommentTag;
  }
  
  public 
  FileTracer(File file) 
    throws Exception 
  {
    outputStream = new FileOutputStream(file, true);
    printWriter  = new PrintWriter(outputStream);
  }
  
  public 
  FileTracer(File file, String sCommentTag) 
    throws Exception 
  {
    this(file);
    this.sCommentTag = sCommentTag;
  }
  
  public 
  FileTracer(OutputStream os) 
    throws Exception 
  {
    printWriter  = new PrintWriter(os);
  }
  
  public 
  FileTracer(OutputStream os, String sCommentTag) 
    throws Exception 
  {
    this(os);
    this.sCommentTag = sCommentTag;
  }
  
  @Override
  public void traceRem(String sText) {
    if (sCommentTag == null)  return;
    printWriter.println(sCommentTag + sText);
    printWriter.flush();
  }
  
  @Override
  public void trace(String sText) {
    printWriter.println(sText);
    printWriter.flush();
  }
  
  @Override
  public void traceException(Throwable throwable) {
    String sMessage = null;
    if (sCommentTag != null) {
      sMessage = sCommentTag + "Exception: ";
      sMessage += throwable.getMessage();
    } 
    else {
      sMessage = "[Exception: ";
      sMessage += throwable.getMessage() + "]";
    }
    printWriter.println(sMessage);
    printWriter.flush();
  }

  public void finalize() {
    try {
      if(outputStream  != null) outputStream.close();
    } 
    catch (Exception ex) {
    }
    try {
      if(printWriter  != null) printWriter.close();
    } 
    catch (Exception ex) {
    }
  }
}