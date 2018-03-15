package net.minecraft.server;


public class ItemWorldMapBase
  extends Item
{
  protected ItemWorldMapBase(int paramInt)
  {
    super(paramInt);
  }
  
  public boolean t_() {
    return true;
  }
  
  public Packet c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
    return null;
  }
}
