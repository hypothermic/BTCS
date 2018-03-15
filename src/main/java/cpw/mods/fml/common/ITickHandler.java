package cpw.mods.fml.common;

import java.util.EnumSet;

public abstract interface ITickHandler
{
  public abstract void tickStart(EnumSet<TickType> paramEnumSet, Object... paramVarArgs);
  
  public abstract void tickEnd(EnumSet<TickType> paramEnumSet, Object... paramVarArgs);
  
  public abstract EnumSet<TickType> ticks();
  
  public abstract String getLabel();
}
