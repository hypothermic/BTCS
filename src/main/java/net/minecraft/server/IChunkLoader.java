package net.minecraft.server;

public abstract interface IChunkLoader
{
  public abstract Chunk a(World paramWorld, int paramInt1, int paramInt2);
  
  public abstract void a(World paramWorld, Chunk paramChunk);
  
  public abstract void b(World paramWorld, Chunk paramChunk);
  
  public abstract void a();
  
  public abstract void b();
}
