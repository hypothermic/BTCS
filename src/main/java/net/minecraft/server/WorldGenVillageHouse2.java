package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageHouse2
  extends WorldGenVillagePiece
{
  private int a = -1;
  
  public WorldGenVillageHouse2(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  

  public static WorldGenVillageHouse2 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 9, 7, 12, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenVillageHouse2(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
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
    

    a(paramWorld, paramStructureBoundingBox, 2, 0, 5, 8, 0, 10, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 0, 1, 7, 0, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 0, 3, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 8, 0, 0, 8, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 7, 2, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 0, 5, 2, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 2, 0, 6, 2, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, paramStructureBoundingBox, 3, 0, 10, 7, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    


    a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 7, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 1, 2, 5, 2, 3, 5, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 4, 1, 8, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 4, 4, 3, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 0, 5, 2, 8, 5, 3, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, Block.WOOD.id, 0, 0, 4, 2, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 0, 4, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 8, 4, 2, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 8, 4, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 8, 4, 4, paramStructureBoundingBox);
    
    int i = c(Block.WOOD_STAIRS.id, 3);
    int j = c(Block.WOOD_STAIRS.id, 2);
    int k; // BTCS: moved outside for loop
    for (k = -1; k <= 2; k++) {
      for (int m = 0; m <= 8; m++) { // BTCS: added decl 'int '
        a(paramWorld, Block.WOOD_STAIRS.id, i, m, 4 + k, k, paramStructureBoundingBox);
        if (((k > -1) || (m <= 1)) && ((k > 0) || (m <= 3)) && ((k > 1) || (m <= 4) || (m >= 6))) {
          a(paramWorld, Block.WOOD_STAIRS.id, j, m, 4 + k, 5 - k, paramStructureBoundingBox);
        }
      }
    }
    

    a(paramWorld, paramStructureBoundingBox, 3, 4, 5, 3, 4, 10, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 7, 4, 2, 7, 4, 10, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 4, 5, 4, 4, 5, 10, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 6, 5, 4, 6, 5, 10, Block.WOOD.id, Block.WOOD.id, false);
    a(paramWorld, paramStructureBoundingBox, 5, 6, 3, 5, 6, 10, Block.WOOD.id, Block.WOOD.id, false);
    k = c(Block.WOOD_STAIRS.id, 0);
    int m; // BTCS: moved outside for loop
    for (m = 4; m >= 1; m--) {
      a(paramWorld, Block.WOOD.id, 0, m, 2 + m, 7 - m, paramStructureBoundingBox);
      for (int n = 8 - m; n <= 10; n++) { // BTCS: added 'int' decl
        a(paramWorld, Block.WOOD_STAIRS.id, k, m, 2 + m, n, paramStructureBoundingBox);
      }
    }
    m = c(Block.WOOD_STAIRS.id, 1);
    a(paramWorld, Block.WOOD.id, 0, 6, 6, 3, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 7, 5, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD_STAIRS.id, m, 6, 6, 4, paramStructureBoundingBox);
    int i1; for (int n = 6; n <= 8; n++) {
      for (i1 = 5; i1 <= 10; i1++) {
        a(paramWorld, Block.WOOD_STAIRS.id, m, n, 12 - n, i1, paramStructureBoundingBox);
      }
    }
    

    a(paramWorld, Block.LOG.id, 0, 0, 2, 1, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 0, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 3, paramStructureBoundingBox);
    
    a(paramWorld, Block.LOG.id, 0, 4, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 5, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 6, 2, 0, paramStructureBoundingBox);
    
    a(paramWorld, Block.LOG.id, 0, 8, 2, 1, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 3, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 8, 2, 4, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 8, 2, 5, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 8, 2, 6, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 7, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 8, 2, 8, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 8, 2, 9, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 2, 2, 6, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 2, 7, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 2, 8, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 2, 2, 9, paramStructureBoundingBox);
    
    a(paramWorld, Block.LOG.id, 0, 4, 4, 10, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 5, 4, 10, paramStructureBoundingBox);
    a(paramWorld, Block.LOG.id, 0, 6, 4, 10, paramStructureBoundingBox);
    a(paramWorld, Block.WOOD.id, 0, 5, 5, 10, paramStructureBoundingBox);
    








    a(paramWorld, 0, 0, 2, 1, 0, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 2, 2, 0, paramStructureBoundingBox);
    a(paramWorld, Block.TORCH.id, 0, 2, 3, 1, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 1, 0, c(Block.WOODEN_DOOR.id, 1));
    a(paramWorld, paramStructureBoundingBox, 1, 0, -1, 3, 2, -1, 0, 0, false);
    if ((a(paramWorld, 2, 0, -1, paramStructureBoundingBox) == 0) && (a(paramWorld, 2, -1, -1, paramStructureBoundingBox) != 0)) {
      a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, paramStructureBoundingBox);
    }
    
    int n; // BTCS
    for (n = 0; n < 5; n++) {
      for (i1 = 0; i1 < 9; i1++) {
        b(paramWorld, i1, 7, n, paramStructureBoundingBox);
        b(paramWorld, Block.COBBLESTONE.id, 0, i1, -1, n, paramStructureBoundingBox);
      }
    }
    for (n = 5; n < 11; n++) {
      for (i1 = 2; i1 < 9; i1++) {
        b(paramWorld, i1, 7, n, paramStructureBoundingBox);
        b(paramWorld, Block.COBBLESTONE.id, 0, i1, -1, n, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 4, 1, 2, 2);
    

    return true;
  }
}
