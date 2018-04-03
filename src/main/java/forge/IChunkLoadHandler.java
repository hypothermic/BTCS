package forge;

import java.util.Set;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.Entity;
import net.minecraft.server.World;

public abstract interface IChunkLoadHandler
{
  public abstract void addActiveChunks(World paramWorld, Set<ChunkCoordIntPair> paramSet);
  
  public abstract boolean canUnloadChunk(Chunk paramChunk);
  
  public abstract boolean canUpdateEntity(Entity paramEntity);
}
