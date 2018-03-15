package net.minecraft.server;

public class EnchantmentDigging extends Enchantment
{
  protected EnchantmentDigging(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2, EnchantmentSlotType.DIGGER);
    
    a("digging");
  }
  
  public int a(int paramInt)
  {
    return 1 + 15 * (paramInt - 1);
  }
  
  public int b(int paramInt)
  {
    return super.a(paramInt) + 50;
  }
  
  public int getMaxLevel()
  {
    return 5;
  }
}
