package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdCrossing extends WorldGenStrongholdPiece
{
  protected final WorldGenStrongholdDoorType a;
  private boolean b;
  private boolean c;
  private boolean d;
  private boolean e;
  
  public WorldGenStrongholdCrossing(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.h = paramInt2;
    this.a = a(paramRandom);
    this.g = paramStructureBoundingBox;
    
    this.b = paramRandom.nextBoolean();
    this.c = paramRandom.nextBoolean();
    this.d = paramRandom.nextBoolean();
    this.e = (paramRandom.nextInt(3) > 0);
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    int i = 3;
    int j = 5;
    
    if ((this.h == 1) || (this.h == 2)) {
      i = 8 - i;
      j = 8 - j;
    }
    
    a((WorldGenStrongholdStairs2)paramStructurePiece, paramList, paramRandom, 5, 1);
    if (this.b) b((WorldGenStrongholdStairs2)paramStructurePiece, paramList, paramRandom, i, 1);
    if (this.c) b((WorldGenStrongholdStairs2)paramStructurePiece, paramList, paramRandom, j, 7);
    if (this.d) c((WorldGenStrongholdStairs2)paramStructurePiece, paramList, paramRandom, i, 1);
    if (this.e) { c((WorldGenStrongholdStairs2)paramStructurePiece, paramList, paramRandom, j, 7);
    }
  }
  
  public static WorldGenStrongholdCrossing a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -3, 0, 10, 9, 11, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenStrongholdCrossing(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 9, 8, 10, true, paramRandom, WorldGenStrongholdPieces.b());
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, this.a, 4, 3, 0);
    

    if (this.b) a(paramWorld, paramStructureBoundingBox, 0, 3, 1, 0, 5, 3, 0, 0, false);
    if (this.d) a(paramWorld, paramStructureBoundingBox, 9, 3, 1, 9, 5, 3, 0, 0, false);
    if (this.c) a(paramWorld, paramStructureBoundingBox, 0, 5, 7, 0, 7, 9, 0, 0, false);
    if (this.e) a(paramWorld, paramStructureBoundingBox, 9, 5, 7, 9, 7, 9, 0, 0, false);
    a(paramWorld, paramStructureBoundingBox, 5, 1, 10, 7, 3, 10, 0, 0, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 2, 1, 8, 2, 6, false, paramRandom, WorldGenStrongholdPieces.b());
    
    a(paramWorld, paramStructureBoundingBox, 4, 1, 5, 4, 4, 9, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 8, 1, 5, 8, 4, 9, false, paramRandom, WorldGenStrongholdPieces.b());
    
    a(paramWorld, paramStructureBoundingBox, 1, 4, 7, 3, 4, 9, false, paramRandom, WorldGenStrongholdPieces.b());
    

    a(paramWorld, paramStructureBoundingBox, 1, 3, 5, 3, 3, 6, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 1, 3, 4, 3, 3, 4, Block.STEP.id, Block.STEP.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 4, 6, 3, 4, 6, Block.STEP.id, Block.STEP.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 5, 1, 7, 7, 1, 8, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 5, 1, 9, 7, 1, 9, Block.STEP.id, Block.STEP.id, false);
    a(paramWorld, paramStructureBoundingBox, 5, 2, 7, 7, 2, 7, Block.STEP.id, Block.STEP.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 4, 5, 7, 4, 5, 9, Block.STEP.id, Block.STEP.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 5, 7, 8, 5, 9, Block.STEP.id, Block.STEP.id, false);
    a(paramWorld, paramStructureBoundingBox, 5, 5, 7, 7, 5, 9, Block.DOUBLE_STEP.id, Block.DOUBLE_STEP.id, false);
    a(paramWorld, Block.TORCH.id, 0, 6, 5, 6, paramStructureBoundingBox);
    
    return true;
  }
}
