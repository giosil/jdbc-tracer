package org.dew.jdbc.util;

import java.io.*;
import java.util.*;

public
class JDBCLogAnalyzer
{
  public static 
  void main (String[] args)
  {
    if(args == null || args.length == 0) {
      System.out.println("Usage: JDBCLogAnalyzer file_trace");
      System.exit(1);
    }
    
    String fileName = args[0];
    if(fileName == null || fileName.length() == 0) {
      System.out.println("Invalid file trace");
      System.exit(1);
    }
    
    // Check Relative Path
    int iSep = fileName.indexOf('/');
    if(iSep < 0) iSep = fileName.indexOf('\\');
    if(iSep < 0) {
      fileName = System.getProperty("user.home") + File.separator + fileName;
    }
    
    File file = new File(fileName);
    if(!file.exists()) {
      System.out.println ("File " + file + " not found.");
      return;
    }
    
    System.out.println ("Read " + file + " ...");
    
    analyze(file);
    
    System.out.println ("End.");
  }
  
  public static 
  List<Integer> analyze(String fileName)
  {
    if(fileName == null || fileName.length() == 0) {
      return new ArrayList<Integer>();
    }
    
    // Check Relative Path
    int iSep = fileName.indexOf('/');
    if(iSep < 0) iSep = fileName.indexOf('\\');
    if(iSep < 0) {
      fileName = System.getProperty("user.home") + File.separator + fileName;
    }
    
    return analyze(new File(fileName));
  }
  
  public static 
  List<Integer> analyze(File file)
  {
    List<Integer> listResult = new ArrayList<Integer>();
    
    if(file == null) return listResult;
    
    if(!file.exists()) return listResult;
    
    Map<String,Integer> mapRisorse = new HashMap<String,Integer>();
    
    FileReader fr = null;
    try {
      fr = new FileReader(file);
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
        else if(sLine.startsWith("-- Exception:")) {
          String sResName = sLine.substring(13).trim();
          mapRisorse.put(sResName, new Integer(iRiga));
        }
      }
      fr.close();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    finally {
      if(fr != null) try { fr.close(); } catch(Exception ex) {}
    }
    
    Iterator<Map.Entry<String, Integer>> iterator = mapRisorse.entrySet().iterator();
    while(iterator.hasNext()) {
      Map.Entry<String, Integer> entry = iterator.next();
      String  res = entry.getKey();
      Integer row = entry.getValue();
      listResult.add(row);
      System.out.println ("Row: " + row + " - " + res);
    }
    
    return listResult;
  }
}
