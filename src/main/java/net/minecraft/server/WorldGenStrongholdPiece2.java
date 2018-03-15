package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPiece2
  extends WorldGenStrongholdPiece
{
  private static final StructurePieceTreasure[] a = { new StructurePieceTreasure(Item.ENDER_PEARL.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_SWORD.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_CHESTPLATE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_HELMET.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_LEGGINGS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_BOOTS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.GOLDEN_APPLE.id, 0, 1, 1, 1) };
  private final WorldGenStrongholdDoorType b;
  private boolean c;
  
  public WorldGenStrongholdPiece2(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    
    this.h = paramInt2;
    this.b = a(paramRandom);
    this.g = paramStructureBoundingBox;
  }
  


  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    a((WorldGenStrongholdStairs2)paramStructurePiece, paramList, paramRandom, 1, 1);
  }
  

  public static WorldGenStrongholdPiece2 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 7, paramInt4);
    
    if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
      return null;
    }
    
    return new WorldGenStrongholdPiece2(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 6, true, paramRandom, WorldGenStrongholdPieces.b());
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, this.b, 1, 1, 0);
    
    a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
    

    a(paramWorld, paramStructureBoundingBox, 3, 1, 2, 3, 1, 4, Block.SMOOTH_BRICK.id, Block.SMOOTH_BRICK.id, false);
    a(paramWorld, Block.STEP.id, 5, 3, 1, 1, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 5, 3, 1, 5, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 5, 3, 2, 2, paramStructureBoundingBox);
    a(paramWorld, Block.STEP.id, 5, 3, 2, 4, paramStructureBoundingBox);
    for (int i = 2; i <= 4; i++) {
      a(paramWorld, Block.STEP.id, 5, 2, 1, i, paramStructureBoundingBox);
    }
    
    if (!this.c) {
      i = b(2);
      int j = a(3, 3);int k = b(3, 3);
      if (paramStructureBoundingBox.b(j, i, k)) {
        this.c = true;
        a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 2, 3, a, 2 + paramRandom.nextInt(2));
      }
    }
    


    return true;
  }
}
