package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class WorldGenNetherPiece extends StructurePiece
{
  protected WorldGenNetherPiece(int paramInt)
  {
    super(paramInt);
  }
  
  private int a(List paramList) {
    int i = 0;
    int j = 0;
    for (WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : (WorldGenNetherPieceWeight[]) paramList.toArray()) { // BTCS: added cast and .toArray()
      if ((localWorldGenNetherPieceWeight.d > 0) && (localWorldGenNetherPieceWeight.c < localWorldGenNetherPieceWeight.d)) {
        i = 1;
      }
      j += localWorldGenNetherPieceWeight.b;
    }
    return i != 0 ? j : -1;
  }
  

  private WorldGenNetherPiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList1, List paramList2, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i = a(paramList1);
    int j = (i > 0) && (paramInt5 <= 30) ? 1 : 0;
    
    int k = 0;
    int m; while ((k < 5) && (j != 0)) {
      k++;
      
      m = paramRandom.nextInt(i);
      for (WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : (WorldGenNetherPieceWeight[])paramList1.toArray()) {  // BTCS: added cast and .toArray()
        m -= localWorldGenNetherPieceWeight.b;
        if (m < 0)
        {
          if ((!localWorldGenNetherPieceWeight.a(paramInt5)) || ((localWorldGenNetherPieceWeight == paramWorldGenNetherPiece15.a) && (!localWorldGenNetherPieceWeight.e))) {
            break;
          }
          
          WorldGenNetherPiece localWorldGenNetherPiece = WorldGenNetherPieces.a(localWorldGenNetherPieceWeight, paramList2, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
          if (localWorldGenNetherPiece != null) {
            localWorldGenNetherPieceWeight.c += 1;
            paramWorldGenNetherPiece15.a = localWorldGenNetherPieceWeight;
            
            if (!localWorldGenNetherPieceWeight.a()) {
              paramList1.remove(localWorldGenNetherPieceWeight);
            }
            return localWorldGenNetherPiece;
          }
        }
      }
    }
    
    WorldGenNetherPiece4 localWorldGenNetherPiece4 = WorldGenNetherPiece4.a(paramList2, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    return localWorldGenNetherPiece4;
  }
  
  private StructurePiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    if ((Math.abs(paramInt1 - paramWorldGenNetherPiece15.b().a) > 112) || (Math.abs(paramInt3 - paramWorldGenNetherPiece15.b().c) > 112)) {
      Object localObject = WorldGenNetherPiece4.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5); // BTCS: added decl 'Object '
      return (StructurePiece)localObject;
    }
    Object localObject = paramWorldGenNetherPiece15.b;
    if (paramBoolean) {
      localObject = paramWorldGenNetherPiece15.c;
    }
    WorldGenNetherPiece localWorldGenNetherPiece = a(paramWorldGenNetherPiece15, (List)localObject, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5 + 1);
    if (localWorldGenNetherPiece != null) {
      paramList.add(localWorldGenNetherPiece);
      paramWorldGenNetherPiece15.d.add(localWorldGenNetherPiece);
    }
    return localWorldGenNetherPiece;
  }
  
  protected StructurePiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
    switch (this.h) {
    case 2: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a + paramInt1, this.g.b + paramInt2, this.g.c - 1, this.h, c(), paramBoolean);
    case 0: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a + paramInt1, this.g.b + paramInt2, this.g.f + 1, this.h, c(), paramBoolean);
    case 1: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt2, this.g.c + paramInt1, this.h, c(), paramBoolean);
    case 3: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt2, this.g.c + paramInt1, this.h, c(), paramBoolean);
    }
    return null;
  }
  
  protected StructurePiece b(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
    switch (this.h) {
    case 2: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt1, this.g.c + paramInt2, 1, c(), paramBoolean);
    case 0: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a - 1, this.g.b + paramInt1, this.g.c + paramInt2, 1, c(), paramBoolean);
    case 1: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.c - 1, 2, c(), paramBoolean);
    case 3: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.c - 1, 2, c(), paramBoolean);
    }
    return null;
  }
  
  protected StructurePiece c(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
    switch (this.h) {
    case 2: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt1, this.g.c + paramInt2, 3, c(), paramBoolean);
    case 0: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.d + 1, this.g.b + paramInt1, this.g.c + paramInt2, 3, c(), paramBoolean);
    case 1: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.f + 1, 0, c(), paramBoolean);
    case 3: 
      return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.g.a + paramInt2, this.g.b + paramInt1, this.g.f + 1, 0, c(), paramBoolean);
    }
    return null;
  }
  
  protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
    return (paramStructureBoundingBox != null) && (paramStructureBoundingBox.b > 10);
  }
}
