package net.minecraft.server;

import java.util.Random;






public class BlockMelon
  extends Block
{
  protected BlockMelon(int paramInt)
  {
    super(paramInt, Material.PUMPKIN);
    this.textureId = 136;
  }
  
  public int a(int paramInt1, int paramInt2) {
    if ((paramInt1 == 1) || (paramInt1 == 0)) return 137;
    return 136;
  }
  
  public int a(int paramInt) {
    if ((paramInt == 1) || (paramInt == 0)) return 137;
    return 136;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.MELON.id;
  }
  
  public int a(Random paramRandom)
  {
    return 3 + paramRandom.nextInt(5);
  }
  
  public int getDropCount(int paramInt, Random paramRandom)
  {
    int i = a(paramRandom) + paramRandom.nextInt(1 + paramInt);
    if (i > 9) {
      i = 9;
    }
    return i;
  }
}
