package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;



public enum WorldType
{
  NORMAL("DEFAULT"), 
  FLAT("FLAT"), 
  VERSION_1_1("DEFAULT_1_1");
  
  private static final Map<String, WorldType> BY_NAME;
  private final String name;
  
  private WorldType(String name) {
    this.name = name;
  }
  




  public String getName()
  {
    return this.name;
  }
  





  public static WorldType getByName(String name)
  {
    return (WorldType)BY_NAME.get(name.toUpperCase());
  }
  
  static
  {
    BY_NAME = Maps.newHashMap();
    

























    for (WorldType type : values()) {
      BY_NAME.put(type.name, type);
    }
  }
}
