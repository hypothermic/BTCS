package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;




public enum SandstoneType
{
  CRACKED(0), 
  GLYPHED(1), 
  SMOOTH(2);
  
  private final byte data;
  private static final Map<Byte, SandstoneType> BY_DATA;
  
  private SandstoneType(int data) {
    this.data = ((byte)data);
  }
  




  public byte getData()
  {
    return this.data;
  }
  







  public static SandstoneType getByData(byte data)
  {
    return (SandstoneType)BY_DATA.get(Byte.valueOf(data));
  }
  
  static
  {
    BY_DATA = Maps.newHashMap();
    


























    for (SandstoneType type : values()) {
      BY_DATA.put(Byte.valueOf(type.data), type);
    }
  }
}
