package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPortalRoom
  extends WorldGenStrongholdPiece
{
  private boolean a;
  
  public WorldGenStrongholdPortalRoom(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    if (paramStructurePiece != null) {
      ((WorldGenStrongholdStairs2)paramStructurePiece).b = this;
    }
  }
  

  public static WorldGenStrongholdPortalRoom a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 11, 8, 16, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenStrongholdPortalRoom(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 10, 7, 15, false, paramRandom, WorldGenStrongholdPieces.b());
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdDoorType.c, 4, 1, 0);
    

    int i = 6;
    a(paramWorld, paramStructureBoundingBox, 1, i, 1, 1, i, 14, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 9, i, 1, 9, i, 14, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 2, i, 1, 8, i, 2, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 2, i, 14, 8, i, 14, false, paramRandom, WorldGenStrongholdPieces.b());
    

    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 2, 1, 4, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 8, 1, 1, 9, 1, 4, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 1, 1, 3, Block.LAVA.id, Block.LAVA.id, false);
    a(paramWorld, paramStructureBoundingBox, 9, 1, 1, 9, 1, 3, Block.LAVA.id, Block.LAVA.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 3, 1, 8, 7, 1, 12, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 4, 1, 9, 6, 1, 11, Block.LAVA.id, Block.LAVA.id, false);
    

    int j; // BTCS: moved outside of for loop
    for (j = 3; j < 14; j += 2) {
      a(paramWorld, paramStructureBoundingBox, 0, 3, j, 0, 4, j, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
      a(paramWorld, paramStructureBoundingBox, 10, 3, j, 10, 4, j, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
    }
    for (j = 2; j < 9; j += 2) {
      a(paramWorld, paramStructureBoundingBox, j, 3, 15, j, 4, 15, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
    }
    

    j = c(Block.STONE_STAIRS.id, 3);
    a(paramWorld, paramStructureBoundingBox, 4, 1, 5, 6, 1, 7, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 4, 2, 6, 6, 2, 7, false, paramRandom, WorldGenStrongholdPieces.b());
    a(paramWorld, paramStructureBoundingBox, 4, 3, 7, 6, 3, 7, false, paramRandom, WorldGenStrongholdPieces.b());
    int k; // BTCS: moved outside for loop
    for (k = 4; k <= 6; k++) {
      a(paramWorld, Block.STONE_STAIRS.id, j, k, 1, 4, paramStructureBoundingBox);
      a(paramWorld, Block.STONE_STAIRS.id, j, k, 2, 5, paramStructureBoundingBox);
      a(paramWorld, Block.STONE_STAIRS.id, j, k, 3, 6, paramStructureBoundingBox);
    }
    
    k = 2;
    int m = 0;
    int n = 3;
    int i1 = 1;
    
    switch (this.h) {
    case 0: 
      k = 0;
      m = 2;
      break;
    case 3: 
      k = 3;
      m = 1;
      n = 0;
      i1 = 2;
      break;
    case 1: 
      k = 1;
      m = 3;
      n = 0;
      i1 = 2;
    }
    
    
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, k + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 4, 3, 8, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, k + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 5, 3, 8, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, k + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 6, 3, 8, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, m + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 4, 3, 12, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, m + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 5, 3, 12, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, m + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 6, 3, 12, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, n + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 3, 3, 9, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, n + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 3, 3, 10, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, n + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 3, 3, 11, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, i1 + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 7, 3, 9, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, i1 + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 7, 3, 10, paramStructureBoundingBox);
    a(paramWorld, Block.ENDER_PORTAL_FRAME.id, i1 + (paramRandom.nextFloat() > 0.9F ? 4 : 0), 7, 3, 11, paramStructureBoundingBox);
    
    if (!this.a) {
      i = b(3);
      int i2 = a(5, 6);int i3 = b(5, 6);
      if (paramStructureBoundingBox.b(i2, i, i3)) {
        this.a = true;
        paramWorld.setTypeId(i2, i, i3, Block.MOB_SPAWNER.id);
        TileEntityMobSpawner localTileEntityMobSpawner = (TileEntityMobSpawner)paramWorld.getTileEntity(i2, i, i3);
        if (localTileEntityMobSpawner != null) { localTileEntityMobSpawner.a("Silverfish");
        }
      }
    }
    return true;
  }
}
