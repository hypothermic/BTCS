package net.minecraft.server;

public abstract interface IMinecraftServer
{
  public abstract int getProperty(String paramString, int paramInt);
  
  public abstract String a(String paramString1, String paramString2);
  
  public abstract void a(String paramString, Object paramObject);
  
  public abstract void c();
  
  public abstract String getPropertiesFile();
  
  public abstract String getMotd();
  
  public abstract int getPort();
  
  public abstract String getServerAddress();
  
  public abstract String getVersion();
  
  public abstract int getPlayerCount();
  
  public abstract int getMaxPlayers();
  
  public abstract String[] getPlayers();
  
  public abstract String getWorldName();
  
  public abstract String getPlugins();
  
  public abstract void o();
  
  public abstract String d(String paramString);
  
  public abstract boolean isDebugging();
  
  public abstract void sendMessage(String paramString);
  
  public abstract void warning(String paramString);
  
  public abstract void severe(String paramString);
  
  public abstract void debug(String paramString);
}
