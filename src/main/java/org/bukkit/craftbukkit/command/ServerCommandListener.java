package org.bukkit.craftbukkit.command;

import java.lang.reflect.Method;
import net.minecraft.server.ICommandListener;
import org.bukkit.command.CommandSender;

public class ServerCommandListener implements ICommandListener
{
  private final CommandSender commandSender;
  private final String prefix;
  
  public ServerCommandListener(CommandSender commandSender)
  {
    this.commandSender = commandSender;
    String[] parts = commandSender.getClass().getName().split("\\.");
    this.prefix = parts[(parts.length - 1)];
  }
  
  public void sendMessage(String msg) {
    this.commandSender.sendMessage(msg);
  }
  
  public CommandSender getSender() {
    return this.commandSender;
  }
  
  public String getName() {
    try {
      Method getName = this.commandSender.getClass().getMethod("getName", new Class[0]);
      
      return (String)getName.invoke(this.commandSender, new Object[0]);
    }
    catch (Exception e) {}
    return this.prefix;
  }
}
