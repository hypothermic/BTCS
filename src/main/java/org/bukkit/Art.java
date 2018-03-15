package org.bukkit;

import com.google.common.collect.Maps;
import java.util.HashMap;
import org.apache.commons.lang3.Validate;





public enum Art
{
  KEBAB(0, 1, 1), 
  AZTEC(1, 1, 1), 
  ALBAN(2, 1, 1), 
  AZTEC2(3, 1, 1), 
  BOMB(4, 1, 1), 
  PLANT(5, 1, 1), 
  WASTELAND(6, 1, 1), 
  POOL(7, 2, 1), 
  COURBET(8, 2, 1), 
  SEA(9, 2, 1), 
  SUNSET(10, 2, 1), 
  CREEBET(11, 2, 1), 
  WANDERER(12, 1, 2), 
  GRAHAM(13, 1, 2), 
  MATCH(14, 2, 2), 
  BUST(15, 2, 2), 
  STAGE(16, 2, 2), 
  VOID(17, 2, 2), 
  SKULL_AND_ROSES(18, 2, 2), 
  FIGHTERS(19, 4, 2), 
  POINTER(20, 4, 4), 
  PIGSCENE(21, 4, 4), 
  BURNINGSKULL(22, 4, 4), 
  SKELETON(23, 4, 3), 
  DONKEYKONG(24, 4, 3);
  
  private int id;
  private int width;
  
  private Art(int id, int width, int height)
  {
    this.id = id;
    this.width = width;
    this.height = height;
  }
  




  public int getBlockWidth()
  {
    return this.width;
  }
  




  public int getBlockHeight()
  {
    return this.height;
  }
  




  public int getId()
  {
    return this.id;
  }
  





  public static Art getById(int id)
  {
    return (Art)BY_ID.get(Integer.valueOf(id));
  }
  

  private int height;
  
  private static final HashMap<String, Art> BY_NAME;
  
  private static final HashMap<Integer, Art> BY_ID;
  
  public static Art getByName(String name)
  {
    Validate.notNull(name, "Name cannot be null");
    
    return (Art)BY_NAME.get(name.toLowerCase().replaceAll("_", ""));
  }
  
  static
  {
    BY_NAME = Maps.newHashMap();
    BY_ID = Maps.newHashMap();
    


























































    for (Art art : values()) {
      BY_ID.put(Integer.valueOf(art.id), art);
      BY_NAME.put(art.toString().toLowerCase().replaceAll("_", ""), art);
    }
  }
}
