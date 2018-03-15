package net.minecraft.server;

import java.util.Random;

public class WorldGenLightStone2
  extends WorldGenerator
{
  public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    if (!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) return false;
    if (paramWorld.getTypeId(paramInt1, paramInt2 + 1, paramInt3) != Block.NETHERRACK.id) return false;
    paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.GLOWSTONE.id);
    
    for (int i = 0; i < 1500; i++) {
      int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      int k = paramInt2 - paramRandom.nextInt(12);
      int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
      if (paramWorld.getTypeId(j, k, m) == 0)
      {
        int n = 0;
        for (int i1 = 0; i1 < 6; i1++) {
          int i2 = 0;
          if (i1 == 0) i2 = paramWorld.getTypeId(j - 1, k, m);
          if (i1 == 1) i2 = paramWorld.getTypeId(j + 1, k, m);
          if (i1 == 2) i2 = paramWorld.getTypeId(j, k - 1, m);
          if (i1 == 3) i2 = paramWorld.getTypeId(j, k + 1, m);
          if (i1 == 4) i2 = paramWorld.getTypeId(j, k, m - 1);
          if (i1 == 5) { i2 = paramWorld.getTypeId(j, k, m + 1);
          }
          if (i2 == Block.GLOWSTONE.id) { n++;
          }
        }
        if (n == 1) paramWorld.setTypeId(j, k, m, Block.GLOWSTONE.id);
      }
    }
    return true;
  }
}
