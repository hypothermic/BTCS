package net.minecraft.server;

import java.util.Random;

public class WorldGenFlowers
  extends WorldGenerator
{
  private int a;
  
  public WorldGenFlowers(int paramInt)
  {
    this.a = paramInt;
  }
  
  public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
    for (int i = 0; i < 64; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if ((paramWorld.isEmpty(j, k, m)) && 
        (((BlockFlower)Block.byId[this.a]).f(paramWorld, j, k, m))) {
        paramWorld.setRawTypeId(j, k, m, this.a);
      }
    }
    

    return true;
  }
}
