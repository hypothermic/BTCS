package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;




















public class BaseModTicker
  implements ITickHandler
{
  private BaseMod mod;
  private EnumSet<TickType> ticks;
  private boolean clockTickTrigger;
  
  BaseModTicker(BaseMod mod)
  {
    this.mod = mod;
    this.ticks = EnumSet.of(TickType.WORLDLOAD);
  }
  
  BaseModTicker(EnumSet<TickType> ticks)
  {
    this.ticks = ticks;
  }
  

  public void tickStart(EnumSet<TickType> types, Object... tickData)
  {
    tickBaseMod(types, false, tickData);
  }
  

  public void tickEnd(EnumSet<TickType> types, Object... tickData)
  {
    tickBaseMod(types, true, tickData);
  }
  
  private void tickBaseMod(EnumSet<TickType> types, boolean end, Object... tickData)
  {
    if ((FMLCommonHandler.instance().getSide().isClient()) && ((this.ticks.contains(TickType.GAME)) || (this.ticks.contains(TickType.WORLDGUI))))
    {
      EnumSet cTypes = EnumSet.copyOf(types);
      if ((end) && ((types.contains(TickType.GAME)) || (types.contains(TickType.WORLDLOAD)) || (types.contains(TickType.WORLDGUI))))
      {
        this.clockTickTrigger = true;
        cTypes.remove(TickType.GAME);
        cTypes.remove(TickType.WORLDLOAD);
        cTypes.remove(TickType.WORLDGUI);
      }
      
      if ((end) && (this.clockTickTrigger) && (types.contains(TickType.RENDER)))
      {
        this.clockTickTrigger = false;
        cTypes.remove(TickType.RENDER);
        if (this.ticks.contains(TickType.GAME)) cTypes.add(TickType.GAME);
        if (this.ticks.contains(TickType.WORLDGUI)) { cTypes.add(TickType.WORLDGUI);
        }
      }
      sendTick(cTypes, end, tickData);
    }
    else
    {
      sendTick(types, end, tickData);
    }
  }
  
  private void sendTick(EnumSet<TickType> types, boolean end, Object... tickData)
  {
    for (TickType type : types)
    {
      if (this.ticks.contains(type))
      {


        boolean keepTicking = this.mod.doTickInGame(type, end, FMLCommonHandler.instance().getMinecraftInstance(), tickData);
        if (!keepTicking) {
          this.ticks.remove(type);
          this.ticks.removeAll(type.partnerTicks());
        }
      }
    }
  }
  
  public EnumSet<TickType> ticks()
  {
    return this.clockTickTrigger ? EnumSet.of(TickType.RENDER) : this.ticks;
  }
  

  public String getLabel()
  {
    return this.mod.getClass().getSimpleName();
  }
  



  public void setMod(BaseMod mod)
  {
    this.mod = mod;
  }
}
