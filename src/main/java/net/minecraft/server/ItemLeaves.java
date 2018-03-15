package net.minecraft.server;


public class ItemLeaves
  extends ItemBlock
{
  public ItemLeaves(int paramInt)
  {
    super(paramInt);
    
    setMaxDurability(0);
    a(true);
  }
  
  public int filterData(int paramInt)
  {
    return paramInt | 0x4;
  }
}
