package forge;

import net.minecraft.server.Chunk;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public abstract interface ISaveEventHandler
{
  public abstract void onWorldLoad(World paramWorld);
  
  public abstract void onWorldSave(World paramWorld);
  
  public abstract void onChunkLoad(World paramWorld, Chunk paramChunk);
  
  public abstract void onChunkUnload(World paramWorld, Chunk paramChunk);
  
  public abstract void onChunkSaveData(World paramWorld, Chunk paramChunk, NBTTagCompound paramNBTTagCompound);
  
  public abstract void onChunkLoadData(World paramWorld, Chunk paramChunk, NBTTagCompound paramNBTTagCompound);
}
