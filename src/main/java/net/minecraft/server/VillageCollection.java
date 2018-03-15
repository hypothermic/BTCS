package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class VillageCollection
{
  private World world;
  private final List b = new ArrayList();
  private final List c = new ArrayList();
  private final List villages = new ArrayList();
  private int time = 0;
  
  public VillageCollection(World paramWorld) {
    this.world = paramWorld;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3) {
    if (this.b.size() > 64) return;
    if (!d(paramInt1, paramInt2, paramInt3)) this.b.add(new ChunkCoordinates(paramInt1, paramInt2, paramInt3));
  }
  
  public void tick() {
    this.time += 1;
    for (Village localVillage : (Village[]) this.villages.toArray()) // BTCS: added cast and .toArray()
      localVillage.tick(this.time);
    c();
    d();
    e();
  }
  
  private void c() {
    for (Iterator localIterator = this.villages.iterator(); localIterator.hasNext();) {
      Village localVillage = (Village)localIterator.next();
      if (localVillage.isAbandoned()) localIterator.remove();
    }
  }
  
  public List getVillages() {
    return this.villages;
  }
  
  public Village getClosestVillage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Object localObject = null;
    float f1 = Float.MAX_VALUE;
    for (Village localVillage : (Village[]) this.villages.toArray()) // BTCS: added cast and .toArray()
    {
      float f2 = localVillage.getCenter().c(paramInt1, paramInt2, paramInt3);
      if (f2 < f1)
      {
        int i = paramInt4 + localVillage.getSize();
        if (f2 <= i * i)
        {
          localObject = localVillage;
          f1 = f2;
        } } }
    return (Village)localObject;
  }
  
  private void d() {
    if (this.b.isEmpty()) return;
    a((ChunkCoordinates)this.b.remove(0));
  }
  
  private void e()
  {
    for (int i = 0; i < this.c.size(); i++) {
      VillageDoor localVillageDoor = (VillageDoor)this.c.get(i);
      
      int j = 0;
      for (Object localObject = this.villages.iterator(); ((Iterator)localObject).hasNext();) { Village localVillage = (Village)((Iterator)localObject).next();
        int k = (int)localVillage.getCenter().b(localVillageDoor.locX, localVillageDoor.locY, localVillageDoor.locZ);
        if (k <= 32 + localVillage.getSize()) {
          localVillage.addDoor(localVillageDoor);
          j = 1;
        }
      }
      if (j == 0)
      {

        Village localObject = new Village(this.world); // BTCS: added decl
        ((Village)localObject).addDoor(localVillageDoor);
        this.villages.add(localObject);
      } }
    this.c.clear();
  }
  
  private void a(ChunkCoordinates paramChunkCoordinates) {
    int i = 16;int j = 4;int k = 16;
    for (int m = paramChunkCoordinates.x - i; m < paramChunkCoordinates.x + i; m++) {
      for (int n = paramChunkCoordinates.y - j; n < paramChunkCoordinates.y + j; n++) {
        for (int i1 = paramChunkCoordinates.z - k; i1 < paramChunkCoordinates.z + k; i1++)
          if (e(m, n, i1))
          {
            VillageDoor localVillageDoor = b(m, n, i1);
            if (localVillageDoor == null) c(m, n, i1); else
              localVillageDoor.addedTime = this.time;
          }
      }
    }
  }
  
  // BTCS start
  /*private VillageDoor b(int paramInt1, int paramInt2, int paramInt3) {
    Object localObject;
    for (Iterator localIterator = this.c.iterator(); localIterator.hasNext(); return localObject)
    {
      localObject = (VillageDoor)localIterator.next();
      if ((((VillageDoor)localObject).locX != paramInt1) || (((VillageDoor)localObject).locZ != paramInt3) || (Math.abs(((VillageDoor)localObject).locY - paramInt2) > 1)) {} }
    Iterator localIterator;
    for (localIterator = this.villages.iterator(); localIterator.hasNext();) { localObject = (Village)localIterator.next();
      VillageDoor localVillageDoor = ((Village)localObject).d(paramInt1, paramInt2, paramInt3);
      if (localVillageDoor != null) return localVillageDoor;
    }
    return null;
  }*/
  private VillageDoor b(int i, int j, int k) {
      Iterator iterator = this.c.iterator();

      VillageDoor villagedoor;

      do {
          if (!iterator.hasNext()) {
              iterator = this.villages.iterator();

              VillageDoor villagedoor1;

              do {
                  if (!iterator.hasNext()) {
                      return null;
                  }

                  Village village = (Village) iterator.next();

                  villagedoor1 = village.d(i, j, k);
              } while (villagedoor1 == null);

              return villagedoor1;
          }

          villagedoor = (VillageDoor) iterator.next();
      } while (villagedoor.locX != i || villagedoor.locZ != k || Math.abs(villagedoor.locY - j) > 1);

      return villagedoor;
  }
  // BTCS end
  
  private void c(int paramInt1, int paramInt2, int paramInt3) {
    int i = ((BlockDoor)Block.WOODEN_DOOR).c(this.world, paramInt1, paramInt2, paramInt3);
    int j; int k; if ((i == 0) || (i == 2)) {
      j = 0;
      for (k = -5; k < 0; k++)
        if (this.world.isChunkLoaded(paramInt1 + k, paramInt2, paramInt3)) j--;
      for (k = 1; k <= 5; k++)
        if (this.world.isChunkLoaded(paramInt1 + k, paramInt2, paramInt3)) j++;
      if (j != 0) this.c.add(new VillageDoor(paramInt1, paramInt2, paramInt3, j > 0 ? -2 : 2, 0, this.time));
    } else {
      j = 0;
      for (k = -5; k < 0; k++)
        if (this.world.isChunkLoaded(paramInt1, paramInt2, paramInt3 + k)) j--;
      for (k = 1; k <= 5; k++)
        if (this.world.isChunkLoaded(paramInt1, paramInt2, paramInt3 + k)) j++;
      if (j != 0) this.c.add(new VillageDoor(paramInt1, paramInt2, paramInt3, 0, j > 0 ? -2 : 2, this.time));
    }
  }
  
  // BTCS start
  /*private boolean d(int paramInt1, int paramInt2, int paramInt3) {
    for (Iterator localIterator = this.b.iterator(); localIterator.hasNext(); 
        return true)
    {
      ChunkCoordinates localChunkCoordinates = (ChunkCoordinates)localIterator.next();
      if ((localChunkCoordinates.x != paramInt1) || (localChunkCoordinates.y != paramInt2) || (localChunkCoordinates.z != paramInt3)) {} }
    return false;
  }*/
  private boolean d(int i, int j, int k) {
      Iterator iterator = this.b.iterator();

      ChunkCoordinates chunkcoordinates;

      do {
          if (!iterator.hasNext()) {
              return false;
          }

          chunkcoordinates = (ChunkCoordinates) iterator.next();
      } while (chunkcoordinates.x != i || chunkcoordinates.y != j || chunkcoordinates.z != k);

      return true;
  }
  // BTCS end
  
  private boolean e(int paramInt1, int paramInt2, int paramInt3) {
    int i = this.world.getTypeId(paramInt1, paramInt2, paramInt3);
    return i == Block.WOODEN_DOOR.id;
  }
}
