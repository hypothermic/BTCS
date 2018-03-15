package net.minecraft.server;

public class EnchantmentArrowKnockback extends Enchantment
{
  public EnchantmentArrowKnockback(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.BOW);
    a("arrowKnockback");
  }
  
  public int a(int paramInt)
  {
    return 12 + (paramInt - 1) * 20;
  }
  
  public int b(int paramInt)
  {
    return a(paramInt) + 25;
  }
  
  public int getMaxLevel()
  {
    return 2;
  }
}
