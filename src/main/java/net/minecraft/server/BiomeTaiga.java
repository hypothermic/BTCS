package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BiomeTaiga
  extends BiomeBase
{
  public BiomeTaiga(int paramInt)
  {
    super(paramInt);
    
    this.K.add(new BiomeMeta(EntityWolf.class, 8, 4, 4));
    
    this.I.z = 10;
    this.I.B = 1;
  }
  

  public WorldGenerator a(Random paramRandom)
  {
    if (paramRandom.nextInt(3) == 0) {
      return new WorldGenTaiga1();
    }
    return new WorldGenTaiga2(false);
  }
}
