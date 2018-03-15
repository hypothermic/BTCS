package net.minecraft.server;

public class ItemWithAuxData
  extends ItemBlock
{
  private Block a;
  
  public ItemWithAuxData(int paramInt, Block paramBlock)
  {
    super(paramInt);
    
    this.a = paramBlock;
    
    setMaxDurability(0);
    a(true);
  }
  





  public int filterData(int paramInt)
  {
    return paramInt;
  }
}
