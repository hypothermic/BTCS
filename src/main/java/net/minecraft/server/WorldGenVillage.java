package net.minecraft.server;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class WorldGenVillage
  extends StructureGenerator
{
  public static List a = Arrays.asList(new BiomeBase[] { BiomeBase.PLAINS, BiomeBase.DESERT });
  

  private final int f;
  

  public WorldGenVillage(int paramInt)
  {
    this.f = paramInt;
  }
  
  protected boolean a(int paramInt1, int paramInt2)
  {
    int i = 32;
    int j = 8;
    
    int k = paramInt1;
    int m = paramInt2;
    if (paramInt1 < 0) paramInt1 -= i - 1;
    if (paramInt2 < 0) { paramInt2 -= i - 1;
    }
    int n = paramInt1 / i;
    int i1 = paramInt2 / i;
    Random localRandom = this.d.A(n, i1, 10387312);
    n *= i;
    i1 *= i;
    n += localRandom.nextInt(i - j);
    i1 += localRandom.nextInt(i - j);
    paramInt1 = k;
    paramInt2 = m;
    

    if ((paramInt1 == n) && (paramInt2 == i1)) {
      boolean bool = this.d.getWorldChunkManager().a(paramInt1 * 16 + 8, paramInt2 * 16 + 8, 0, a);
      if (bool)
      {

        return true;
      }
    }
    
    return false;
  }
  


















  protected StructureStart b(int paramInt1, int paramInt2)
  {
    return new WorldGenVillageStart(this.d, this.c, paramInt1, paramInt2, this.f);
  }
}
