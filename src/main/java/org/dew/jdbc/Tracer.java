package org.dew.jdbc;

public 
interface Tracer 
{
  /**
   * Info trace.
   * 
   * @param text text to trace
   */
  public void info(String text);
  
  /**
   * Debug trace (SQL Statement).
   * 
   * @param text text to trace
   */
  public void debug(String text);
  
  /**
   * Error trace.
   * 
   * @param throwable Throwable object
   */
  public void error(Throwable throwable);
}