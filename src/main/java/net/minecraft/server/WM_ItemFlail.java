package net.minecraft.server;

import java.util.Random;

public class WM_ItemFlail extends WM_ItemWeaponMod
{
  private int iconIndexHere;
  private int iconIndexThrown;
  public EnumToolMaterial enumToolMaterial;
  private WM_EntityFlail ball;
  private boolean flailThrown;
  
  public WM_ItemFlail(int paramInt1, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramEnumToolMaterial, paramWM_EnumWeapon);
    this.enumToolMaterial = paramEnumToolMaterial;
    this.iconIndexHere = paramInt2;
    this.iconIndexThrown = paramInt3;
  }
  




  public void a(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean)
  {
    if (!(paramEntity instanceof EntityHuman))
    {
      return;
    }
    
    ItemStack localItemStack = ((EntityHuman)paramEntity).U();
    
    if ((localItemStack == null) || (localItemStack.getItem().id != this.id))
    {
      if (paramWorld.isStatic)
      {
        setThrown(false);
      }
      else if (this.ball != null)
      {
        this.ball.pickUpByOwner();
      }
    }
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    paramEntityHuman.C_();
    
    if (this.ball != null)
    {
      this.ball.pickUpByOwner();
    }
    
    if (!this.flailThrown)
    {
      paramItemStack.damage(1, paramEntityHuman);
      
      if (paramItemStack.count > 0)
      {
        throwBall(paramItemStack, paramWorld, paramEntityHuman);
      }
    }
    
    return paramItemStack;
  }
  
  public void throwBall(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
    
    if (!paramWorld.isStatic)
    {
      this.ball = new WM_EntityFlail(paramWorld, paramEntityHuman, this.id);
      paramWorld.addEntity(this.ball);
    }
    
    setThrown(true);
  }
  
  public void setThrown(boolean paramBoolean)
  {
    d(paramBoolean ? this.iconIndexThrown : this.iconIndexHere);
    this.flailThrown = paramBoolean;
  }
  
  public boolean getThrown()
  {
    return this.flailThrown;
  }
}
