package net.minecraft.server;

public class EnchantmentDurability extends Enchantment
{
  protected EnchantmentDurability(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2, EnchantmentSlotType.DIGGER);
    
    a("durability");
  }
  
  public int a(int paramInt)
  {
    return 5 + (paramInt - 1) * 10;
  }
  
  public int b(int paramInt)
  {
    return super.a(paramInt) + 50;
  }
  
  public int getMaxLevel()
  {
    return 3;
  }
}
