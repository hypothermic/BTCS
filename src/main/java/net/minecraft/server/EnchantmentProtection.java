package net.minecraft.server;







public class EnchantmentProtection
  extends Enchantment
{
  private static final String[] A = { "all", "fire", "fall", "explosion", "projectile" };
  


  private static final int[] B = { 1, 10, 5, 5, 3 };
  


  private static final int[] C = { 16, 8, 6, 8, 6 };
  


  private static final int[] D = { 20, 12, 10, 12, 15 };
  
  public final int a;
  

  public EnchantmentProtection(int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.ARMOR);
    this.a = paramInt3;
    
    if (paramInt3 == 2) {
      this.slot = EnchantmentSlotType.ARMOR_FEET;
    }
  }
  
  public int a(int paramInt)
  {
    return B[this.a] + (paramInt - 1) * C[this.a];
  }
  
  public int b(int paramInt)
  {
    return a(paramInt) + D[this.a];
  }
  
  public int getMaxLevel()
  {
    return 4;
  }
  
  public int a(int paramInt, DamageSource paramDamageSource) {
    if (paramDamageSource.ignoresInvulnerability()) { return 0;
    }
    int i = (6 + paramInt * paramInt) / 2;
    
    if (this.a == 0) return i;
    if ((this.a == 1) && (paramDamageSource.k())) return i;
    if ((this.a == 2) && (paramDamageSource == DamageSource.FALL)) return i * 2;
    if ((this.a == 3) && (paramDamageSource == DamageSource.EXPLOSION)) return i;
    if ((this.a == 4) && (paramDamageSource.c())) return i;
    return 0;
  }
  





  public boolean a(Enchantment paramEnchantment)
  {
    if ((paramEnchantment instanceof EnchantmentProtection)) {
      EnchantmentProtection localEnchantmentProtection = (EnchantmentProtection)paramEnchantment;
      
      if (localEnchantmentProtection.a == this.a) {
        return false;
      }
      if ((this.a == 2) || (localEnchantmentProtection.a == 2)) {
        return true;
      }
      return false;
    }
    return super.a(paramEnchantment);
  }
}
