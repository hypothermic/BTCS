package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

class WorldGenVillageStart extends StructureStart
{
  private boolean c = false;
  


  public WorldGenVillageStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt3;
    ArrayList localArrayList1 = WorldGenVillagePieces.a(paramRandom, i);
    
    WorldGenVillageStartPiece localWorldGenVillageStartPiece = new WorldGenVillageStartPiece(paramWorld.getWorldChunkManager(), 0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2, localArrayList1, i);
    this.a.add(localWorldGenVillageStartPiece);
    localWorldGenVillageStartPiece.a(localWorldGenVillageStartPiece, this.a, paramRandom);
    
    ArrayList localArrayList2 = localWorldGenVillageStartPiece.f;
    ArrayList localArrayList3 = localWorldGenVillageStartPiece.e;
    while ((!localArrayList2.isEmpty()) || (!localArrayList3.isEmpty()))
    {

    	int j; // BTCS
    	Object localObject; // BTCS
      if (!localArrayList2.isEmpty()) {
        j = paramRandom.nextInt(localArrayList2.size());
        localObject = (StructurePiece)localArrayList2.remove(j);
        ((StructurePiece)localObject).a(localWorldGenVillageStartPiece, this.a, paramRandom);
      } else {
        j = paramRandom.nextInt(localArrayList3.size());
        localObject = (StructurePiece)localArrayList3.remove(j);
        ((StructurePiece)localObject).a(localWorldGenVillageStartPiece, this.a, paramRandom);
      }
    }
    
    d();
    
    int j = 0;
    for (Object localObject = this.a.iterator(); ((Iterator)localObject).hasNext();) { StructurePiece localStructurePiece = (StructurePiece)((Iterator)localObject).next();
      if (!(localStructurePiece instanceof WorldGenVillageRoadPiece)) {
        j++;
      }
    }
    this.c = (j > 2);
  }
  
  public boolean a() {
    return this.c;
  }
}
