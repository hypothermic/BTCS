package org.bukkit;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

public abstract interface Chunk {
  public abstract int getX();
  
  public abstract int getZ();
  
  public abstract World getWorld();
  
  public abstract Block getBlock(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract ChunkSnapshot getChunkSnapshot();
  
  public abstract ChunkSnapshot getChunkSnapshot(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract Entity[] getEntities();
  
  public abstract BlockState[] getTileEntities();
  
  public abstract boolean isLoaded();
  
  public abstract boolean load(boolean paramBoolean);
  
  public abstract boolean load();
  
  public abstract boolean unload(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract boolean unload(boolean paramBoolean);
  
  public abstract boolean unload();
}
