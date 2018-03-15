package net.minecraft.server;








public class ItemArmor
  extends Item
{
  private static final int[] bV = { 11, 16, 15, 13 };
  







  public final int a;
  







  public final int b;
  







  public final int bU;
  






  private final EnumArmorMaterial bW;
  







  public ItemArmor(int paramInt1, EnumArmorMaterial paramEnumArmorMaterial, int paramInt2, int paramInt3)
  {
    super(paramInt1);
    this.bW = paramEnumArmorMaterial;
    this.a = paramInt3;
    this.bU = paramInt2;
    this.b = paramEnumArmorMaterial.b(paramInt3);
    setMaxDurability(paramEnumArmorMaterial.a(paramInt3));
    this.maxStackSize = 1;
  }
  
  public int c()
  {
    return this.bW.a();
  }
  
  // BTCS start
  static int[] o() {
	  return bV;
  }
  // BTCS end
}
