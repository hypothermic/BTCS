package net.minecraft.server;

import java.util.List;
import java.util.Random;
public class WorldGenNetherPieces
{
  private static final WorldGenNetherPieceWeight[] a = { new WorldGenNetherPieceWeight(WorldGenNetherPiece2.class, 30, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece9.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece11.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece7.class, 10, 3), new WorldGenNetherPieceWeight(WorldGenNetherPiece12.class, 5, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece10.class, 5, 1) };
 
  private static final WorldGenNetherPieceWeight[] b = { new WorldGenNetherPieceWeight(WorldGenNetherPiece8.class, 25, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece13.class, 15, 5), new WorldGenNetherPieceWeight(WorldGenNetherPiece1.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece5.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece14.class, 10, 3, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece6.class, 7, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece3.class, 5, 2) };
  
  private static WorldGenNetherPiece b(WorldGenNetherPieceWeight paramWorldGenNetherPieceWeight, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Class localClass = paramWorldGenNetherPieceWeight.a;
    Object localObject = null;
    
    if (localClass == WorldGenNetherPiece2.class) {
      localObject = WorldGenNetherPiece2.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece9.class) {
      localObject = WorldGenNetherPiece9.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece11.class) {
      localObject = WorldGenNetherPiece11.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece7.class) {
      localObject = WorldGenNetherPiece7.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece12.class) {
      localObject = WorldGenNetherPiece12.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece10.class) {
      localObject = WorldGenNetherPiece10.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece8.class) {
      localObject = WorldGenNetherPiece8.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece1.class) {
      localObject = WorldGenNetherPiece1.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece5.class) {
      localObject = WorldGenNetherPiece5.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece14.class) {
      localObject = WorldGenNetherPiece14.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece6.class) {
      localObject = WorldGenNetherPiece6.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece13.class) {
      localObject = WorldGenNetherPiece13.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (localClass == WorldGenNetherPiece3.class) {
      localObject = WorldGenNetherPiece3.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
    return (WorldGenNetherPiece)localObject;
  }
  // BTCS start
  static WorldGenNetherPiece a(WorldGenNetherPieceWeight worldgennetherpieceweight, List list, Random random, int i, int j, int k, int l, int i1) {
      return b(worldgennetherpieceweight, list, random, i, j, k, l, i1);
  }

  static WorldGenNetherPieceWeight[] a() {
      return a;
  }

  static WorldGenNetherPieceWeight[] b() {
      return b;
  }
  // BTCS end
}
