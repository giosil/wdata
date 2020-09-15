package org.dew.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dew.wdata.FileField;
import org.dew.wdata.FileRecord;
import org.dew.wdata.FileSchema;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public 
class TestWData extends TestCase 
{
  public TestWData(String testName) {
    super(testName);
  }
  
  public static Test suite() {
    return new TestSuite(TestWData.class);
  }
  
  public void testApp() {
    List<Map<String, Object>> listData = null;
    
    System.out.println("Parse data.txt...");
    listData = parseFile("data.txt", getFileSchema(true));
    if(listData == null) {
      System.out.println("listData = null");
    }
    else if(listData.isEmpty()) {
      System.out.println("listData is empty");
    }
    else {
      for(int i = 0; i < listData.size(); i++) {
        System.out.println(listData.get(i));
      }
    }
    
    System.out.println("Parse data.csv...");
    listData = parseFile("data.csv", getFileSchema(false));
    if(listData == null) {
      System.out.println("listData = null");
    }
    else if(listData.isEmpty()) {
      System.out.println("listData is empty");
    }
    else {
      for(int i = 0; i < listData.size(); i++) {
        System.out.println(listData.get(i));
      }
    }
  }
  
  public 
  List<Map<String, Object>> parseFile(String file, FileSchema fileSchema) 
  {
    List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
    
    BufferedReader br = null;
    try {
      int iFileSep = file.indexOf('/');
      if(iFileSep < 0) {
        iFileSep = file.indexOf('\\');
      }
      if(iFileSep < 0) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(file);
        br = new BufferedReader(new InputStreamReader(url.openStream()));
      }
      else {
        br = new BufferedReader(new FileReader(file));
      }
      
      String sLine = null;
      while ((sLine = br.readLine()) != null) {
        if (sLine.trim().length() == 0) continue;
        
        List<FileRecord> listFileRecord = fileSchema.getRecordSchemas();
        if (listFileRecord == null || listFileRecord.size() == 0) {
          break;
        }
        
        for (int i = 0; i < listFileRecord.size(); i++) {
          FileRecord fileRecord = listFileRecord.get(i);
          
          if (fileRecord.check(sLine)) {
            Map<String,Object> mapRecord = fileRecord.parse(sLine);
            if(mapRecord == null || mapRecord.isEmpty()) continue;
            
            listData.add(mapRecord);
          }
        }
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    finally {
      if(br != null) try { br.close(); } catch(Exception ex) {}
    }
    
    return listData;
  }
  
  public 
  FileSchema getFileSchema(boolean fixed)
  {
    FileSchema fileSchema = new FileSchema("test", "File schema of test");
    
    FileRecord record = new FileRecord(fixed ? FileRecord.TYPE_FIXED_WIDTH : FileRecord.TYPE_CSV);
    
    record.addField(new FileField("lastname",  20,    String.class));
    record.addField(new FileField("firstname", 20,    String.class));
    record.addField(new FileField("gender",     1,    String.class));
    record.addField(new FileField("birthdate", 10,    Date.class));
    record.addField(new FileField("married",    1,    Boolean.class));
    record.addField(new FileField("rate",       8, 2, Double.class));
    
    fileSchema.addRecord(record);
    
    return fileSchema;
  }
}
