package net.minecraft.server;

public enum EnchantmentSlotType
{
	// BTCS start
	ALL("all", 0), ARMOR("armor", 1), ARMOR_FEET("armor_feet", 2), ARMOR_LEGS("armor_legs", 3), ARMOR_TORSO("armor_torso", 4), ARMOR_HEAD("armor_head", 5), WEAPON("weapon", 6), DIGGER("digger", 7), BOW("bow", 8);
	
	private static final EnchantmentSlotType[] j = new EnchantmentSlotType[] { ALL, ARMOR, ARMOR_FEET, ARMOR_LEGS, ARMOR_TORSO, ARMOR_HEAD, WEAPON, DIGGER, BOW};

    private EnchantmentSlotType(String s, int i) {}

	// BTCS end
  public boolean canEnchant(Item paramItem)
  {
    if (this == ALL) { return true;
    }
    if ((paramItem instanceof ItemArmor)) {
      if (this == ARMOR) return true;
      ItemArmor localItemArmor = (ItemArmor)paramItem;
      if (localItemArmor.a == 0) return this == ARMOR_HEAD;
      if (localItemArmor.a == 2) return this == ARMOR_LEGS;
      if (localItemArmor.a == 1) return this == ARMOR_TORSO;
      if (localItemArmor.a == 3) return this == ARMOR_FEET;
      return false; }
    if ((paramItem instanceof ItemSword))
      return this == WEAPON;
    if ((paramItem instanceof ItemTool))
      return this == DIGGER;
    if ((paramItem instanceof ItemBow)) {
      return this == BOW;
    }
    return false;
  }
}
