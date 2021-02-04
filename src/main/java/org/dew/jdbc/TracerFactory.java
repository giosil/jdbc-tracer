package org.dew.jdbc;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.dew.jdbc.impl.FileTracer;
import org.dew.jdbc.impl.NullTracer;

public 
class TracerFactory 
{
  public static Map<String, Tracer> instances = new HashMap<String, Tracer>();
  
  public static 
  Tracer getTracer(String fileName) 
  {
    Tracer tracer = instances.get(fileName);
    
    if(tracer != null) return tracer;
    
    try {
      tracer = new FileTracer(System.getProperty("user.home") + File.separator + fileName, "-- ");
    } 
    catch (Exception ex) {
      tracer = new NullTracer();
    }
    
    instances.put(fileName, tracer);
    
    return tracer;
  }
}
