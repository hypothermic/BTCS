package net.minecraft.server;

import java.util.List;
import java.util.Random;





























































































































































































































































































































public class WorldGenStrongholdCorridor
  extends WorldGenStrongholdPiece
{
  private final int a;
  
  public WorldGenStrongholdCorridor(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
    this.a = ((paramInt2 == 2) || (paramInt2 == 0) ? paramStructureBoundingBox.d() : paramStructureBoundingBox.b());
  }
  



  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  


  public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 4, paramInt4);
    
    StructurePiece localStructurePiece = StructurePiece.a(paramList, localStructureBoundingBox);
    if (localStructurePiece == null)
    {

      return null;
    }
    
    if (localStructurePiece.b().b == localStructureBoundingBox.b)
    {
      for (int i = 3; i >= 1; i--) {
        localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, i - 1, paramInt4);
        if (!localStructurePiece.b().a(localStructureBoundingBox))
        {


          return StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, i, paramInt4);
        }
      }
    }
    
    return null;
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    for (int i = 0; i < this.a; i++)
    {
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 0, 0, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 1, 0, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 2, 0, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 3, 0, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 4, 0, i, paramStructureBoundingBox);
      
      for (int j = 1; j <= 3; j++) {
        a(paramWorld, Block.SMOOTH_BRICK.id, 0, 0, j, i, paramStructureBoundingBox);
        a(paramWorld, 0, 0, 1, j, i, paramStructureBoundingBox);
        a(paramWorld, 0, 0, 2, j, i, paramStructureBoundingBox);
        a(paramWorld, 0, 0, 3, j, i, paramStructureBoundingBox);
        a(paramWorld, Block.SMOOTH_BRICK.id, 0, 4, j, i, paramStructureBoundingBox);
      }
      
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 0, 4, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 1, 4, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 2, 4, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 3, 4, i, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, 4, 4, i, paramStructureBoundingBox);
    }
    
    return true;
  }
}
