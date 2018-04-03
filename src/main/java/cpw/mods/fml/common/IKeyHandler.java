package cpw.mods.fml.common;

public abstract interface IKeyHandler
{
  public abstract Object getKeyBinding();
  
  public abstract ModContainer getOwningContainer();
  
  public abstract void onEndTick();
}
