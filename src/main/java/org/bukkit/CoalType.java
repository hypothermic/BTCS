package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;




public enum CoalType
{
  COAL(0), 
  CHARCOAL(1);
  
  private final byte data;
  private static final Map<Byte, CoalType> BY_DATA;
  
  private CoalType(int data) {
    this.data = ((byte)data);
  }
  




  public byte getData()
  {
    return this.data;
  }
  







  public static CoalType getByData(byte data)
  {
    return (CoalType)BY_DATA.get(Byte.valueOf(data));
  }
  
  static
  {
    BY_DATA = Maps.newHashMap();
    


























    for (CoalType type : values()) {
      BY_DATA.put(Byte.valueOf(type.data), type);
    }
  }
}
