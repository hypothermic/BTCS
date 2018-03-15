package org.bukkit.command;

import org.bukkit.Server;
import org.bukkit.permissions.Permissible;

public abstract interface CommandSender
  extends Permissible
{
  public abstract void sendMessage(String paramString);
  
  public abstract void sendMessage(String[] paramArrayOfString);
  
  public abstract Server getServer();
  
  public abstract String getName();
}
