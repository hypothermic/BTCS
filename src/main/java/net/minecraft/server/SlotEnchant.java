package net.minecraft.server;


































class SlotEnchant
  extends Slot
{
  SlotEnchant(ContainerEnchantTable paramContainerEnchantTable, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3) { super(paramIInventory, paramInt1, paramInt2, paramInt3); }
  
  public boolean isAllowed(ItemStack paramItemStack) { return true; }
}
