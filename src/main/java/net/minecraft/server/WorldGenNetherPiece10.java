package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece10 extends WorldGenNetherPiece
{
  public WorldGenNetherPiece10(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 3, true);
  }
  

  public static WorldGenNetherPiece10 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -5, -3, 0, 13, 14, 13, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece10(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  


  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 12, 4, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 12, 13, 12, 0, 0, false);
    

    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 1, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 12, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 11, 4, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 5, 11, 10, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 5, 9, 11, 7, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 5, 9, 0, 7, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 2, 11, 2, 10, 12, 10, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 5, 8, 0, 7, 8, 0, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
    

    for (int i = 1; i <= 11; i += 2) {
      a(paramWorld, paramStructureBoundingBox, i, 10, 0, i, 11, 0, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
      a(paramWorld, paramStructureBoundingBox, i, 10, 12, i, 11, 12, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
      a(paramWorld, paramStructureBoundingBox, 0, 10, i, 0, 11, i, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
      a(paramWorld, paramStructureBoundingBox, 12, 10, i, 12, 11, i, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
      a(paramWorld, Block.NETHER_BRICK.id, 0, i, 13, 0, paramStructureBoundingBox);
      a(paramWorld, Block.NETHER_BRICK.id, 0, i, 13, 12, paramStructureBoundingBox);
      a(paramWorld, Block.NETHER_BRICK.id, 0, 0, 13, i, paramStructureBoundingBox);
      a(paramWorld, Block.NETHER_BRICK.id, 0, 12, 13, i, paramStructureBoundingBox);
      a(paramWorld, Block.NETHER_FENCE.id, 0, i + 1, 13, 0, paramStructureBoundingBox);
      a(paramWorld, Block.NETHER_FENCE.id, 0, i + 1, 13, 12, paramStructureBoundingBox);
      a(paramWorld, Block.NETHER_FENCE.id, 0, 0, 13, i + 1, paramStructureBoundingBox);
      a(paramWorld, Block.NETHER_FENCE.id, 0, 12, 13, i + 1, paramStructureBoundingBox);
    }
    a(paramWorld, Block.NETHER_FENCE.id, 0, 0, 13, 0, paramStructureBoundingBox);
    a(paramWorld, Block.NETHER_FENCE.id, 0, 0, 13, 12, paramStructureBoundingBox);
    a(paramWorld, Block.NETHER_FENCE.id, 0, 0, 13, 0, paramStructureBoundingBox);
    a(paramWorld, Block.NETHER_FENCE.id, 0, 12, 13, 0, paramStructureBoundingBox);
    

    for (i = 3; i <= 9; i += 2) {
      a(paramWorld, paramStructureBoundingBox, 1, 7, i, 1, 8, i, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
      a(paramWorld, paramStructureBoundingBox, 11, 7, i, 11, 8, i, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
    }
    

    a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 8, 2, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 12, 2, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 4, 0, 0, 8, 1, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 4, 0, 9, 8, 1, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 3, 1, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 9, 0, 4, 12, 1, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    
    int j; // BTCS
    for (i = 4; i <= 8; i++) {
      for (j = 0; j <= 2; j++) {
        b(paramWorld, Block.NETHER_BRICK.id, 0, i, -1, j, paramStructureBoundingBox);
        b(paramWorld, Block.NETHER_BRICK.id, 0, i, -1, 12 - j, paramStructureBoundingBox);
      }
    }
    for (i = 0; i <= 2; i++) {
      for (j = 4; j <= 8; j++) {
        b(paramWorld, Block.NETHER_BRICK.id, 0, i, -1, j, paramStructureBoundingBox);
        b(paramWorld, Block.NETHER_BRICK.id, 0, 12 - i, -1, j, paramStructureBoundingBox);
      }
    }
    

    a(paramWorld, paramStructureBoundingBox, 5, 5, 5, 7, 5, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    a(paramWorld, paramStructureBoundingBox, 6, 1, 6, 6, 4, 6, 0, 0, false);
    a(paramWorld, Block.NETHER_BRICK.id, 0, 6, 0, 6, paramStructureBoundingBox);
    a(paramWorld, Block.LAVA.id, 0, 6, 5, 6, paramStructureBoundingBox);
    

    i = a(6, 6);
    j = b(5);
    int k = b(6, 6);
    if (paramStructureBoundingBox.b(i, j, k)) {
      paramWorld.a = true;
      Block.byId[Block.LAVA.id].a(paramWorld, i, j, k, paramRandom);
      paramWorld.a = false;
    }
    


    return true;
  }
}
