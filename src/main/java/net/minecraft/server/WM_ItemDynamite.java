package net.minecraft.server;

import java.util.Random;

public class WM_ItemDynamite extends WM_Item
{
  private EntityHuman ep;
  private World wo;
  private ItemStack is;
  private boolean primed;
  private int fuse;
  
  public WM_ItemDynamite(int paramInt)
  {
    super(paramInt);
    this.maxStackSize = 64;
  }
  




  public void a(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean)
  {
    if (paramItemStack == this.is)
    {
      if ((this.fuse > 0) && (this.primed))
      {
        this.fuse -= 1;
        spawnSmoke(this.ep);
      }
      else
      {
        this.fuse = 0;
      }
      
      if ((this.fuse <= 5) && (this.primed))
      {
        this.primed = false;
        this.wo.makeSound(this.ep, "random.fizz", 1.0F, 1.2F / (c.nextFloat() * 0.2F + 0.9F));
      }
    }
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (!this.primed)
    {
      this.fuse = (c.nextInt(10) + 60);
      this.primed = true;
      paramWorld.makeSound(paramEntityHuman, "random.fuse", 1.0F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
      this.is = paramItemStack;
      this.wo = paramWorld;
      this.ep = paramEntityHuman;
    }
    else
    {
      ItemStack localItemStack = throwDynamite(paramItemStack, paramWorld, paramEntityHuman);
      return localItemStack;
    }
    
    return paramItemStack;
  }
  
  private ItemStack throwDynamite(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if ((paramEntityHuman.inventory.c(this.id)) && (!paramWorld.isStatic))
    {
      paramWorld.addEntity(new WM_EntityDynamite(paramWorld, paramEntityHuman, this.fuse));
    }
    
    this.primed = false;
    this.fuse = 0;
    return paramItemStack;
  }
  
  private void spawnSmoke(EntityHuman paramEntityHuman)
  {
    float f1 = -MathHelper.sin((paramEntityHuman.yaw + 23.0F) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntityHuman.pitch / 180.0F * 3.1415927F);
    float f2 = -MathHelper.sin(paramEntityHuman.pitch / 180.0F * 3.1415927F);
    float f3 = MathHelper.cos((paramEntityHuman.yaw + 23.0F) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntityHuman.pitch / 180.0F * 3.1415927F);
    paramEntityHuman.world.a("smoke", paramEntityHuman.locX + f1, paramEntityHuman.locY + f2, paramEntityHuman.locZ + f3, 0.0D, 0.0D, 0.0D);
  }
  
  public boolean shouldRotateAroundWhenRendering()
  {
    return true;
  }
}
