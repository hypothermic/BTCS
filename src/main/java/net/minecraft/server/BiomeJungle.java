package net.minecraft.server;

import java.util.List;
import java.util.Random;



public class BiomeJungle
  extends BiomeBase
{
  public BiomeJungle(int paramInt)
  {
    super(paramInt);
    this.I.z = 50;
    this.I.B = 25;
    this.I.A = 4;
    
    this.J.add(new BiomeMeta(EntityOcelot.class, 2, 1, 1));
    

    this.K.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
  }
  

  public WorldGenerator a(Random paramRandom)
  {
    if (paramRandom.nextInt(10) == 0) {
      return this.O;
    }
    if (paramRandom.nextInt(2) == 0) {
      return new WorldGenGroundBush(3, 0);
    }
    if (paramRandom.nextInt(3) == 0) {
      return new WorldGenMegaTree(false, 10 + paramRandom.nextInt(20), 3, 3);
    }
    return new WorldGenTrees(false, 4 + paramRandom.nextInt(7), 3, 3, true);
  }
  
  public WorldGenerator b(Random paramRandom)
  {
    if (paramRandom.nextInt(4) == 0) {
      return new WorldGenGrass(Block.LONG_GRASS.id, 2);
    }
    return new WorldGenGrass(Block.LONG_GRASS.id, 1);
  }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
    
    WorldGenVines localWorldGenVines = new WorldGenVines();
    
    for (int i = 0; i < 50; i++) {
      int j = paramInt1 + paramRandom.nextInt(16) + 8;
      int k = 64;
      int m = paramInt2 + paramRandom.nextInt(16) + 8;
      localWorldGenVines.a(paramWorld, paramRandom, j, k, m);
    }
  }
}
