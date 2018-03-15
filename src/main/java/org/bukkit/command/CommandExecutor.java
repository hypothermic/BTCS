package org.bukkit.command;

public abstract interface CommandExecutor
{
  public abstract boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString);
}
