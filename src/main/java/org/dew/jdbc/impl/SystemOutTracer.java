package org.dew.jdbc.impl;

import org.dew.jdbc.Tracer;
import org.dew.jdbc.TracerFactory;

public 
class SystemOutTracer implements Tracer 
{
  protected String comment = "-- ";

  public SystemOutTracer() {
  }
  
  public SystemOutTracer(String comment) {
    if(comment == null) comment = "";
    this.comment = comment;
  }
  
  @Override
  public void info(String text) {
    if(!TracerFactory.INFO || !TracerFactory.ENABLED) return;
    
    System.out.println(comment + text);
  }
  
  @Override
  public void debug(String text) {
    if(!TracerFactory.DEBUG || !TracerFactory.ENABLED) return;
    
    System.out.println(text);
  }
  
  @Override
  public void error(Throwable throwable) {
    if(!TracerFactory.ERROR || !TracerFactory.ENABLED) return;
    
    String message = comment + "Exception";
    if (throwable != null) {
      message += ": " + throwable.getMessage();
    }
    System.err.println(message);
  }
}
