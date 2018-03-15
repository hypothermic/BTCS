package net.minecraft.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
























































































class WorldGenStrongholdStart
  extends StructureStart
{
  public WorldGenStrongholdStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    WorldGenStrongholdPieces.a();
    
    WorldGenStrongholdStairs2 localWorldGenStrongholdStairs2 = new WorldGenStrongholdStairs2(0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
    this.a.add(localWorldGenStrongholdStairs2);
    localWorldGenStrongholdStairs2.a(localWorldGenStrongholdStairs2, this.a, paramRandom);
    
    ArrayList localArrayList = localWorldGenStrongholdStairs2.c;
    while (!localArrayList.isEmpty()) {
      int i = paramRandom.nextInt(localArrayList.size());
      StructurePiece localStructurePiece = (StructurePiece)localArrayList.remove(i);
      localStructurePiece.a(localWorldGenStrongholdStairs2, this.a, paramRandom);
    }
    
    d();
    a(paramWorld, paramRandom, 10);
  }
}
