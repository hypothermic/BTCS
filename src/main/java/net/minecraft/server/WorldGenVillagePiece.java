package net.minecraft.server;

import java.util.List;
import java.util.Random;

abstract class WorldGenVillagePiece extends StructurePiece
{
  private int a;
  
  protected WorldGenVillagePiece(int paramInt)
  {
    super(paramInt);
  }
  
  protected StructurePiece a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
    switch (this.h) {
    case 2: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt1, this.g.c + paramInt2, 1, c());
    case 0: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt1, this.g.c + paramInt2, 1, c());
    case 1: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.c - 1, 2, c());
    case 3: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.c - 1, 2, c());
    }
    return null;
  }
  
  protected StructurePiece b(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
    switch (this.h) {
    case 2: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt1, this.g.c + paramInt2, 3, c());
    case 0: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt1, this.g.c + paramInt2, 3, c());
    case 1: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.f + 1, 0, c());
    case 3: 
      return WorldGenVillagePieces.a(paramWorldGenVillageStartPiece, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.f + 1, 0, c());
    }
    return null;
  }
  
  protected int b(World paramWorld, StructureBoundingBox paramStructureBoundingBox)
  {
    int i = 0;
    int j = 0;
    for (int k = this.g.c; k <= this.g.f; k++) {
      for (int m = this.g.a; m <= this.g.d; m++) {
        if (paramStructureBoundingBox.b(m, 64, k)) {
          i += Math.max(paramWorld.g(m, k), paramWorld.worldProvider.getSeaLevel());
          j++;
        }
      }
    }
    
    if (j == 0) {
      return -1;
    }
    return i / j;
  }
  
  protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
    return (paramStructureBoundingBox != null) && (paramStructureBoundingBox.b > 10);
  }

  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.a >= paramInt4) {
      return;
    }
    
    for (int i = this.a; i < paramInt4; i++) {
      int j = a(paramInt1 + i, paramInt3);
      int k = b(paramInt2);
      int m = b(paramInt1 + i, paramInt3);
      
      if (!paramStructureBoundingBox.b(j, k, m)) break;
      this.a += 1;
      
      EntityVillager localEntityVillager = new EntityVillager(paramWorld, a(i));
      localEntityVillager.setPositionRotation(j + 0.5D, k, m + 0.5D, 0.0F, 0.0F);
      paramWorld.addEntity(localEntityVillager);
    }
  }

  protected int a(int paramInt)
  {
    return 0;
  }
}
