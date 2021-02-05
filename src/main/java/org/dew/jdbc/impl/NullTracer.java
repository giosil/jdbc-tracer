package org.dew.jdbc.impl;

import org.dew.jdbc.Tracer;

public 
class NullTracer implements Tracer 
{
  public NullTracer() {
  }
  
  @Override
  public void traceRem(String sText) {
  }
  
  @Override
  public void trace(String sText) {
  }
  
  @Override
  public void traceException(Throwable throwable) {
  }
}