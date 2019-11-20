package org.dew.jdbc.impl;

import org.dew.jdbc.Tracer;

public class SystemOutTracer implements Tracer {
  protected String sCommentTag = "-- ";

  public SystemOutTracer() {
  }
  
  public SystemOutTracer(String sCommentTag) {
    this.sCommentTag = sCommentTag;
  }
  
  @Override
  public void traceRem(String sText) {
    System.out.println(sCommentTag + sText);
  }
  
  @Override
  public void trace(String sText) {
    System.out.println(sText);
  }
  
  @Override
  public void traceException(Throwable throwable) {
    String sMessage = null;
    if (sCommentTag != null) {
      sMessage = sCommentTag + "Exception: ";
      sMessage += throwable.getMessage();
    } else {
      sMessage = "[Exception: ";
      sMessage += throwable.getMessage() + "]";
    }
    System.err.println(sMessage);
  }
}
