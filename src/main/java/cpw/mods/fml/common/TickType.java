package cpw.mods.fml.common;

import java.util.EnumSet;




















public enum TickType
{
  WORLD, 
  




  RENDER, 
  





  GUI, 
  




  WORLDGUI, 
  



  WORLDLOAD, 
  




  GUILOAD, 
  



  GAME, 
  





  PLAYER, 
  




  RESETMARKER;
  


  private TickType() {}
  

  public EnumSet<TickType> partnerTicks()
  {
    if (this == GAME) return EnumSet.of(RENDER);
    if (this == RENDER) return EnumSet.of(GAME);
    if (this == GUI) return EnumSet.of(WORLDGUI, GUILOAD);
    if (this == WORLDGUI) return EnumSet.of(GUI, GUILOAD);
    if (this == GUILOAD) return EnumSet.of(GUI, WORLDGUI);
    return EnumSet.noneOf(TickType.class);
  }
}
