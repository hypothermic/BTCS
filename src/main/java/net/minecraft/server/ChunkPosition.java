package net.minecraft.server;

public class ChunkPosition
{
  public final int x;
  public final int y;
  public final int z;
  
  public ChunkPosition(int paramInt1, int paramInt2, int paramInt3) {
    this.x = paramInt1;
    this.y = paramInt2;
    this.z = paramInt3;
  }
  
  public ChunkPosition(Vec3D paramVec3D) {
    this(MathHelper.floor(paramVec3D.a), MathHelper.floor(paramVec3D.b), MathHelper.floor(paramVec3D.c));
  }
  
  public boolean equals(Object paramObject) {
    if ((paramObject instanceof ChunkPosition))
    {
      ChunkPosition localChunkPosition = (ChunkPosition)paramObject;
      return (localChunkPosition.x == this.x) && (localChunkPosition.y == this.y) && (localChunkPosition.z == this.z);
    }
    
    return false;
  }
  
  public int hashCode() {
    return this.x * 8976890 + this.y * 981131 + this.z;
  }
}
