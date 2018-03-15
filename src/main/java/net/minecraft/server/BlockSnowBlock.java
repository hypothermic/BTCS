package net.minecraft.server;

import java.util.Random;


public class BlockSnowBlock
  extends Block
{
  protected BlockSnowBlock(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.SNOW_BLOCK);
    a(true);
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Item.SNOW_BALL.id;
  }
  
  public int a(Random paramRandom) {
    return 4;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
    if (paramWorld.a(EnumSkyBlock.BLOCK, paramInt1, paramInt2, paramInt3, false) > 11) {
      b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
      paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
    }
  }
}
