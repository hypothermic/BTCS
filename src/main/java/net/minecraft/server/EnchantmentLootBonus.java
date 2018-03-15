package net.minecraft.server;

public class EnchantmentLootBonus extends Enchantment
{
  protected EnchantmentLootBonus(int paramInt1, int paramInt2, EnchantmentSlotType paramEnchantmentSlotType) {
    super(paramInt1, paramInt2, paramEnchantmentSlotType);
    
    a("lootBonus");
    if (paramEnchantmentSlotType == EnchantmentSlotType.DIGGER) {
      a("lootBonusDigger");
    }
  }
  
  public int a(int paramInt)
  {
    return 20 + (paramInt - 1) * 12;
  }
  
  public int b(int paramInt)
  {
    return super.a(paramInt) + 50;
  }
  
  public int getMaxLevel()
  {
    return 3;
  }
  
  public boolean a(Enchantment paramEnchantment)
  {
    return (super.a(paramEnchantment)) && (paramEnchantment.id != SILK_TOUCH.id);
  }
}
