package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.Potion;








public enum Effect
{
  CLICK2(1000, Type.SOUND), 
  


  CLICK1(1001, Type.SOUND), 
  


  BOW_FIRE(1002, Type.SOUND), 
  


  DOOR_TOGGLE(1003, Type.SOUND), 
  


  EXTINGUISH(1004, Type.SOUND), 
  


  RECORD_PLAY(1005, Type.SOUND, Material.class), 
  


  GHAST_SHRIEK(1007, Type.SOUND), 
  


  GHAST_SHOOT(1008, Type.SOUND), 
  


  BLAZE_SHOOT(1009, Type.SOUND), 
  


  ZOMBIE_CHEW_WOODEN_DOOR(1010, Type.SOUND), 
  


  ZOMBIE_CHEW_IRON_DOOR(1011, Type.SOUND), 
  


  ZOMBIE_DESTROY_DOOR(1012, Type.SOUND), 
  


  SMOKE(2000, Type.VISUAL, BlockFace.class), 
  


  STEP_SOUND(2001, Type.SOUND, Material.class), 
  


  POTION_BREAK(2002, Type.VISUAL, Potion.class), 
  


  ENDER_SIGNAL(2003, Type.VISUAL), 
  


  MOBSPAWNER_FLAMES(2004, Type.VISUAL);
  
  private final int id;
  private final Type type;
  private final Class<?> data;
  private static final Map<Integer, Effect> BY_ID;
  
  private Effect(int id, Type type) {
    this(id, type, null);
  }
  
  private Effect(int id, Type type, Class<?> data) {
    this.id = id;
    this.type = type;
    this.data = data;
  }
  




  public int getId()
  {
    return this.id;
  }
  


  public Type getType()
  {
    return this.type;
  }
  


  public Class<?> getData()
  {
    return this.data;
  }
  





  public static Effect getById(int id)
  {
    return (Effect)BY_ID.get(Integer.valueOf(id));
  }
  
  static
  {
    BY_ID = Maps.newHashMap();
    












































    for (Effect effect : values()) {
      BY_ID.put(Integer.valueOf(effect.id), effect);
    }
  }
  

  public static enum Type
  {
    SOUND,  VISUAL;
    
    private Type() {}
  }
}
