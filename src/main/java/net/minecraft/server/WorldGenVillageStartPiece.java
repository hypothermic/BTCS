package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenVillageStartPiece
  extends WorldGenVillageWell
{
  public WorldChunkManager a;
  public int b;
  public WorldGenVillagePieceWeight c;
  public ArrayList d;
  public ArrayList e = new ArrayList();
  public ArrayList f = new ArrayList();
  
  public WorldGenVillageStartPiece(WorldChunkManager paramWorldChunkManager, int paramInt1, Random paramRandom, int paramInt2, int paramInt3, ArrayList paramArrayList, int paramInt4) {
    super(0, paramRandom, paramInt2, paramInt3);
    this.a = paramWorldChunkManager;
    this.d = paramArrayList;
    this.b = paramInt4;
  }
  
  public WorldChunkManager a() {
    return this.a;
  }
}
