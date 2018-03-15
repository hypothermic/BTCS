package net.minecraft.server;

import java.util.Random;


public class BlockLightStone
  extends Block
{
  public BlockLightStone(int paramInt1, int paramInt2, Material paramMaterial)
  {
    super(paramInt1, paramInt2, paramMaterial);
  }
  
  public int getDropCount(int paramInt, Random paramRandom)
  {
    return MathHelper.a(a(paramRandom) + paramRandom.nextInt(paramInt + 1), 1, 4);
  }
  
  public int a(Random paramRandom) {
    return 2 + paramRandom.nextInt(3);
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Item.GLOWSTONE_DUST.id;
  }
}
