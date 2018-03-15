package net.minecraft.server;

public abstract interface PlayerFileData
{
  public abstract void save(EntityHuman paramEntityHuman);
  
  public abstract void load(EntityHuman paramEntityHuman);
  
  public abstract String[] getSeenPlayers();
}
