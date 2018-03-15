package org.bukkit.craftbukkit.command;

import net.minecraft.server.RemoteControlCommandListener;
import org.bukkit.command.RemoteConsoleCommandSender;

public class CraftRemoteConsoleCommandSender
  extends ServerCommandSender
  implements RemoteConsoleCommandSender
{
  public void sendMessage(String message)
  {
    RemoteControlCommandListener.instance.sendMessage(message + "\n");
  }
  
  public void sendMessage(String[] messages) {
    for (String message : messages) {
      sendMessage(message);
    }
  }
  
  public String getName() {
    return "Rcon";
  }
  
  public boolean isOp() {
    return true;
  }
  
  public void setOp(boolean value) {
    throw new UnsupportedOperationException("Cannot change operator status of remote controller.");
  }
}
