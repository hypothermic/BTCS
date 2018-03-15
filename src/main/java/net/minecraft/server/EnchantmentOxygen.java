package net.minecraft.server;

public class EnchantmentOxygen extends Enchantment
{
  public EnchantmentOxygen(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.ARMOR_HEAD);
    a("oxygen");
  }
  
  public int a(int paramInt)
  {
    return 10 * paramInt;
  }
  
  public int b(int paramInt)
  {
    return a(paramInt) + 30;
  }
  
  public int getMaxLevel()
  {
    return 3;
  }
}
