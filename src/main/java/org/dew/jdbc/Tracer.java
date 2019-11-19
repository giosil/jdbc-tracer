package org.dew.jdbc;

public 
interface Tracer 
{
  /**
   * Traccia i commenti.
   * 
   * @param sText Text
   */
  public void traceRem(String sText);

  /**
   * Traccia i comandi sql.
   * 
   * @param sText Text
   */
  public void trace(String sText);
  
  /**
   * Traccia le eccezioni.
   * 
   * @param throwable Oggetto Throwable
   */
  public void traceException(Throwable throwable);
}