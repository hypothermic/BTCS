package net.minecraft.server;

public class WM_ItemWeaponMod extends WM_Item
{
  protected int weaponDamage;
  protected float knockBack;
  protected float strVsBlock;
  protected int entityDamage;
  protected int blockDamage;
  protected int enchantability;
  
  public WM_ItemWeaponMod(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt);
    
    if (paramEnumToolMaterial == null)
    {
      paramEnumToolMaterial = EnumToolMaterial.WOOD;
    }
    
    this.maxStackSize = paramWM_EnumWeapon.getMaxStackSize();
    setMaxDurability((int)(paramWM_EnumWeapon.durabilityBase + paramEnumToolMaterial.a() * paramWM_EnumWeapon.durabilityMultiplier));
    this.weaponDamage = ((int)(paramWM_EnumWeapon.damageBase + paramEnumToolMaterial.c() * paramWM_EnumWeapon.damageMultiplier));
    this.knockBack = (paramWM_EnumWeapon.getKnockBack() + (paramEnumToolMaterial != EnumToolMaterial.GOLD ? 0.0F : 0.2F));
    this.strVsBlock = paramWM_EnumWeapon.getStrVsBlock();
    this.entityDamage = paramWM_EnumWeapon.getEntityDamage();
    this.blockDamage = paramWM_EnumWeapon.getBlockDamage();
    this.enchantability = paramEnumToolMaterial.e();
  }
  




  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    return this.strVsBlock;
  }
  




  public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
  {
    if (this.knockBack != 0.0F)
    {
      WM_PhysHelper.knockBack(paramEntityLiving1, paramEntityLiving2, this.knockBack);
    }
    
    paramItemStack.damage(this.entityDamage, paramEntityLiving2);
    return true;
  }
  
  public boolean a(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityLiving paramEntityLiving)
  {
    paramItemStack.damage(this.blockDamage, paramEntityLiving);
    return true;
  }
  



  public int a(Entity paramEntity)
  {
    return this.weaponDamage;
  }
  




  public void a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt) {}
  




  public EnumAnimation d(ItemStack paramItemStack)
  {
    return EnumAnimation.d;
  }
  



  public int c(ItemStack paramItemStack)
  {
    return 72000;
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    paramEntityHuman.a(paramItemStack, c(paramItemStack));
    return paramItemStack;
  }
  



  public int c()
  {
    return this.enchantability;
  }
}
