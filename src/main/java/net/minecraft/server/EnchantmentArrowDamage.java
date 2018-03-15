package net.minecraft.server;

public class EnchantmentArrowDamage extends Enchantment
{
  public EnchantmentArrowDamage(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.BOW);
    a("arrowDamage");
  }
  
  public int a(int paramInt)
  {
    return 1 + (paramInt - 1) * 10;
  }
  
  public int b(int paramInt)
  {
    return a(paramInt) + 15;
  }
  
  public int getMaxLevel()
  {
    return 5;
  }
}
