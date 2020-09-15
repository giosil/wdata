package org.dew.wdata;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dew.wdata.util.WUtil;

public
class FileRecord implements Serializable
{
  private static final long serialVersionUID = -5993824755476143344L;
  
  public static final int TYPE_FIXED_WIDTH = 0;
  public static final int TYPE_CSV         = 1;
  
  protected String name;
  protected List<FileField> fields = new ArrayList<FileField>();
  protected int type = TYPE_FIXED_WIDTH;
  protected String separator = ";";
  protected String starstWith;
  
  public FileRecord()
  {
    this.name = "RECORD";
  }
  
  public FileRecord(int type)
  {
    this.name = "RECORD";
    this.type = type;
  }
  
  public FileRecord(String name)
  {
    if(name == null || name.length() == 0) {
      name = "RECORD";
    }
    this.name = name;
  }
  
  public FileRecord(String name, int type)
  {
    if(name == null || name.length() == 0) {
      name = "RECORD";
    }
    this.name = name;
    this.type = type;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public List<FileField> getFields() {
    return fields;
  }
  
  public void setFields(List<FileField> fields) {
    this.fields = fields;
  }
  
  public int getType() {
    return type;
  }
  
  public void setType(int type) {
    this.type = type;
  }
  
  public String getSeparator() {
    return separator;
  }
  
  public void setSeparator(String separator) {
    this.separator = separator;
  }
  
  public String getStarstWith() {
    return starstWith;
  }
  
  public void setStarstWith(String starstWith) {
    this.starstWith = starstWith;
  }
  
  public void addField(FileField field) {
    if(field  == null) return;
    if(fields == null) fields = new ArrayList<FileField>();
    fields.add(field);
  }
  
  public void removeField(FileField field) {
    if(field  == null) return;
    if(fields == null) return;
    fields.remove(field);
  }
  
  public boolean check(String record) {
    if(record == null || record.length() == 0) {
      return false;
    }
    if(starstWith != null && starstWith.length() > 0) {
      return record.startsWith(starstWith);
    }
    return true;
  }
  
  public Map<String,Object> parse(String record) {
    if(record == null || record.length() == 0) {
      return new HashMap<String, Object>();
    }
    if(fields == null || fields.size() == 0) {
      return new HashMap<String, Object>();
    }
    Map<String,Object> mapResult = new HashMap<String, Object>(fields.size());
    if(type == TYPE_FIXED_WIDTH) {
      int iRecLen = record.length();
      int iStartIdx = 0;
      for(int i = 0; i < fields.size(); i++) {
        FileField fileField = fields.get(i);
        int iSize   = fileField.getSize();
        int iMinLen = iStartIdx + iSize;
        if(iRecLen <= iStartIdx) break;
        String value = null;
        if(iRecLen >= iMinLen) {
          value = record.substring(iStartIdx, iStartIdx+iSize);
        }
        else {
          value = record.substring(iStartIdx);
        }
        mapResult.put(fileField.getName(), toObject(value, fileField.getType(), fileField.precision));
        iStartIdx += iSize;
      }
    }
    else if(type == TYPE_CSV) {
      if(separator == null || separator.length() == 0) {
        separator = ";";
      }
      List<String> listValues = new ArrayList<String>();
      int iIndexOf = 0;
      int iBegin   = 0;
      iIndexOf     = record.indexOf(separator);
      while(iIndexOf >= 0) {
        listValues.add(record.substring(iBegin, iIndexOf));
        iBegin = iIndexOf + separator.length();
        iIndexOf = record.indexOf(separator, iBegin);
      }
      listValues.add(record.substring(iBegin));
      
      for(int i = 0; i < listValues.size(); i++) {
        if(fields.size() <= i) break;
        String value = listValues.get(i);
        FileField fileField = fields.get(i);
        mapResult.put(fileField.getName(), toObject(value, fileField.getType(), fileField.precision));
      }
    }
    return mapResult;
  }
  
  protected static Object toObject(String value, Class<?> type, int precision) {
    if(type == null || type.equals(String.class)) {
      if(value == null) return "";
      return value.trim();
    }
    else if(type.equals(Double.class)) {
      if(value != null && value.indexOf('.') < 0 && value.indexOf(',') < 0 && precision > 0) {
        if(value.length() > precision) {
          int iSep = value.length() - precision;
          value = value.substring(0, iSep) + "." + value.substring(iSep);
        }
        else if(value.length() <= precision) {
          value = "0." + value;
        }
      }
      return WUtil.toDoubleObj(value, 0.0d);
    }
    return WUtil.toObject(value, type.getName());
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof FileField) {
      String sName = ((FileField) object).getName();
      if(sName == null && name == null) return true;
      return sName != null && sName.equals(name);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if(name == null) return 0;
    return name.hashCode();
  }
  
  @Override
  public String toString() {
    return "FileRecord(" + name + ")";
  }
}
