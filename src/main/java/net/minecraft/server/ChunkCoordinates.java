package net.minecraft.server;

public class ChunkCoordinates implements Comparable
{
  public int x;
  public int y;
  public int z;
  
  public ChunkCoordinates() {}
  
  public ChunkCoordinates(int paramInt1, int paramInt2, int paramInt3)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.z = paramInt3;
  }
  
  public ChunkCoordinates(ChunkCoordinates paramChunkCoordinates) {
    this.x = paramChunkCoordinates.x;
    this.y = paramChunkCoordinates.y;
    this.z = paramChunkCoordinates.z;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ChunkCoordinates)) {
      return false;
    }
    
    ChunkCoordinates localChunkCoordinates = (ChunkCoordinates)paramObject;
    return (this.x == localChunkCoordinates.x) && (this.y == localChunkCoordinates.y) && (this.z == localChunkCoordinates.z);
  }
  
  public int hashCode()
  {
    return this.x + this.z << 8 + this.y << 16;
  }
  
  public int compareTo(ChunkCoordinates paramChunkCoordinates) {
    if (this.y == paramChunkCoordinates.y) {
      if (this.z == paramChunkCoordinates.z) {
        return this.x - paramChunkCoordinates.x;
      }
      return this.z - paramChunkCoordinates.z;
    }
    return this.y - paramChunkCoordinates.y;
  }
  



  public void a(int paramInt1, int paramInt2, int paramInt3)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.z = paramInt3;
  }
  





























































































































  public double b(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.x - paramInt1;
    int j = this.y - paramInt2;
    int k = this.z - paramInt3;
    
    return Math.sqrt(i * i + j * j + k * k);
  }
  



  public float c(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.x - paramInt1;
    int j = this.y - paramInt2;
    int k = this.z - paramInt3;
    return i * i + j * j + k * k;
  }

  // BTCS start
  public int compareTo(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
  }
  // BTCS end
}
