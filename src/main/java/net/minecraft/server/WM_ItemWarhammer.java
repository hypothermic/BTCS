package net.minecraft.server;

public class WM_ItemWarhammer extends WM_ItemWeaponMod
{
  private static long chargeStartTime;
  private static int chargeDelay;
  public static final int CHARGE_DELAY = 30000;
  
  public WM_ItemWarhammer(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon);
    chargeStartTime = 0L;
    chargeDelay = 0;
    
    switch (paramEnumToolMaterial.ordinal())
    {
    case 1: 
      this.strVsBlock *= 2.0F;
      break;
    
    case 2: 
      this.strVsBlock *= 3.0F;
      break;
    
    case 3: 
      this.strVsBlock *= 4.0F;
      break;
    
    case 4: 
      this.strVsBlock *= 5.0F;
      break;
    
    case 5: 
      this.strVsBlock *= 2.0F;
    }
    
  }
  



  public void a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt)
  {
    int i = c(paramItemStack) - paramInt;
    float f = i / 20.0F;
    f = (f * f + f * 2.0F) / 4.0F;
    
    if (f > 1.0F)
    {
      superSmash(paramItemStack, paramWorld, paramEntityHuman);
    }
  }
  
  protected void superSmash(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramEntityHuman.aU())
    {
      return;
    }
    
    paramEntityHuman.C_();
    
    if (!paramWorld.isStatic)
    {
      float f = this.weaponDamage / 2.0F;
      WM_PhysHelper.createAdvancedExplosion(paramWorld, paramEntityHuman, paramEntityHuman.locX, paramEntityHuman.locY - paramEntityHuman.getHeadHeight(), paramEntityHuman.locZ, f, false, false, true, false);
    }
    
    WM_WarhammerCharge.getWarhammerCharge(paramEntityHuman).resetCharge(30000);
    paramItemStack.damage(4, paramEntityHuman);
  }
  



  public EnumAnimation d(ItemStack paramItemStack)
  {
    return EnumAnimation.e;
  }
  



  public int c(ItemStack paramItemStack)
  {
    return 72000;
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramItemStack.count <= 0)
    {
      return paramItemStack;
    }
    
    if (WM_WarhammerCharge.getWarhammerCharge(paramEntityHuman).isCharged())
    {
      int i = c(paramItemStack);
      paramEntityHuman.a(paramItemStack, i);
    }
    
    return paramItemStack;
  }
}
