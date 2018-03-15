package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;









public enum GameMode
{
  CREATIVE(1), 
  



  SURVIVAL(0);
  
  private final int value;
  private static final Map<Integer, GameMode> BY_ID;
  
  private GameMode(int value) {
    this.value = value;
  }
  




  public int getValue()
  {
    return this.value;
  }
  





  public static GameMode getByValue(int value)
  {
    return (GameMode)BY_ID.get(Integer.valueOf(value));
  }
  
  static
  {
    BY_ID = Maps.newHashMap();
    
























    for (GameMode mode : values()) {
      BY_ID.put(Integer.valueOf(mode.getValue()), mode);
    }
  }
}
