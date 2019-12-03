package org.dew.jdbc.util;

import java.io.*;
import java.util.*;

public
class JDBCLogAnalyzer
{
  public
  static void main (String[] args)
  {
    if(args == null || args.length == 0) {
      System.out.println("Usage: JDBCLogAnalyzer file_trace");
      System.exit(1);
    }
    
    String sFileTrace = args[0];
    if(sFileTrace == null || sFileTrace.length() == 0) {
      System.out.println("Invalid file trace");
      System.exit(1);
    }
    
    // Check Relative Path
    int iSep = sFileTrace.indexOf('/');
    if(iSep < 0) iSep = sFileTrace.indexOf('\\');
    if(iSep < 0) {
      sFileTrace = System.getProperty("user.home") + File.separator + sFileTrace;
    }
    
    Map<String,Integer> mapRisorse = new HashMap<String,Integer>();
    
    System.out.println ("Read " + sFileTrace + " ...");
    try {
      FileReader fr = new FileReader(sFileTrace);
      BufferedReader br = new BufferedReader(fr);
      String sLine = null;
      int iRiga = 0;
      while((sLine = br.readLine()) != null) {
        iRiga++;
        if(sLine.startsWith("-- [")) {
          int i = sLine.indexOf("created");
          if(i > 0) {
            String sResName = sLine.substring(4, i - 1);
            mapRisorse.put(sResName, new Integer(iRiga));
          }
          else {
            i = sLine.indexOf("opened");
            if(i > 0) {
              String sResName = sLine.substring(15, i - 1);
              mapRisorse.put(sResName, new Integer(iRiga));
            }
          }
          i = sLine.indexOf(".close");
          if(i > 0) {
            String sResName = sLine.substring(4, i);
            mapRisorse.remove(sResName);
          }
        }
      }
      fr.close();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    
    Iterator<Map.Entry<String, Integer>> iterator = mapRisorse.entrySet().iterator();
    while(iterator.hasNext()) {
      Map.Entry<String, Integer> entry = iterator.next();
      String  res = entry.getKey();
      Integer row = entry.getValue();
      System.out.println ("Row: " + row + " - Resources: " + res);
    }
    
    System.out.println ("End.");
  }
}
