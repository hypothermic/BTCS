package net.minecraft.server;

import java.util.List;
import java.util.Random;






























































































































































































































































































































































































































































































































































































public class WorldGenVillageHouse
  extends WorldGenVillagePiece
{
  private int a = -1;
  private final boolean b;
  
  public WorldGenVillageHouse(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
    this.b = paramRandom.nextBoolean();
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  

  public static WorldGenVillageHouse a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 5, 6, 5, paramInt4);
    
    if (StructurePiece.a(paramList, localStructureBoundingBox) != null) {
      return null;
    }
    
    return new WorldGenVillageHouse(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (this.a < 0) {
      this.a = b(paramWorld, paramStructureBoundingBox);
      if (this.a < 0) {
        return true;
      }
      this.g.a(0, this.a - this.g.e + 6 - 1, 0);
    }
    





    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 0, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 4, 4, 4, Block.LOG.id, Block.LOG.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 3, 4, 3, Block.WOOD.id, Block.WOOD.id, false);
    

    a(paramWorld, Block.COBBLESTONE.id, 0, 0, 1, 0, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 0, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 0, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 4, 1, 0, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 4, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 4, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 0, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 0, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 0, 3, 4, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 4, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 4, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 4, 3, 4, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 1, 4, 3, 3, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 2, 2, paramStructureBoundingBox);
    

    a(paramWorld, Block.WOOD.id, 0, 1, 1, 0, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 1, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 1, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 2, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 3, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 3, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 3, 1, 0, paramStructureBoundingBox);
    if ((a(paramWorld, 2, 0, -1, paramStructureBoundingBox) == 0) && (a(paramWorld, 2, -1, -1, paramStructureBoundingBox) != 0)) {
      a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, paramStructureBoundingBox);
    }
    

    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 3, 3, 3, 0, 0, false);
    

    if (this.b) {
      a(paramWorld, Block.FENCE.id, 0, 0, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 1, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 2, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 3, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 4, 5, 0, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 0, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 1, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 2, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 3, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 4, 5, 4, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 4, 5, 1, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 4, 5, 2, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 4, 5, 3, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 0, 5, 1, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 0, 5, 2, paramStructureBoundingBox);
      a(paramWorld, Block.FENCE.id, 0, 0, 5, 3, paramStructureBoundingBox);
    }
    

    if (this.b) {
      i = c(Block.LADDER.id, 3);
      a(paramWorld, Block.LADDER.id, i, 3, 1, 3, paramStructureBoundingBox);
      a(paramWorld, Block.LADDER.id, i, 3, 2, 3, paramStructureBoundingBox);
      a(paramWorld, Block.LADDER.id, i, 3, 3, 3, paramStructureBoundingBox);
      a(paramWorld, Block.LADDER.id, i, 3, 4, 3, paramStructureBoundingBox);
    }
    

    a(paramWorld, Block.TORCH.id, 0, 2, 3, 1, paramStructureBoundingBox);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        b(paramWorld, j, 6, i, paramStructureBoundingBox);
        b(paramWorld, Block.COBBLESTONE.id, 0, j, -1, i, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 1, 1, 2, 1);
    
    return true;
  }
}
