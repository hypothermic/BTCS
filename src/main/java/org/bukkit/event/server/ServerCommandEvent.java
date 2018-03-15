package org.bukkit.event.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;


public class ServerCommandEvent
  extends ServerEvent
{
  private static final HandlerList handlers = new HandlerList();
  private String command;
  private final CommandSender sender;
  
  public ServerCommandEvent(CommandSender sender, String command) {
    this.command = command;
    this.sender = sender;
  }
  




  public String getCommand()
  {
    return this.command;
  }
  




  public void setCommand(String message)
  {
    this.command = message;
  }
  




  public CommandSender getSender()
  {
    return this.sender;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
