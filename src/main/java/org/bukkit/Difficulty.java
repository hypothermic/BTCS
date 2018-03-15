package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;







public enum Difficulty
{
  PEACEFUL(0), 
  



  EASY(1), 
  



  NORMAL(2), 
  



  HARD(3);
  
  private final int value;
  private static final Map<Integer, Difficulty> BY_ID;
  
  private Difficulty(int value) {
    this.value = value;
  }
  




  public int getValue()
  {
    return this.value;
  }
  





  public static Difficulty getByValue(int value)
  {
    return (Difficulty)BY_ID.get(Integer.valueOf(value));
  }
  
  static
  {
    BY_ID = Maps.newHashMap();
    
























    for (Difficulty diff : values()) {
      BY_ID.put(Integer.valueOf(diff.value), diff);
    }
  }
}
