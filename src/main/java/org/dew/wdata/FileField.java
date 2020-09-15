package org.dew.wdata;

import java.io.Serializable;

public 
class FileField implements Serializable 
{
  private static final long serialVersionUID = -5571164141226914807L;
  
  protected String name;
  protected String description;
  protected int size;
  protected int precision;
  protected Class<?> type;
  
  public FileField() {
  }
  
  public FileField(String name, int size) {
    this.name = name;
    this.size = size;
  }
  
  public FileField(String name, int size, Class<?> type) {
    this.name = name;
    this.size = size;
    this.type = type;
  }
  
  public FileField(String name, int size, int precision, Class<?> type) {
    this.name = name;
    this.size = size;
    this.precision = precision;
    this.type = type;
  }
  
  public FileField(String name, int size, String description) {
    this.name = name;
    this.size = size;
    this.description = description;
  }
  
  public FileField(String name, int size, String description, Class<?> type) {
    this.name = name;
    this.size = size;
    this.description = description;
    this.type = type;
  }
  
  public String getName() {
    if (name == null)
      return "";
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
  
  public int getSize() {
    return size;
  }
  
  public void setSize(int size) {
    this.size = size;
  }
  
  public int getPrecision() {
    return precision;
  }
  
  public void setPrecision(int precision) {
    this.precision = precision;
  }
  
  public Class<?> getType() {
    return type;
  }
  
  public void setType(Class<?> type) {
    this.type = type;
  }
  
  @Override
  public boolean equals(Object object) {
    if (object instanceof FileField) {
      String sName = ((FileField) object).getName();
      if (sName == null && name == null)
        return true;
      return sName != null && sName.equals(name);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    if (name == null)
      return 0;
    return name.hashCode();
  }
  
  @Override
  public String toString() {
    return "FileField(" + name + ")";
  }
}
