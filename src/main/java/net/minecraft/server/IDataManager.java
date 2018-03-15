package net.minecraft.server;

import java.io.File;
import java.util.List;
import java.util.UUID;

public abstract interface IDataManager
{
  public abstract WorldData getWorldData();
  
  public abstract void checkSession();
  
  public abstract IChunkLoader createChunkLoader(WorldProvider paramWorldProvider);
  
  public abstract void saveWorldData(WorldData paramWorldData, List paramList);
  
  public abstract void saveWorldData(WorldData paramWorldData);
  
  public abstract PlayerFileData getPlayerFileData();
  
  public abstract void e();
  
  public abstract File getDataFile(String paramString);
  
  public abstract UUID getUUID();
}
