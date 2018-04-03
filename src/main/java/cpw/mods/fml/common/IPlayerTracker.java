package cpw.mods.fml.common;

public abstract interface IPlayerTracker
{
  public abstract void onPlayerLogin(Object paramObject);
  
  public abstract void onPlayerLogout(Object paramObject);
  
  public abstract void onPlayerChangedDimension(Object paramObject);
}
