package org.bukkit.block;

import org.bukkit.Material;

public abstract interface Jukebox
  extends BlockState
{
  public abstract Material getPlaying();
  
  public abstract void setPlaying(Material paramMaterial);
  
  public abstract boolean isPlaying();
  
  public abstract boolean eject();
}
