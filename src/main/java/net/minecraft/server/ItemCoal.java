package net.minecraft.server;


public class ItemCoal
  extends Item
{
  public ItemCoal(int paramInt)
  {
    super(paramInt);
    
    a(true);
    setMaxDurability(0);
  }
  

  public String a(ItemStack paramItemStack)
  {
    if (paramItemStack.getData() == 1) {
      return "item.charcoal";
    }
    return "item.coal";
  }
}
