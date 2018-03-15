package org.bukkit.event.world;

import org.bukkit.Chunk;

public abstract class ChunkEvent
  extends WorldEvent
{
  protected Chunk chunk;
  
  protected ChunkEvent(Chunk chunk)
  {
    super(chunk.getWorld());
    this.chunk = chunk;
  }
  




  public Chunk getChunk()
  {
    return this.chunk;
  }
}
