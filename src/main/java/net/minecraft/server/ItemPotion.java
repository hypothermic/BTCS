package net.minecraft.server;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemPotion
  extends Item
{
  private HashMap a = new HashMap();
  
  public ItemPotion(int paramInt) {
    super(paramInt);
    
    e(1);
    a(true);
    setMaxDurability(0);
  }
  
  public List b(ItemStack paramItemStack) {
    return b(paramItemStack.getData());
  }
  
  public List b(int paramInt)
  {
    List localList = (List)this.a.get(Integer.valueOf(paramInt));
    if (localList == null) {
      localList = PotionBrewer.getEffects(paramInt, false);
      this.a.put(Integer.valueOf(paramInt), localList);
    }
    return localList;
  }
  
  public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
    paramItemStack.count -= 1;
    
    if (!paramWorld.isStatic) {
      List localList = b(paramItemStack);
      if (localList != null) {
        for (MobEffect localMobEffect : (MobEffect[]) localList.toArray()) { // BTCS: added cast and .toArray()
          paramEntityHuman.addEffect(new MobEffect(localMobEffect));
        }
      }
    }
    if (paramItemStack.count <= 0) {
      return new ItemStack(Item.GLASS_BOTTLE);
    }
    paramEntityHuman.inventory.pickup(new ItemStack(Item.GLASS_BOTTLE));
    

    return paramItemStack;
  }
  
  public int c(ItemStack paramItemStack) {
    return 32;
  }
  
  public EnumAnimation d(ItemStack paramItemStack) {
    return EnumAnimation.c;
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
    if (c(paramItemStack.getData())) {
      paramItemStack.count -= 1;
      paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
      if (!paramWorld.isStatic) paramWorld.addEntity(new EntityPotion(paramWorld, paramEntityHuman, paramItemStack.getData()));
      return paramItemStack;
    }
    paramEntityHuman.a(paramItemStack, c(paramItemStack));
    return paramItemStack;
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return false;
  }
  















  public static boolean c(int paramInt)
  {
    return (paramInt & 0x4000) != 0;
  }
}
