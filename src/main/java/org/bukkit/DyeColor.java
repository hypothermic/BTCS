package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;








public enum DyeColor
{
  WHITE(0), 
  


  ORANGE(1), 
  


  MAGENTA(2), 
  


  LIGHT_BLUE(3), 
  


  YELLOW(4), 
  


  LIME(5), 
  


  PINK(6), 
  


  GRAY(7), 
  


  SILVER(8), 
  


  CYAN(9), 
  


  PURPLE(10), 
  


  BLUE(11), 
  


  BROWN(12), 
  


  GREEN(13), 
  


  RED(14), 
  


  BLACK(15);
  
  private final byte data;
  private static final Map<Byte, DyeColor> BY_DATA;
  
  private DyeColor(int data) {
    this.data = ((byte)data);
  }
  




  public byte getData()
  {
    return this.data;
  }
  





  public static DyeColor getByData(byte data)
  {
    return (DyeColor)BY_DATA.get(Byte.valueOf(data));
  }
  
  static
  {
    BY_DATA = Maps.newHashMap();
    
























    for (DyeColor color : values()) {
      BY_DATA.put(Byte.valueOf(color.getData()), color);
    }
  }
}
