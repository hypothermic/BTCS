package net.minecraft.server;





public class ItemGlassBottle
  extends Item
{
  public ItemGlassBottle(int paramInt)
  {
    super(paramInt);
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    MovingObjectPosition localMovingObjectPosition = a(paramWorld, paramEntityHuman, true);
    if (localMovingObjectPosition == null) { return paramItemStack;
    }
    if (localMovingObjectPosition.type == EnumMovingObjectType.TILE) {
      int i = localMovingObjectPosition.b;
      int j = localMovingObjectPosition.c;
      int k = localMovingObjectPosition.d;
      
      if (!paramWorld.a(paramEntityHuman, i, j, k)) {
        return paramItemStack;
      }
      if (!paramEntityHuman.d(i, j, k)) {
        return paramItemStack;
      }
      if (paramWorld.getMaterial(i, j, k) == Material.WATER)
      {
        paramItemStack.count -= 1;
        if (paramItemStack.count <= 0) {
          return new ItemStack(Item.POTION);
        }
        if (!paramEntityHuman.inventory.pickup(new ItemStack(Item.POTION))) {
          paramEntityHuman.drop(new ItemStack(Item.POTION.id, 1, 0));
        }
      }
    }
    

    return paramItemStack;
  }
}
