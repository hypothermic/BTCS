package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;





public enum Instrument
{
  PIANO(0), 
  


  BASS_DRUM(1), 
  


  SNARE_DRUM(2), 
  


  STICKS(3), 
  


  BASS_GUITAR(4);
  
  private final byte type;
  private static final Map<Byte, Instrument> BY_DATA;
  
  private Instrument(int type) {
    this.type = ((byte)type);
  }
  


  public byte getType()
  {
    return this.type;
  }
  




  public static Instrument getByType(byte type)
  {
    return (Instrument)BY_DATA.get(Byte.valueOf(type));
  }
  
  static
  {
    BY_DATA = Maps.newHashMap();
    





















    for (Instrument instrument : values()) {
      BY_DATA.put(Byte.valueOf(instrument.getType()), instrument);
    }
  }
}
