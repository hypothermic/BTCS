package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;








public enum CropState
{
  SEEDED(0), 
  


  GERMINATED(1), 
  


  VERY_SMALL(2), 
  


  SMALL(3), 
  


  MEDIUM(4), 
  


  TALL(5), 
  


  VERY_TALL(6), 
  


  RIPE(7);
  
  private final byte data;
  private static final Map<Byte, CropState> BY_DATA;
  
  private CropState(int data) {
    this.data = ((byte)data);
  }
  




  public byte getData()
  {
    return this.data;
  }
  







  public static CropState getByData(byte data)
  {
    return (CropState)BY_DATA.get(Byte.valueOf(data));
  }
  
  static
  {
    BY_DATA = Maps.newHashMap();
    


























    for (CropState cropState : values()) {
      BY_DATA.put(Byte.valueOf(cropState.getData()), cropState);
    }
  }
}
