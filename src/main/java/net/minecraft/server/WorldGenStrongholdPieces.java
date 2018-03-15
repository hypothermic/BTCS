package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPieces
{
  private static final WorldGenStrongholdPieceWeight[] b = { new WorldGenStrongholdPieceWeight(WorldGenStrongholdStraight.class, 40, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdPrison.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdLeftTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRightTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRoomCrossing.class, 10, 6), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairsStraight.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairs.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdCrossing.class, 5, 4), new WorldGenStrongholdPieceWeight(WorldGenStrongholdPiece2.class, 5, 4), new WorldGenStrongholdUnknown(WorldGenStrongholdLibrary.class, 10, 2), new WorldGenStrongholdPieceWeight3(WorldGenStrongholdPortalRoom.class, 20, 1) };
 
  private static List c;
 
  private static Class d;
 
  static int a = 0;
  
  public static void a() {
    c = new ArrayList();
    for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : b) {
      localWorldGenStrongholdPieceWeight.c = 0;
      c.add(localWorldGenStrongholdPieceWeight);
    }
    d = null;
  }
  
  private static boolean c() {
    boolean bool = false;
    a = 0;
    for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : (WorldGenStrongholdPieceWeight[]) c.toArray()) { // BTCS: added cast and .toArray()
      if ((localWorldGenStrongholdPieceWeight.d > 0) && (localWorldGenStrongholdPieceWeight.c < localWorldGenStrongholdPieceWeight.d)) {
        bool = true;
      }
      a += localWorldGenStrongholdPieceWeight.b;
    }
    return bool;
  }
  

  private static WorldGenStrongholdPiece a(Class paramClass, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Object localObject = null;
    
    if (paramClass == WorldGenStrongholdStraight.class) {
      localObject = WorldGenStrongholdStraight.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdPrison.class) {
      localObject = WorldGenStrongholdPrison.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdLeftTurn.class) {
      localObject = WorldGenStrongholdLeftTurn.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdRightTurn.class) {
      localObject = WorldGenStrongholdRightTurn.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdRoomCrossing.class) {
      localObject = WorldGenStrongholdRoomCrossing.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdStairsStraight.class) {
      localObject = WorldGenStrongholdStairsStraight.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdStairs.class) {
      localObject = WorldGenStrongholdStairs.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdCrossing.class) {
      localObject = WorldGenStrongholdCrossing.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdPiece2.class) {
      localObject = WorldGenStrongholdPiece2.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdLibrary.class) {
      localObject = WorldGenStrongholdLibrary.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramClass == WorldGenStrongholdPortalRoom.class) {
      localObject = WorldGenStrongholdPortalRoom.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
    
    return (WorldGenStrongholdPiece)localObject;
  }
  
  private static WorldGenStrongholdPiece b(WorldGenStrongholdStairs2 paramWorldGenStrongholdStairs2, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (!c()) {
      return null;
    }
    
    if (d != null)
    {
      WorldGenStrongholdPiece localWorldGenStrongholdPiece1 = a(d, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      d = null;
      
      if (localWorldGenStrongholdPiece1 != null) {
        return localWorldGenStrongholdPiece1;
      }
    }
    
    int i = 0;
    int j; while (i < 5) {
      i++;
      
      j = paramRandom.nextInt(a);
      for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : (WorldGenStrongholdPieceWeight[]) c.toArray()) { // BTCS: added cast and .toArray()
        j -= localWorldGenStrongholdPieceWeight.b;
        if (j < 0)
        {
          if ((!localWorldGenStrongholdPieceWeight.a(paramInt5)) || (localWorldGenStrongholdPieceWeight == paramWorldGenStrongholdStairs2.a)) {
            break;
          }
          
          WorldGenStrongholdPiece localWorldGenStrongholdPiece2 = a(localWorldGenStrongholdPieceWeight.a, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
          if (localWorldGenStrongholdPiece2 != null) {
            localWorldGenStrongholdPieceWeight.c += 1;
            paramWorldGenStrongholdStairs2.a = localWorldGenStrongholdPieceWeight;
            
            if (!localWorldGenStrongholdPieceWeight.a()) {
              c.remove(localWorldGenStrongholdPieceWeight);
            }
            return localWorldGenStrongholdPiece2;
          }
        }
      }
    }
    


    StructureBoundingBox localStructureBoundingBox = WorldGenStrongholdCorridor.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((localStructureBoundingBox != null) && (localStructureBoundingBox.b > 1)) {
      return new WorldGenStrongholdCorridor(paramInt5, paramRandom, localStructureBoundingBox, paramInt4);
    }
    

    return null;
  }
  
  private static StructurePiece c(WorldGenStrongholdStairs2 paramWorldGenStrongholdStairs2, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramInt5 > 50) {
      return null;
    }
    if ((Math.abs(paramInt1 - paramWorldGenStrongholdStairs2.b().a) > 112) || (Math.abs(paramInt3 - paramWorldGenStrongholdStairs2.b().c) > 112)) {
      return null;
    }
    
    WorldGenStrongholdPiece localWorldGenStrongholdPiece = b(paramWorldGenStrongholdStairs2, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5 + 1);
    if (localWorldGenStrongholdPiece != null) {
      paramList.add(localWorldGenStrongholdPiece);
      paramWorldGenStrongholdStairs2.c.add(localWorldGenStrongholdPiece);
    }
    return localWorldGenStrongholdPiece;
  }
  
  // BTCS start
  static StructurePiece a(WorldGenStrongholdStairs2 worldgenstrongholdstairs2, List list, Random random, int i, int j, int k, int l, int i1) {
      return c(worldgenstrongholdstairs2, list, random, i, j, k, l, i1);
  }

  static Class a(Class oclass) {
      d = oclass;
      return oclass;
  }

  static WorldGenStrongholdStones b() {
      return e;
  }
  // BTCS end
  
  private static final WorldGenStrongholdStones e = new WorldGenStrongholdStones(null);
}
