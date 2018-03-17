package net.minecraft.server;

public enum WM_EnumWeapon
{
  SPEAR(0, 1.0F, 3, 2.0F, 1.0F, 0.2F, 1, 0, 1), 
  HALBERD(0, 1.0F, 5, 2.0F, 1.5F, 0.6F, 1, 2, 1), 
  BATTLEAXE(0, 0.7F, 6, 2.0F, 1.5F, 0.8F, 1, 2, 1), 
  WARHAMMER(0, 1.0F, 4, 2.0F, 1.0F, 0.8F, 1, 2, 1), 
  KNIFE(0, 0.5F, 3, 2.0F, 1.5F, 0.2F, 1, 2, 1), 
  FLAIL(0, 1.0F, 1, 0.0F, 1.0F, 0.0F, 0, 0, 1), 
  FIREROD(1, 0.0F, 1, 0.0F, 1.0F, 0.0F, 2, 0, 1), 
  MUSKET(0, 1.0F, 1, 0.0F, 1.0F, 0.0F, 0, 0, 1), 
  NONE(0, 0.0F, 1, 0.0F, 1.0F, 0.0F, 0, 0, 1);
  
  public final int damageBase;
  public final float damageMultiplier;
  public final int durabilityBase;
  public final float durabilityMultiplier;
  private final int maxStackSize;
  private final float knockBack;
  private final float strVsBlock;
  private final int entityDamage;
  private final int blockDamage;
  
  private WM_EnumWeapon(int paramInt1, float paramFloat1, int paramInt2, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt3, int paramInt4, int paramInt5)
  {
    this.durabilityBase = paramInt1;
    this.durabilityMultiplier = paramFloat1;
    this.damageBase = paramInt2;
    this.damageMultiplier = paramFloat2;
    this.maxStackSize = paramInt5;
    this.knockBack = paramFloat4;
    this.strVsBlock = paramFloat3;
    this.entityDamage = paramInt3;
    this.blockDamage = paramInt4;
  }
  
  public int getEntityDamage()
  {
    return this.entityDamage;
  }
  
  public int getBlockDamage()
  {
    return this.blockDamage;
  }
  
  public float getKnockBack()
  {
    return this.knockBack;
  }
  
  public float getStrVsBlock()
  {
    return this.strVsBlock;
  }
  
  public int getMaxStackSize()
  {
    return this.maxStackSize;
  }
}
