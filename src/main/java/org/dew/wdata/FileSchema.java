package org.dew.wdata;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public
class FileSchema implements Serializable
{
  private static final long serialVersionUID = -863999365166441297L;
  
  protected String name;
  protected String description;
  protected List<FileRecord> recordSchemas = new ArrayList<FileRecord>();
  
  public FileSchema()
  {
  }
  
  public FileSchema(String name)
  {
    this.name = name;
  }
  
  public FileSchema(String name, String description)
  {
    this.name = name;
    this.description = description;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public List<FileRecord> getRecordSchemas() {
    return recordSchemas;
  }
  
  public void setRecordSchemas(List<FileRecord> recordSchemas) {
    this.recordSchemas = recordSchemas;
  }
  
  public void addRecord(FileRecord fileRecord) {
    if(fileRecord == null) return;
    if(recordSchemas == null) recordSchemas = new ArrayList<FileRecord>();
    recordSchemas.add(fileRecord);
  }
  
  public boolean removeRecord(FileRecord fileRecord) {
    if(fileRecord == null) return false;
    if(recordSchemas == null) return false;
    return recordSchemas.remove(fileRecord);
  }
  
  @Override
  public boolean equals(Object object) {
    if(object instanceof FileSchema) {
      String sName = ((FileSchema) object).getName();
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
    return "FileSchema(" + name + ")";
  }
}
