package net.minecraft.server;

class SlotArmor extends Slot
{
	// BTCS start
	final int a;

    final ContainerPlayer f;

    SlotArmor(ContainerPlayer containerplayer, IInventory iinventory, int i, int j, int k, int l) {
        super(iinventory, i, j, k);
        this.f = containerplayer;
        this.a = l;
    }
    // BTCS end
  
  public int a() {
      return 1;
  }
  
  public boolean isAllowed(ItemStack paramItemStack)
  {
    if ((paramItemStack.getItem() instanceof ItemArmor)) {
      return ((ItemArmor)paramItemStack.getItem()).a == this.a;
    }
    if (paramItemStack.getItem().id == Block.PUMPKIN.id) {
      return this.a == 0;
    }
    return false;
  }
}
