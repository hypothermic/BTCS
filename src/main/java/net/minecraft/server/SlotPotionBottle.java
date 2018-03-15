package net.minecraft.server;





















































class SlotPotionBottle
  extends Slot
{
  private EntityHuman f;
  



















































  public SlotPotionBottle(ContainerBrewingStand paramContainerBrewingStand, EntityHuman paramEntityHuman, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramIInventory, paramInt1, paramInt2, paramInt3);
    this.f = paramEntityHuman;
  }
  
  public boolean isAllowed(ItemStack paramItemStack)
  {
    return (paramItemStack != null) && ((paramItemStack.id == Item.POTION.id) || (paramItemStack.id == Item.GLASS_BOTTLE.id));
  }
  
  public int a()
  {
    return 1;
  }
  
  public void c(ItemStack paramItemStack)
  {
    if ((paramItemStack.id == Item.POTION.id) && (paramItemStack.getData() > 0)) this.f.a(AchievementList.A, 1);
    super.c(paramItemStack);
  }
}
