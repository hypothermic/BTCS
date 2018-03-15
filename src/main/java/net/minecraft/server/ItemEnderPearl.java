package net.minecraft.server;

import java.util.Random;

public class ItemEnderPearl extends Item
{
  public ItemEnderPearl(int paramInt)
  {
    super(paramInt);
    this.maxStackSize = 16;
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
    if (paramEntityHuman.abilities.canInstantlyBuild) return paramItemStack;
    if (paramEntityHuman.vehicle != null) { return paramItemStack;
    }
    paramItemStack.count -= 1;
    paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
    if (!paramWorld.isStatic) paramWorld.addEntity(new EntityEnderPearl(paramWorld, paramEntityHuman));
    return paramItemStack;
  }
}
