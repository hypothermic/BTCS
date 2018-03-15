package net.minecraft.server;

import java.util.List;
import java.util.Random;








































































































































































































































































































































































































public class WorldGenMineshaftCross
  extends StructurePiece
{
  private final int a;
  private final boolean b;
  
  public WorldGenMineshaftCross(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2)
  {
    super(paramInt1);
    this.a = paramInt2;
    this.g = paramStructureBoundingBox;
    this.b = (paramStructureBoundingBox.c() > 3);
  }
  
  public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    StructureBoundingBox localStructureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
    
    if (paramRandom.nextInt(4) == 0) {
      localStructureBoundingBox.e += 4;
    }
    
    switch (paramInt4) {
    case 2: 
      localStructureBoundingBox.a = (paramInt1 - 1);
      localStructureBoundingBox.d = (paramInt1 + 3);
      localStructureBoundingBox.c = (paramInt3 - 4);
      break;
    case 0: 
      localStructureBoundingBox.a = (paramInt1 - 1);
      localStructureBoundingBox.d = (paramInt1 + 3);
      localStructureBoundingBox.f = (paramInt3 + 4);
      break;
    case 1: 
      localStructureBoundingBox.a = (paramInt1 - 4);
      localStructureBoundingBox.c = (paramInt3 - 1);
      localStructureBoundingBox.f = (paramInt3 + 3);
      break;
    case 3: 
      localStructureBoundingBox.d = (paramInt1 + 4);
      localStructureBoundingBox.c = (paramInt3 - 1);
      localStructureBoundingBox.f = (paramInt3 + 3);
    }
    
    
    if (StructurePiece.a(paramList, localStructureBoundingBox) != null) {
      return null;
    }
    
    return localStructureBoundingBox;
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    int i = c();
    


    switch (this.a) {
    case 2: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b, this.g.c - 1, 2, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a - 1, this.g.b, this.g.c + 1, 1, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.d + 1, this.g.b, this.g.c + 1, 3, i);
      break;
    case 0: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b, this.g.f + 1, 0, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a - 1, this.g.b, this.g.c + 1, 1, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.d + 1, this.g.b, this.g.c + 1, 3, i);
      break;
    case 1: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b, this.g.c - 1, 2, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b, this.g.f + 1, 0, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a - 1, this.g.b, this.g.c + 1, 1, i);
      break;
    case 3: 
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b, this.g.c - 1, 2, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b, this.g.f + 1, 0, i);
      WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.d + 1, this.g.b, this.g.c + 1, 3, i);
    }
    
    
    if (this.b) {
      if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b + 3 + 1, this.g.c - 1, 2, i);
      if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a - 1, this.g.b + 3 + 1, this.g.c + 1, 1, i);
      if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.d + 1, this.g.b + 3 + 1, this.g.c + 1, 3, i);
      if (paramRandom.nextBoolean()) { WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + 1, this.g.b + 3 + 1, this.g.f + 1, 0, i);
      }
    }
  }
  
  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    

    if (this.b) {
      a(paramWorld, paramStructureBoundingBox, this.g.a + 1, this.g.b, this.g.c, this.g.d - 1, this.g.b + 3 - 1, this.g.f, 0, 0, false);
      a(paramWorld, paramStructureBoundingBox, this.g.a, this.g.b, this.g.c + 1, this.g.d, this.g.b + 3 - 1, this.g.f - 1, 0, 0, false);
      a(paramWorld, paramStructureBoundingBox, this.g.a + 1, this.g.e - 2, this.g.c, this.g.d - 1, this.g.e, this.g.f, 0, 0, false);
      a(paramWorld, paramStructureBoundingBox, this.g.a, this.g.e - 2, this.g.c + 1, this.g.d, this.g.e, this.g.f - 1, 0, 0, false);
      a(paramWorld, paramStructureBoundingBox, this.g.a + 1, this.g.b + 3, this.g.c + 1, this.g.d - 1, this.g.b + 3, this.g.f - 1, 0, 0, false);
    }
    else {
      a(paramWorld, paramStructureBoundingBox, this.g.a + 1, this.g.b, this.g.c, this.g.d - 1, this.g.e, this.g.f, 0, 0, false);
      a(paramWorld, paramStructureBoundingBox, this.g.a, this.g.b, this.g.c + 1, this.g.d, this.g.e, this.g.f - 1, 0, 0, false);
    }
    

    a(paramWorld, paramStructureBoundingBox, this.g.a + 1, this.g.b, this.g.c + 1, this.g.a + 1, this.g.e, this.g.c + 1, Block.WOOD.id, 0, false);
    a(paramWorld, paramStructureBoundingBox, this.g.a + 1, this.g.b, this.g.f - 1, this.g.a + 1, this.g.e, this.g.f - 1, Block.WOOD.id, 0, false);
    a(paramWorld, paramStructureBoundingBox, this.g.d - 1, this.g.b, this.g.c + 1, this.g.d - 1, this.g.e, this.g.c + 1, Block.WOOD.id, 0, false);
    a(paramWorld, paramStructureBoundingBox, this.g.d - 1, this.g.b, this.g.f - 1, this.g.d - 1, this.g.e, this.g.f - 1, Block.WOOD.id, 0, false);
    



    for (int i = this.g.a; i <= this.g.d; i++) {
      for (int j = this.g.c; j <= this.g.f; j++) {
        int k = a(paramWorld, i, this.g.b - 1, j, paramStructureBoundingBox);
        if (k == 0) {
          a(paramWorld, Block.WOOD.id, 0, i, this.g.b - 1, j, paramStructureBoundingBox);
        }
      }
    }
    


    return true;
  }
}
