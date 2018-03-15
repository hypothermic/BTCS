package net.minecraft.server;

import java.util.List;
import java.util.Random;


public class BiomeDesert
  extends BiomeBase
{
  public BiomeDesert(int paramInt)
  {
    super(paramInt);
    

    this.K.clear();
    this.A = ((byte)Block.SAND.id);
    this.B = ((byte)Block.SAND.id);
    
    this.I.z = 64537;
    this.I.C = 2;
    this.I.E = 50;
    this.I.F = 10;
  }
  
  public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    super.a(paramWorld, paramRandom, paramInt1, paramInt2);
    
    if (paramRandom.nextInt(1000) == 0) {
      int i = paramInt1 + paramRandom.nextInt(16) + 8;
      int j = paramInt2 + paramRandom.nextInt(16) + 8;
      WorldGenDesertWell localWorldGenDesertWell = new WorldGenDesertWell();
      localWorldGenDesertWell.a(paramWorld, paramRandom, i, paramWorld.getHighestBlockYAt(i, j) + 1, j);
    }
  }
}
