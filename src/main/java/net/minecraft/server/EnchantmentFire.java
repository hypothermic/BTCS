package net.minecraft.server;

public class EnchantmentFire extends Enchantment
{
  protected EnchantmentFire(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2, EnchantmentSlotType.WEAPON);
    
    a("fire");
  }
  
  public int a(int paramInt)
  {
    return 10 + 20 * (paramInt - 1);
  }
  
  public int b(int paramInt)
  {
    return super.a(paramInt) + 50;
  }
  
  public int getMaxLevel()
  {
    return 2;
  }
}
