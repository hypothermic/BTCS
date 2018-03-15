package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageLibrary extends WorldGenVillagePiece
{
  private int a = -1;
  
  public WorldGenVillageLibrary(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
  }
  
  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  

  public static WorldGenVillageLibrary a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 9, 9, 6, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenVillageLibrary(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (this.a < 0) {
      this.a = b(paramWorld, paramStructureBoundingBox);
      if (this.a < 0) {
        return true;
      }
      this.g.a(0, this.a - this.g.e + 9 - 1, 0);
    }
    






    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 7, 5, 4, 0, 0, false);
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 8, 0, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 8, 5, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 6, 1, 8, 6, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 7, 2, 8, 7, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    int i = c(Block.WOOD_STAIRS.id, 3);
    int j = c(Block.WOOD_STAIRS.id, 2);
    int k; // BTCS: moved outside for loop
    for (k = -1; k <= 2; k++) {
      for (int m = 0; m <= 8; m++) { // BTCS: added decl 'int'
        a(paramWorld, Block.WOOD_STAIRS.id, i, m, 6 + k, k, paramStructureBoundingBox);
        a(paramWorld, Block.WOOD_STAIRS.id, j, m, 6 + k, 5 - k, paramStructureBoundingBox);
      }
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 1, 5, 8, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 1, 0, 8, 1, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 2, 1, 0, 7, 1, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 4, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 2, 5, 0, 4, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 2, 5, 8, 4, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 2, 0, 8, 4, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 0, 2, 1, 0, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 2, 5, 7, 4, 5, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 2, 1, 8, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 7, 4, 0, Block.WOOD.id, Block.WOOD.id, false);
    

    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 5, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 6, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 5, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 6, 3, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 3, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 3, 3, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 3, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 3, 3, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 2, 5, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 3, 2, 5, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 5, 2, 5, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 6, 2, 5, paramStructureBoundingBox);
    

    a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 7, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 4, 4, 7, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 3, 4, 7, 3, 4, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
    

    a(paramWorld, Block.WOOD.id, 0, 7, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_STAIRS.id, c(Block.WOOD_STAIRS.id, 0), 7, 1, 3, paramStructureBoundingBox);
    k = c(Block.WOOD_STAIRS.id, 3);
    a(paramWorld, Block.WOOD_STAIRS.id, k, 6, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_STAIRS.id, k, 5, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_STAIRS.id, k, 4, 1, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_STAIRS.id, k, 3, 1, 4, paramStructureBoundingBox);
    

    a(paramWorld, Block.FENCE.id, 0, 6, 1, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_PLATE.id, 0, 6, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.FENCE.id, 0, 4, 1, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_PLATE.id, 0, 4, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WORKBENCH.id, 0, 7, 1, 1, paramStructureBoundingBox);
    

    a(paramWorld, 0, 0, 1, 1, 0, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 1, 2, 0, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 1, 1, 0, c(Block.WOODEN_DOOR.id, 1));
    if ((a(paramWorld, 1, 0, -1, paramStructureBoundingBox) == 0) && (a(paramWorld, 1, -1, -1, paramStructureBoundingBox) != 0)) {
      a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 1, 0, -1, paramStructureBoundingBox);
    }
    
    for (int m = 0; m < 6; m++) {
      for (int n = 0; n < 9; n++) {
        b(paramWorld, n, 9, m, paramStructureBoundingBox);
        b(paramWorld, Block.COBBLESTONE.id, 0, n, -1, m, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 1);
    

    return true;
  }
  
  protected int a(int paramInt)
  {
    return 1;
  }
}
