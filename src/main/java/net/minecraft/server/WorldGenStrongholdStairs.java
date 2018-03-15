package net.minecraft.server;

import java.util.List;
import java.util.Random;





















































































































































































































































































































































































































public class WorldGenStrongholdStairs
  extends WorldGenStrongholdPiece
{
  private final boolean a;
  private final WorldGenStrongholdDoorType b;
  
  public WorldGenStrongholdStairs(int paramInt1, Random paramRandom, int paramInt2, int paramInt3)
  {
    super(paramInt1);
    
    this.a = true;
    this.h = paramRandom.nextInt(4);
    this.b = WorldGenStrongholdDoorType.a;
    
    switch (this.h) {
    case 0: 
    case 2: 
      this.g = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 5 - 1, 74, paramInt3 + 5 - 1);
      break;
    default: 
      this.g = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 5 - 1, 74, paramInt3 + 5 - 1);
    }
  }
  
  public WorldGenStrongholdStairs(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.a = false;
    this.h = paramInt2;
    this.b = a(paramRandom);
    this.g = paramStructureBoundingBox;
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    if (this.a)
    {
      WorldGenStrongholdPieces.a(WorldGenStrongholdCrossing.class);
    }
    a((WorldGenStrongholdStairs2)paramStructurePiece, paramList, paramRandom, 1, 1);
  }
  
  public static WorldGenStrongholdStairs a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -7, 0, 5, 11, 5, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenStrongholdStairs(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    
    if (this.a) {}
    




    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 10, 4, true, paramRandom, WorldGenStrongholdPieces.b());
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, this.b, 1, 7, 0);
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 4);
    

    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 2, 6, 1, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 1, 5, 1, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 0, 1, 6, 1, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 1, 5, 2, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 1, 4, 3, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 0, 1, 5, 3, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 2, 4, 3, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 3, 3, 3, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 0, 3, 4, 3, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 3, 3, 2, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 3, 2, 1, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 0, 3, 3, 1, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 2, 2, 1, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 1, 1, 1, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 0, 1, 2, 1, paramStructureBoundingBox);
    a(paramWorld, Block.SMOOTH_BRICK.id, 0, 1, 1, 2, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 0, 1, 1, 3, paramStructureBoundingBox);
    
    return true;
  }
}
