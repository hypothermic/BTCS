package cpw.mods.fml.common;

public abstract interface IScheduledTickHandler
  extends ITickHandler
{
  public abstract int nextTickSpacing();
}
