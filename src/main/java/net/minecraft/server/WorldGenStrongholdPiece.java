package net.minecraft.server;

import java.util.List;
import java.util.Random;

abstract class WorldGenStrongholdPiece
  extends StructurePiece
{
  protected WorldGenStrongholdPiece(int paramInt)
  {
    super(paramInt);
  }

  protected void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, WorldGenStrongholdDoorType paramWorldGenStrongholdDoorType, int paramInt1, int paramInt2, int paramInt3)
  {
    switch (WorldGenStrongholdPieceWeight2.a[paramWorldGenStrongholdDoorType.ordinal()]) {
    case 1: 
    default: 
      a(paramWorld, paramStructureBoundingBox, paramInt1, paramInt2, paramInt3, paramInt1 + 3 - 1, paramInt2 + 3 - 1, paramInt3, 0, 0, false);
      break;
    case 2: 
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.WOODEN_DOOR.id, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.WOODEN_DOOR.id, 8, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      break;
    case 3: 
      a(paramWorld, 0, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, 0, 0, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_FENCE.id, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_FENCE.id, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_FENCE.id, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_FENCE.id, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_FENCE.id, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_FENCE.id, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_FENCE.id, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
      break;
    case 4: 
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.SMOOTH_BRICK.id, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_DOOR_BLOCK.id, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.IRON_DOOR_BLOCK.id, 8, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
      a(paramWorld, Block.STONE_BUTTON.id, c(Block.STONE_BUTTON.id, 4), paramInt1 + 2, paramInt2 + 1, paramInt3 + 1, paramStructureBoundingBox);
      a(paramWorld, Block.STONE_BUTTON.id, c(Block.STONE_BUTTON.id, 3), paramInt1 + 2, paramInt2 + 1, paramInt3 - 1, paramStructureBoundingBox);
    }
  }
  
  protected WorldGenStrongholdDoorType a(Random paramRandom)
  {
    int i = paramRandom.nextInt(5);
    switch (i) {
    case 0: 
    case 1: 
    default: 
      return WorldGenStrongholdDoorType.a;
    case 2: 
      return WorldGenStrongholdDoorType.b;
    case 3: 
      return WorldGenStrongholdDoorType.c;
    }
    //return WorldGenStrongholdDoorType.d; // BTCS comment: not reachable
  }
  
  protected StructurePiece a(WorldGenStrongholdStairs2 paramWorldGenStrongholdStairs2, List paramList, Random paramRandom, int paramInt1, int paramInt2)
  {
    switch (this.h) {
    case 2: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a + paramInt1, this.g.b + paramInt2, this.g.c - 1, this.h, c());
    case 0: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a + paramInt1, this.g.b + paramInt2, this.g.f + 1, this.h, c());
    case 1: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt2, this.g.c + paramInt1, this.h, c());
    case 3: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt2, this.g.c + paramInt1, this.h, c());
    }
    return null;
  }
  
  protected StructurePiece b(WorldGenStrongholdStairs2 paramWorldGenStrongholdStairs2, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
    switch (this.h) {
    case 2: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt1, this.g.c + paramInt2, 1, c());
    case 0: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt1, this.g.c + paramInt2, 1, c());
    case 1: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.c - 1, 2, c());
    case 3: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.c - 1, 2, c());
    }
    return null;
  }
  
  protected StructurePiece c(WorldGenStrongholdStairs2 paramWorldGenStrongholdStairs2, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
    switch (this.h) {
    case 2: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt1, this.g.c + paramInt2, 3, c());
    case 0: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt1, this.g.c + paramInt2, 3, c());
    case 1: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.f + 1, 0, c());
    case 3: 
      return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStairs2, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.f + 1, 0, c());
    }
    return null;
  }
  
  protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
    return (paramStructureBoundingBox != null) && (paramStructureBoundingBox.b > 10);
  }
}
