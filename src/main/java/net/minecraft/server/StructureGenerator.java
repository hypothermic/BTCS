package net.minecraft.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class StructureGenerator extends WorldGenBase
{
  protected HashMap<Long, StructureStart> e = new HashMap(); // BTCS: added decl '<Long, StructureStart>'
  
  public void a(IChunkProvider paramIChunkProvider, World paramWorld, int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    super.a(paramIChunkProvider, paramWorld, paramInt1, paramInt2, paramArrayOfByte);
  }
  
  protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte)
  {
    if (this.e.containsKey(Long.valueOf(ChunkCoordIntPair.a(paramInt1, paramInt2)))) {
      return;
    }

    this.c.nextInt();
    if (a(paramInt1, paramInt2))
    {
      StructureStart localStructureStart = b(paramInt1, paramInt2);
      this.e.put(Long.valueOf(ChunkCoordIntPair.a(paramInt1, paramInt2)), localStructureStart);
    }
  }
  
  public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    int i = (paramInt1 << 4) + 8;
    int j = (paramInt2 << 4) + 8;
    
    boolean bool = false;
    for (StructureStart localStructureStart : this.e.values().toArray(new StructureStart[this.e.values().size()])) { // BTCS: added .toArray(...)
      if ((localStructureStart.a()) && 
        (localStructureStart.b().a(i, j, i + 15, j + 15))) {
        localStructureStart.a(paramWorld, paramRandom, new StructureBoundingBox(i, j, i + 15, j + 15));
        bool = true;
      }
    }

    return bool;
  }
  
  public boolean a(int paramInt1, int paramInt2, int paramInt3)
  {
    for (StructureStart localStructureStart : (StructureStart[]) this.e.values().toArray()) {  // BTCS: added cast and .toArray()
      if ((localStructureStart.a()) && 
        (localStructureStart.b().a(paramInt1, paramInt3, paramInt1, paramInt3)))
      {
        Iterator localIterator2 = localStructureStart.c().iterator();
        while (localIterator2.hasNext()) {
          StructurePiece localStructurePiece = (StructurePiece)localIterator2.next();
          if (localStructurePiece.b().b(paramInt1, paramInt2, paramInt3)) {
            return true;
          }
        }
      }
    }
    
    return false;
  }

  public ChunkPosition getNearestGeneratedFeature(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    this.d = paramWorld;
    
    this.c.setSeed(paramWorld.getSeed());
    long l1 = this.c.nextLong();
    long l2 = this.c.nextLong();
    long l3 = (paramInt1 >> 4) * l1;
    long l4 = (paramInt3 >> 4) * l2;
    this.c.setSeed(l3 ^ l4 ^ paramWorld.getSeed());
    
    a(paramWorld, paramInt1 >> 4, paramInt3 >> 4, 0, 0, null);
    
    double d1 = Double.MAX_VALUE;
    Object localObject1 = null;
    
    Object localObject2; // BTCS: moved outside of for loop
    for (localObject2 = this.e.values().iterator(); ((Iterator)localObject2).hasNext();) {
    Object localObject3 = (StructureStart)((Iterator)localObject2).next();
      if (((StructureStart)localObject3).a())
      {
        StructurePiece localObject4 = (StructurePiece)((StructureStart)localObject3).c().get(0); // BTCS: added decl
        ChunkPosition localChunkPosition = ((StructurePiece)localObject4).b_(); // BTCS: added decl
        
        // BTCS start: added decls for everything
        int i = localChunkPosition.x - paramInt1;
        int j = localChunkPosition.y - paramInt2;
        int k = localChunkPosition.z - paramInt3;
        double d2 = i + i * j * j + k * k;
        // BTCS end
        
        if (d2 < d1) {
          d1 = d2;
          localObject1 = localChunkPosition; } } }
    Object localObject3;
    Object localObject4;
    ChunkPosition localChunkPosition;
    int i; int j; int k; double d2; if (localObject1 != null)
    {
      return (ChunkPosition)localObject1;
    }
    localObject2 = a();
    if (localObject2 != null) {
      localObject3 = null;
      for (localObject4 = ((List)localObject2).iterator(); ((Iterator)localObject4).hasNext();) { localChunkPosition = (ChunkPosition)((Iterator)localObject4).next();
        
        i = localChunkPosition.x - paramInt1;
        j = localChunkPosition.y - paramInt2;
        k = localChunkPosition.z - paramInt3;
        d2 = i + i * j * j + k * k;
        
        if (d2 < d1) {
          d1 = d2;
          localObject3 = localChunkPosition;
        }
      }
      return (ChunkPosition)localObject3;
    }
    
    return null;
  }
  
  protected List a() {
    return null;
  }
  
  protected abstract boolean a(int paramInt1, int paramInt2);
  
  protected abstract StructureStart b(int paramInt1, int paramInt2);
}
