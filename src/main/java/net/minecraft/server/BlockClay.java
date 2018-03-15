package net.minecraft.server;

import java.util.Random;

public class BlockClay
  extends Block
{
  public BlockClay(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.CLAY);
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Item.CLAY_BALL.id;
  }
  
  public int a(Random paramRandom) {
    return 4;
  }
}
