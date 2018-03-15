package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageTemple extends WorldGenVillagePiece
{
  private int a = -1;
  
  public WorldGenVillageTemple(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
    super(paramInt1);
    
    this.h = paramInt2;
    this.g = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  

  public static WorldGenVillageTemple a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 5, 12, 9, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenVillageTemple(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (this.a < 0) {
      this.a = b(paramWorld, paramStructureBoundingBox);
      if (this.a < 0) {
        return true;
      }
      this.g.a(0, this.a - this.g.e + 12 - 1, 0);
    }
    






    a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 3, 3, 7, 0, 0, false);
    a(paramWorld, paramStructureBoundingBox, 1, 5, 1, 3, 9, 3, 0, 0, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 3, 0, 8, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 3, 10, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 10, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 10, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 0, 4, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 4, 0, 4, 4, 4, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 1, 1, 8, 3, 4, 8, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 1, 5, 4, 3, 10, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    

    a(paramWorld, paramStructureBoundingBox, 1, 5, 5, 3, 5, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 9, 0, 4, 9, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    
    a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 4, 4, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
    a(paramWorld, Block.COBBLESTONE.id, 0, 0, 11, 2, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 4, 11, 2, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 2, 11, 0, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 2, 11, 4, paramStructureBoundingBox);
    

    a(paramWorld, Block.COBBLESTONE.id, 0, 1, 1, 6, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 1, 1, 7, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 2, 1, 7, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 3, 1, 6, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE.id, 0, 3, 1, 7, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 1, 1, 5, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 2, 1, 6, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 3, 1, 5, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 1), 1, 2, 7, paramStructureBoundingBox);
    a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 0), 3, 2, 7, paramStructureBoundingBox);
    

    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 3, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 3, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 6, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 7, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 6, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 7, 2, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 6, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 7, 0, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 6, 4, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 7, 4, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 0, 3, 6, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 4, 3, 6, paramStructureBoundingBox);
    a(paramWorld, Block.THIN_GLASS.id, 0, 2, 3, 8, paramStructureBoundingBox);
    

    a(paramWorld, Block.TORCH.id, 0, 2, 4, 7, paramStructureBoundingBox);
    a(paramWorld, Block.TORCH.id, 0, 1, 4, 6, paramStructureBoundingBox);
    a(paramWorld, Block.TORCH.id, 0, 3, 4, 6, paramStructureBoundingBox);
    a(paramWorld, Block.TORCH.id, 0, 2, 4, 5, paramStructureBoundingBox);
    

    int i = c(Block.LADDER.id, 4);
    int j; // BTCS: moved outside for loop
    for (j = 1; j <= 9; j++) {
      a(paramWorld, Block.LADDER.id, i, 3, j, 3, paramStructureBoundingBox);
    }
    

    a(paramWorld, 0, 0, 2, 1, 0, paramStructureBoundingBox);
    a(paramWorld, 0, 0, 2, 2, 0, paramStructureBoundingBox);
    a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 1, 0, c(Block.WOODEN_DOOR.id, 1));
    if ((a(paramWorld, 2, 0, -1, paramStructureBoundingBox) == 0) && (a(paramWorld, 2, -1, -1, paramStructureBoundingBox) != 0)) {
      a(paramWorld, Block.COBBLESTONE_STAIRS.id, c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, paramStructureBoundingBox);
    }
    

    for (j = 0; j < 9; j++) {
      for (int k = 0; k < 5; k++) {
        b(paramWorld, k, 12, j, paramStructureBoundingBox);
        b(paramWorld, Block.COBBLESTONE.id, 0, k, -1, j, paramStructureBoundingBox);
      }
    }
    
    a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 1);
    
    return true;
  }
  
  protected int a(int paramInt)
  {
    return 2;
  }
}
