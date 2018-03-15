package net.minecraft.server;

public abstract interface ICommandListener
{
  public abstract void sendMessage(String paramString);
  
  public abstract String getName();
}
