package net.minecraft.server;

public class ChunkCoordIntPair
{
  public final int x;
  public final int z;
  
  public ChunkCoordIntPair(int paramInt1, int paramInt2) {
    this.x = paramInt1;
    this.z = paramInt2;
  }
  
  public static long a(int paramInt1, int paramInt2) {
    long l1 = paramInt1;
    long l2 = paramInt2;
    return l1 & 0xFFFFFFFF | (l2 & 0xFFFFFFFF) << 32;
  }
  
  public int hashCode() {
    long l = a(this.x, this.z);
    int i = (int)l;
    int j = (int)(l >> 32);
    return i ^ j;
  }
  
  public boolean equals(Object paramObject) {
    ChunkCoordIntPair localChunkCoordIntPair = (ChunkCoordIntPair)paramObject;
    return (localChunkCoordIntPair.x == this.x) && (localChunkCoordIntPair.z == this.z);
  }
  
  public double a(Entity paramEntity) {
    double d1 = this.x * 16 + 8;
    double d2 = this.z * 16 + 8;
    
    double d3 = d1 - paramEntity.locX;
    double d4 = d2 - paramEntity.locZ;
    
    return d3 * d3 + d4 * d4;
  }
  
  public int a() {
    return (this.x << 4) + 8;
  }
  
  public int b() {
    return (this.z << 4) + 8;
  }
  
  public ChunkPosition a(int paramInt) {
    return new ChunkPosition(a(), paramInt, b());
  }
  
  public String toString() {
    return "[" + this.x + ", " + this.z + "]";
  }
}
