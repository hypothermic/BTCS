package net.minecraft.server;

class PendingChunkToSave
{
  public final ChunkCoordIntPair a;

  public final NBTTagCompound b;

  public PendingChunkToSave(ChunkCoordIntPair paramChunkCoordIntPair, NBTTagCompound paramNBTTagCompound)
  {
    this.a = paramChunkCoordIntPair;
    this.b = paramNBTTagCompound;
  }
}
