package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece15 extends WorldGenNetherPiece9
{
  public ArrayList d = new ArrayList();
  

  public List c;
  
  public List b = new ArrayList();
  public WorldGenNetherPieceWeight a;
  
  public WorldGenNetherPiece15(Random paramRandom, int paramInt1, int paramInt2)
  {
    super(paramRandom, paramInt1, paramInt2);
    
    for (WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : (WorldGenNetherPieceWeight[])WorldGenNetherPieces.a()) { // BTCS: added cast (WorldGenNetherPieceWeight[])
      localWorldGenNetherPieceWeight.c = 0;
      this.b.add(localWorldGenNetherPieceWeight);
    }
    
    this.c = new ArrayList();
    for (WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : (WorldGenNetherPieceWeight[]) WorldGenNetherPieces.b()) { // BTCS: added cast (WorldGenNetherPieceWeight[])
      localWorldGenNetherPieceWeight.c = 0;
      this.c.add(localWorldGenNetherPieceWeight);
    }
  }
}
