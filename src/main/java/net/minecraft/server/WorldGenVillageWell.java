package net.minecraft.server;

import java.util.List;
import java.util.Random;


























































































































































































































































































































public class WorldGenVillageWell
  extends WorldGenVillagePiece
{
  private final boolean a;
  private int b = -1;
  
  public WorldGenVillageWell(int paramInt1, Random paramRandom, int paramInt2, int paramInt3) {
    super(paramInt1);
    
    this.a = true;
    this.h = paramRandom.nextInt(4);
    
    switch (this.h) {
    case 0: 
    case 2: 
      this.g = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 6 - 1, 78, paramInt3 + 6 - 1);
      break;
    default: 
      this.g = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 6 - 1, 78, paramInt3 + 6 - 1);
    }
    
  }
  











  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.g.a - 1, this.g.e - 4, this.g.c + 1, 1, c());
    WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.g.d + 1, this.g.e - 4, this.g.c + 1, 3, c());
    WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.e - 4, this.g.c - 1, 2, c());
    WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.e - 4, this.g.f + 1, 0, c());
  }
  


















  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (this.b < 0) {
      this.b = b(paramWorld, paramStructureBoundingBox);
      if (this.b < 0) {
        return true;
      }
      this.g.a(0, this.b - this.g.e + 3, 0);
    }
    




    if (this.a) {}
    


    a(paramWorld, paramStructureBoundingBox, 1, 0, 1, 4, 12, 4, Block.COBBLESTONE.id, Block.WATER.id, false);
    a(paramWorld, 0, 0, 2, 12, 2, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 3, 12, 2, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 2, 12, 3, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 3, 12, 3, paramStructureBoundingBox);
    
    a(paramWorld, Block.FENCE.id, 0, 1, 13, 1, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 1, 14, 1, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 4, 13, 1, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 4, 14, 1, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 1, 13, 4, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 1, 14, 4, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 4, 13, 4, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 4, 14, 4, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, 1, 15, 1, 4, 15, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    for (int i = 0; i <= 5; i++) {
      for (int j = 0; j <= 5; j++)
      {
        if ((j == 0) || (j == 5) || (i == 0) || (i == 5))
        {

          a(paramWorld, Block.GRAVEL.id, 0, j, 11, i, paramStructureBoundingBox);
          b(paramWorld, j, 12, i, paramStructureBoundingBox);
        }
      }
    }
    return true;
  }
}
