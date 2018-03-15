package net.minecraft.server;

public class ItemPiston
  extends ItemBlock
{
  public ItemPiston(int paramInt)
  {
    super(paramInt);
  }
  

  public int filterData(int paramInt)
  {
    return 7;
  }
}
