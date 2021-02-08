package org.dew.jdbc.impl;

import java.util.logging.Logger;

import org.dew.jdbc.Tracer;
import org.dew.jdbc.TracerFactory;

public 
class LoggerTracer implements Tracer 
{
  protected String comment = "-- ";
  protected Logger logger;

  public LoggerTracer(Logger logger) {
    this.logger = logger;
  }
  
  public LoggerTracer(Logger logger, String comment) {
    this.logger = logger;
    
    if(comment == null) comment = "";
    this.comment = comment;
  }
  
  @Override
  public void info(String text) {
    if(!TracerFactory.INFO || !TracerFactory.ENABLED) return;
    
    if(logger != null) logger.info(comment + text);
  }
  
  @Override
  public void debug(String text) {
    if(!TracerFactory.DEBUG || !TracerFactory.ENABLED) return;
    
    if(logger != null) logger.fine(text);
  }
  
  @Override
  public void error(Throwable throwable) {
    if(!TracerFactory.ERROR || !TracerFactory.ENABLED) return;
    
    String message = comment + "Exception";
    if (throwable != null) {
      message += ": " + throwable.getMessage();
    }
    if(logger != null) logger.severe(message);
  }
}
