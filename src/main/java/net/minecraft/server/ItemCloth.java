package net.minecraft.server;


public class ItemCloth
  extends ItemBlock
{
  public ItemCloth(int paramInt)
  {
    super(paramInt);
    
    setMaxDurability(0);
    a(true);
  }
  






  public int filterData(int paramInt)
  {
    return paramInt;
  }
  
  public String a(ItemStack paramItemStack)
  {
    return super.getName() + "." + ItemDye.a[BlockCloth.d(paramItemStack.getData())];
  }
}
