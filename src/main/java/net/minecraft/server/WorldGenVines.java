package net.minecraft.server;

import java.util.Random;












public class WorldGenVines
  extends WorldGenerator
{
  public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1;
    int j = paramInt3;
    
    while (paramInt2 < 128) {
      if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) {
        for (int k = 2; k <= 5; k++) {
          if (Block.VINE.canPlace(paramWorld, paramInt1, paramInt2, paramInt3, k)) {
            paramWorld.setRawTypeIdAndData(paramInt1, paramInt2, paramInt3, Block.VINE.id, 1 << Direction.d[Facing.OPPOSITE_FACING[k]]);
            break;
          }
        }
      } else {
        paramInt1 = i + paramRandom.nextInt(4) - paramRandom.nextInt(4);
        paramInt3 = j + paramRandom.nextInt(4) - paramRandom.nextInt(4);
      }
      paramInt2++;
    }
    
    return true;
  }
}
