package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BiomeForest
  extends BiomeBase
{
  public BiomeForest(int paramInt)
  {
    super(paramInt);
    this.K.add(new BiomeMeta(EntityWolf.class, 5, 4, 4));
    this.I.z = 10;
    this.I.B = 2;
  }
  

  public WorldGenerator a(Random paramRandom)
  {
    if (paramRandom.nextInt(5) == 0) {
      return this.P;
    }
    if (paramRandom.nextInt(10) == 0) {
      return this.O;
    }
    return this.N;
  }
}
