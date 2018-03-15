package net.minecraft.server;

public class EnchantmentWaterWorker extends Enchantment
{
  public EnchantmentWaterWorker(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, EnchantmentSlotType.ARMOR_HEAD);
    a("waterWorker");
  }
  
  public int a(int paramInt)
  {
    return 1;
  }
  
  public int b(int paramInt)
  {
    return a(paramInt) + 40;
  }
  
  public int getMaxLevel()
  {
    return 1;
  }
}
