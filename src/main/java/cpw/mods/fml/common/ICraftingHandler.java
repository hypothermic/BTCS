package cpw.mods.fml.common;

public abstract interface ICraftingHandler
{
  public abstract void onCrafting(Object... paramVarArgs);
  
  public abstract void onSmelting(Object... paramVarArgs);
}
