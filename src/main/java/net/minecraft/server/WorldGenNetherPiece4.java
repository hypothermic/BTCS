package net.minecraft.server;

import java.util.List;
import java.util.Random;

















































































































































































































































































































































































public class WorldGenNetherPiece4
  extends WorldGenNetherPiece
{
  private int a;
  
  public WorldGenNetherPiece4(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
    this.a = paramRandom.nextInt();
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  


  public static WorldGenNetherPiece4 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -3, 0, 5, 10, 8, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenNetherPiece4(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    Random localRandom = new Random(this.a);
    int j;
    int k;
    for (int i = 0; i <= 4; i++) {
      for (j = 3; j <= 4; j++) {
        k = localRandom.nextInt(8);
        a(paramWorld, paramStructureBoundingBox, i, j, 0, i, j, k, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
      }
    }
    


    i = localRandom.nextInt(8);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 0, 5, i, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    

    i = localRandom.nextInt(8);
    a(paramWorld, paramStructureBoundingBox, 4, 5, 0, 4, 5, i, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    


    for (i = 0; i <= 4; i++) {
      j = localRandom.nextInt(5);
      a(paramWorld, paramStructureBoundingBox, i, 2, 0, i, 2, j, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
    }
    for (i = 0; i <= 4; i++) {
      for (j = 0; j <= 1; j++) {
        k = localRandom.nextInt(3);
        a(paramWorld, paramStructureBoundingBox, i, j, 0, i, j, k, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
      }
    }
    

    return true;
  }
}
