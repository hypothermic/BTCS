package net.minecraft.server;

public class WM_ItemHalberd extends WM_ItemWeaponMod
{
  private float initialKnockBack;
  private float initialWeaponDamage;
  private float initialStrVsBlock;
  public boolean stabMode;
  
  public WM_ItemHalberd(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon);
    this.initialKnockBack = this.knockBack;
    this.initialWeaponDamage = this.weaponDamage;
    this.initialStrVsBlock = this.strVsBlock;
  }
  



  public int c(ItemStack paramItemStack)
  {
    return 72000;
  }
  



  public EnumAnimation d(ItemStack paramItemStack)
  {
    return EnumAnimation.a;
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    this.stabMode = (!this.stabMode);
    
    if (this.stabMode)
    {
      this.knockBack *= 0.6F;
      this.weaponDamage = ((int)(this.weaponDamage * 1.2F));
      this.strVsBlock /= 1.5F;
    }
    else
    {
      this.knockBack = this.initialKnockBack;
      this.weaponDamage = ((int)this.initialWeaponDamage);
      this.strVsBlock = this.initialStrVsBlock;
    }
    
    return paramItemStack;
  }
  




  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    return this.strVsBlock * (paramBlock.id != Block.WEB.id ? 1.0F : 10.0F);
  }
  



  public boolean canDestroySpecialBlock(Block paramBlock)
  {
    return paramBlock.id == Block.WEB.id;
  }
}
