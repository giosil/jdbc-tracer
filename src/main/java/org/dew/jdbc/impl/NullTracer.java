package org.dew.jdbc.impl;

import org.dew.jdbc.Tracer;

public 
class NullTracer implements Tracer 
{
  public NullTracer() {
  }
  
  @Override
  public void info(String text) {
  }
  
  @Override
  public void debug(String text) {
  }
  
  @Override
  public void error(Throwable throwable) {
  }
}