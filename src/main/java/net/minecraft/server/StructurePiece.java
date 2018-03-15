package net.minecraft.server;

import java.util.List;
import java.util.Random;
public abstract class StructurePiece
{
  protected StructureBoundingBox g;
  protected int h;
  protected int i;
  
  protected StructurePiece(int paramInt)
  {
    this.i = paramInt;
    this.h = -1;
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {}
  
  public abstract boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
  
  public StructureBoundingBox b()
  {
    return this.g;
  }
  
  public int c() {
    return this.i;
  }
  
  public static StructurePiece a(List paramList, StructureBoundingBox paramStructureBoundingBox)
  {
    for (StructurePiece localStructurePiece : (StructurePiece[]) paramList.toArray()) {  // BTCS: added cast and .toArray()
      if ((localStructurePiece.b() != null) && (localStructurePiece.b().a(paramStructureBoundingBox))) {
        return localStructurePiece;
      }
    }
    return null;
  }
  
  public ChunkPosition b_() {
    return new ChunkPosition(this.g.e(), this.g.f(), this.g.g());
  }
  
  protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox)
  {
    int j = Math.max(this.g.a - 1, paramStructureBoundingBox.a);
    int k = Math.max(this.g.b - 1, paramStructureBoundingBox.b);
    int m = Math.max(this.g.c - 1, paramStructureBoundingBox.c);
    int n = Math.min(this.g.d + 1, paramStructureBoundingBox.d);
    int i1 = Math.min(this.g.e + 1, paramStructureBoundingBox.e);
    int i2 = Math.min(this.g.f + 1, paramStructureBoundingBox.f);
    int i4;
    int i5;
    for (int i3 = j; i3 <= n; i3++) {
      for (i4 = m; i4 <= i2; i4++) {
        i5 = paramWorld.getTypeId(i3, k, i4);
        if ((i5 > 0) && (Block.byId[i5].material.isLiquid())) {
          return true;
        }
        i5 = paramWorld.getTypeId(i3, i1, i4);
        if ((i5 > 0) && (Block.byId[i5].material.isLiquid())) {
          return true;
        }
      }
    }
    
    for (int i3 = j; i3 <= n; i3++) { // BTCS: added decl 'int '
      for (i4 = k; i4 <= i1; i4++) {
        i5 = paramWorld.getTypeId(i3, i4, m);
        if ((i5 > 0) && (Block.byId[i5].material.isLiquid())) {
          return true;
        }
        i5 = paramWorld.getTypeId(i3, i4, i2);
        if ((i5 > 0) && (Block.byId[i5].material.isLiquid())) {
          return true;
        }
      }
    }
    
    for (int i3 = m; i3 <= i2; i3++) { // BTCS: added decl
      for (i4 = k; i4 <= i1; i4++) {
        i5 = paramWorld.getTypeId(j, i4, i3);
        if ((i5 > 0) && (Block.byId[i5].material.isLiquid())) {
          return true;
        }
        i5 = paramWorld.getTypeId(n, i4, i3);
        if ((i5 > 0) && (Block.byId[i5].material.isLiquid())) {
          return true;
        }
      }
    }
    return false;
  }
  
  protected int a(int paramInt1, int paramInt2) {
    switch (this.h) {
    case 0: 
    case 2: 
      return this.g.a + paramInt1;
    case 1: 
      return this.g.d - paramInt2;
    case 3: 
      return this.g.a + paramInt2;
    }
    return paramInt1;
  }
  
  protected int b(int paramInt)
  {
    if (this.h == -1) {
      return paramInt;
    }
    return paramInt + this.g.b;
  }
  
  protected int b(int paramInt1, int paramInt2) {
    switch (this.h) {
    case 2: 
      return this.g.f - paramInt2;
    case 0: 
      return this.g.c + paramInt2;
    case 1: 
    case 3: 
      return this.g.c + paramInt1;
    }
    return paramInt2;
  }
  
  protected int c(int paramInt1, int paramInt2)
  {
    if (paramInt1 == Block.RAILS.id) {
      if ((this.h == 1) || (this.h == 3)) {
        if (paramInt2 == 1) {
          return 0;
        }
        return 1;
      }
    }
    else if ((paramInt1 == Block.WOODEN_DOOR.id) || (paramInt1 == Block.IRON_DOOR_BLOCK.id)) {
      if (this.h == 0) {
        if (paramInt2 == 0) {
          return 2;
        }
        if (paramInt2 == 2)
          return 0;
      } else {
        if (this.h == 1)
        {



          return paramInt2 + 1 & 0x3; }
        if (this.h == 3)
        {



          return paramInt2 + 3 & 0x3; }
      }
    } else if ((paramInt1 == Block.COBBLESTONE_STAIRS.id) || (paramInt1 == Block.WOOD_STAIRS.id) || (paramInt1 == Block.NETHER_BRICK_STAIRS.id) || (paramInt1 == Block.STONE_STAIRS.id)) {
      if (this.h == 0) {
        if (paramInt2 == 2) {
          return 3;
        }
        if (paramInt2 == 3) {
          return 2;
        }
      } else if (this.h == 1) {
        if (paramInt2 == 0) {
          return 2;
        }
        if (paramInt2 == 1) {
          return 3;
        }
        if (paramInt2 == 2) {
          return 0;
        }
        if (paramInt2 == 3) {
          return 1;
        }
      } else if (this.h == 3) {
        if (paramInt2 == 0) {
          return 2;
        }
        if (paramInt2 == 1) {
          return 3;
        }
        if (paramInt2 == 2) {
          return 1;
        }
        if (paramInt2 == 3) {
          return 0;
        }
      }
    } else if (paramInt1 == Block.LADDER.id) {
      if (this.h == 0) {
        if (paramInt2 == 2) {
          return 3;
        }
        if (paramInt2 == 3) {
          return 2;
        }
      } else if (this.h == 1) {
        if (paramInt2 == 2) {
          return 4;
        }
        if (paramInt2 == 3) {
          return 5;
        }
        if (paramInt2 == 4) {
          return 2;
        }
        if (paramInt2 == 5) {
          return 3;
        }
      } else if (this.h == 3) {
        if (paramInt2 == 2) {
          return 5;
        }
        if (paramInt2 == 3) {
          return 4;
        }
        if (paramInt2 == 4) {
          return 2;
        }
        if (paramInt2 == 5) {
          return 3;
        }
      }
    }
    else if (paramInt1 == Block.STONE_BUTTON.id) {
      if (this.h == 0) {
        if (paramInt2 == 3) {
          return 4;
        }
        if (paramInt2 == 4) {
          return 3;
        }
      } else if (this.h == 1) {
        if (paramInt2 == 3) {
          return 1;
        }
        if (paramInt2 == 4) {
          return 2;
        }
        if (paramInt2 == 2) {
          return 3;
        }
        if (paramInt2 == 1) {
          return 4;
        }
      } else if (this.h == 3) {
        if (paramInt2 == 3) {
          return 2;
        }
        if (paramInt2 == 4) {
          return 1;
        }
        if (paramInt2 == 2) {
          return 3;
        }
        if (paramInt2 == 1) {
          return 4;
        }
      }
    }
    return paramInt2;
  }
  
  protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, StructureBoundingBox paramStructureBoundingBox)
  {
    int j = a(paramInt3, paramInt5);
    int k = b(paramInt4);
    int m = b(paramInt3, paramInt5);
    
    if (!paramStructureBoundingBox.b(j, k, m)) {
      return;
    }
    
    paramWorld.setRawTypeIdAndData(j, k, m, paramInt1, paramInt2);
  }
  












  protected int a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox)
  {
    int j = a(paramInt1, paramInt3);
    int k = b(paramInt2);
    int m = b(paramInt1, paramInt3);
    
    if (!paramStructureBoundingBox.b(j, k, m)) {
      return 0;
    }
    
    return paramWorld.getTypeId(j, k, m);
  }
  
  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    for (int j = paramInt2; j <= paramInt5; j++) {
      for (int k = paramInt1; k <= paramInt4; k++) {
        for (int m = paramInt3; m <= paramInt6; m++)
        {
          if ((!paramBoolean) || (a(paramWorld, k, j, m, paramStructureBoundingBox) != 0))
          {

            if ((j == paramInt2) || (j == paramInt5) || (k == paramInt1) || (k == paramInt4) || (m == paramInt3) || (m == paramInt6)) {
              a(paramWorld, paramInt7, 0, k, j, m, paramStructureBoundingBox);
            } else {
              a(paramWorld, paramInt8, 0, k, j, m, paramStructureBoundingBox);
            }
          }
        }
      }
    }
  }
  




  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, Random paramRandom, StructurePieceBlockSelector paramStructurePieceBlockSelector)
  {
    for (int j = paramInt2; j <= paramInt5; j++) {
      for (int k = paramInt1; k <= paramInt4; k++) {
        for (int m = paramInt3; m <= paramInt6; m++)
        {
          if ((!paramBoolean) || (a(paramWorld, k, j, m, paramStructureBoundingBox) != 0))
          {

            paramStructurePieceBlockSelector.a(paramRandom, k, j, m, (j == paramInt2) || (j == paramInt5) || (k == paramInt1) || (k == paramInt4) || (m == paramInt3) || (m == paramInt6));
            a(paramWorld, paramStructurePieceBlockSelector.a(), paramStructurePieceBlockSelector.b(), k, j, m, paramStructureBoundingBox);
          }
        }
      }
    }
  }
  




  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    for (int j = paramInt2; j <= paramInt5; j++) {
      for (int k = paramInt1; k <= paramInt4; k++) {
        for (int m = paramInt3; m <= paramInt6; m++)
        {
          if (paramRandom.nextFloat() <= paramFloat)
          {

            if ((!paramBoolean) || (a(paramWorld, k, j, m, paramStructureBoundingBox) != 0))
            {

              if ((j == paramInt2) || (j == paramInt5) || (k == paramInt1) || (k == paramInt4) || (m == paramInt3) || (m == paramInt6)) {
                a(paramWorld, paramInt7, 0, k, j, m, paramStructureBoundingBox);
              } else {
                a(paramWorld, paramInt8, 0, k, j, m, paramStructureBoundingBox);
              }
            }
          }
        }
      }
    }
  }
  
  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramRandom.nextFloat() < paramFloat) {
      a(paramWorld, paramInt4, paramInt5, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
    }
  }
  

  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean)
  {
    float f1 = paramInt4 - paramInt1 + 1;
    float f2 = paramInt5 - paramInt2 + 1;
    float f3 = paramInt6 - paramInt3 + 1;
    float f4 = paramInt1 + f1 / 2.0F;
    float f5 = paramInt3 + f3 / 2.0F;
    
    for (int j = paramInt2; j <= paramInt5; j++) {
      float f6 = (j - paramInt2) / f2;
      
      for (int k = paramInt1; k <= paramInt4; k++) {
        float f7 = (k - f4) / (f1 * 0.5F);
        
        for (int m = paramInt3; m <= paramInt6; m++) {
          float f8 = (m - f5) / (f3 * 0.5F);
          
          if ((!paramBoolean) || (a(paramWorld, k, j, m, paramStructureBoundingBox) != 0))
          {


            float f9 = f7 * f7 + f6 * f6 + f8 * f8;
            
            if (f9 <= 1.05F) {
              a(paramWorld, paramInt7, 0, k, j, m, paramStructureBoundingBox);
            }
          }
        }
      }
    }
  }
  
  protected void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox)
  {
    int j = a(paramInt1, paramInt3);
    int k = b(paramInt2);
    int m = b(paramInt1, paramInt3);
    
    if (!paramStructureBoundingBox.b(j, k, m)) {
      return;
    }
    
    while ((!paramWorld.isEmpty(j, k, m)) && (k < 255)) {
      paramWorld.setRawTypeIdAndData(j, k, m, 0, 0);
      k++;
    }
  }
  
  protected void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, StructureBoundingBox paramStructureBoundingBox)
  {
    int j = a(paramInt3, paramInt5);
    int k = b(paramInt4);
    int m = b(paramInt3, paramInt5);
    
    if (!paramStructureBoundingBox.b(j, k, m)) {
      return;
    }
    
    while (((paramWorld.isEmpty(j, k, m)) || (paramWorld.getMaterial(j, k, m).isLiquid())) && (k > 1)) {
      paramWorld.setRawTypeIdAndData(j, k, m, paramInt1, paramInt2);
      k--;
    }
  }
  

  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, int paramInt4)
  {
    int j = a(paramInt1, paramInt3);
    int k = b(paramInt2);
    int m = b(paramInt1, paramInt3);
    
    if ((paramStructureBoundingBox.b(j, k, m)) && 
      (paramWorld.getTypeId(j, k, m) != Block.CHEST.id)) {
      paramWorld.setTypeId(j, k, m, Block.CHEST.id);
      TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(j, k, m);
      if (localTileEntityChest != null) { a(paramRandom, paramArrayOfStructurePieceTreasure, localTileEntityChest, paramInt4);
      }
    }
  }
  

  private static void a(Random paramRandom, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, TileEntityChest paramTileEntityChest, int paramInt)
  {
    for (int j = 0; j < paramInt; j++) {
      StructurePieceTreasure localStructurePieceTreasure = (StructurePieceTreasure)WeightedRandom.a(paramRandom, paramArrayOfStructurePieceTreasure);
      
      int k = localStructurePieceTreasure.c + paramRandom.nextInt(localStructurePieceTreasure.e - localStructurePieceTreasure.c + 1);
      if (Item.byId[localStructurePieceTreasure.a].getMaxStackSize() >= k) {
        paramTileEntityChest.setItem(paramRandom.nextInt(paramTileEntityChest.getSize()), new ItemStack(localStructurePieceTreasure.a, k, localStructurePieceTreasure.b));
      }
      else {
        for (int m = 0; m < k; m++) {
          paramTileEntityChest.setItem(paramRandom.nextInt(paramTileEntityChest.getSize()), new ItemStack(localStructurePieceTreasure.a, 1, localStructurePieceTreasure.b));
        }
      }
    }
  }
  
  protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = a(paramInt1, paramInt3);
    int k = b(paramInt2);
    int m = b(paramInt1, paramInt3);
    
    if (paramStructureBoundingBox.b(j, k, m)) {
      ItemDoor.place(paramWorld, j, k, m, paramInt4, Block.WOODEN_DOOR);
    }
  }
}
