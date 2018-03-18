package net.minecraft.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

class WorldGenNetherStart extends StructureStart {
  public WorldGenNetherStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    WorldGenNetherPiece15 localWorldGenNetherPiece15 = new WorldGenNetherPiece15(paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
    this.a.add(localWorldGenNetherPiece15);
    localWorldGenNetherPiece15.a(localWorldGenNetherPiece15, this.a, paramRandom);
    
    ArrayList localArrayList = localWorldGenNetherPiece15.d;
    while (!localArrayList.isEmpty()) {
      int i = paramRandom.nextInt(localArrayList.size());
      StructurePiece localStructurePiece = (StructurePiece)localArrayList.remove(i);
      localStructurePiece.a(localWorldGenNetherPiece15, this.a, paramRandom);
    }
    
    d();
    a(paramWorld, paramRandom, 48, 70);
  }
}
