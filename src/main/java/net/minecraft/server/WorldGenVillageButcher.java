package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageButcher extends WorldGenVillagePiece
{
  private int a = -1;
  
  public WorldGenVillageButcher(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  

  public static WorldGenVillageButcher a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 9, 7, 11, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenVillageButcher(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (this.a < 0) {
      this.a = b(paramWorld, paramStructureBoundingBox);
      if (this.a < 0) {
        return true;
      }
      this.g.a(0, this.a - this.g.e + 7 - 1, 0);
    }
    






    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 7, 4, 4, 0, 0, false);
    a(paramWorld, paramStructureBoundingBox, 2, 1, 6, 8, 4, 10, 0, 0, false);
    

    a(paramWorld, paramStructureBoundingBox, 2, 0, 6, 8, 0, 10, Block.DIRT.id, Block.DIRT.id, false);
    a(paramWorld, Block.COBBLESTONE.id, 0, 6, 0, 6, paramStructureBoundingBox);
    
    a(paramWorld, paramStructureBoundingBox, 2, 1, 6, 2, 1, 10, Block.FENCE.id, Block.FENCE.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 1, 6, 8, 1, 10, Block.FENCE.id, Block.FENCE.id, false);
    a(paramWorld, paramStructureBoundingBox, 3, 1, 10, 7, 1, 10, Block.FENCE.id, Block.FENCE.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 0, 1, 7, 0, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 0, 3, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 0, 0, 8, 3, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 7, 1, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 0, 5, 7, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 7, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 2, 5, 7, 3, 5, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 4, 1, 8, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 4, 4, 8, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 2, 8, 5, 3, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, Block.WOOD.id, 0, 0, 4, 2, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 0, 4, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 8, 4, 2, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 8, 4, 3, paramStructureBoundingBox);
    
    int i = c(Block.WOOD_STAIRS.id, 3);
    int j = c(Block.WOOD_STAIRS.id, 2);
    int m; for (int k = -1; k <= 2; k++) {
      for (m = 0; m <= 8; m++) {
        a(paramWorld, Block.WOOD_STAIRS.id, i, m, 4 + k, k, paramStructureBoundingBox);
        a(paramWorld, Block.WOOD_STAIRS.id, j, m, 4 + k, 5 - k, paramStructureBoundingBox);
      }
    }
    

    a(paramWorld, Block.LOG.id, 0, 0, 2, 1, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 0, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 8, 2, 1, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 8, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 2, 5, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 3, 2, 5, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 5, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 6, 2, 5, paramStructureBoundingBox);
    

    a(paramWorld, Block.FENCE.id, 0, 2, 1, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_PLATE.id, 0, 2, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 1, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_STAIRS.id, c(Block.WOOD_STAIRS.id, 3), 2, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_STAIRS.id, c(Block.WOOD_STAIRS.id, 1), 1, 1, 3, paramStructureBoundingBox);
    

    a(paramWorld, paramStructureBoundingBox, 5, 0, 1, 7, 0, 3, Block.DOUBLE_STEP.id, Block.DOUBLE_STEP.id, false);
    a(paramWorld, Block.DOUBLE_STEP.id, 0, 6, 1, 1, paramStructureBoundingBox);
    a(paramWorld, Block.DOUBLE_STEP.id, 0, 6, 1, 2, paramStructureBoundingBox);
    

    a(paramWorld, 0, 0, 2, 1, 0, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 2, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.TORCH.id, 0, 2, 3, 1, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 1, 0, c(Block.WOODEN_DOOR.id, 1));
    if ((a(paramWorld, 2, 0, -1, paramStructureBoundingBox) == 0) && (a(paramWorld, 2, -1, -1, paramStructureBoundingBox) != 0)) {
      a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, paramStructureBoundingBox);
    }
    

    a(paramWorld, 0, 0, 6, 1, 5, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 6, 2, 5, paramStructureBoundingBox);
    a(paramWorld, Block.TORCH.id, 0, 6, 3, 4, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 6, 1, 5, c(Block.WOODEN_DOOR.id, 1));
    
    for (int k = 0; k < 5; k++) { // BTCS: added decl 'int'
      for (m = 0; m < 9; m++) {
        b(paramWorld, m, 7, k, paramStructureBoundingBox);
        b(paramWorld, Block.COBBLESTONE.id, 0, m, -1, k, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 4, 1, 2, 2);
    
    return true;
  }
  
  protected int a(int paramInt)
  {
    if (paramInt == 0) {
      return 4;
    }
    return 0;
  }
}
