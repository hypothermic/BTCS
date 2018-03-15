package net.minecraft.server;

public class ItemSapling
  extends ItemBlock
{
  public ItemSapling(int paramInt)
  {
    super(paramInt);
    
    setMaxDurability(0);
    a(true);
  }
  
  public int filterData(int paramInt)
  {
    return paramInt;
  }
}
