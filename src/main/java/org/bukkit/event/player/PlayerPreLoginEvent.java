package org.bukkit.event.player;

import java.net.InetAddress;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class PlayerPreLoginEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Result result;
  private String message;
  private final String name;
  private final InetAddress ipAddress;
  
  public PlayerPreLoginEvent(String name, InetAddress ipAddress) {
    this.result = Result.ALLOWED;
    this.message = "";
    this.name = name;
    this.ipAddress = ipAddress;
  }
  




  public Result getResult()
  {
    return this.result;
  }
  




  public void setResult(Result result)
  {
    this.result = result;
  }
  




  public String getKickMessage()
  {
    return this.message;
  }
  




  public void setKickMessage(String message)
  {
    this.message = message;
  }
  


  public void allow()
  {
    this.result = Result.ALLOWED;
    this.message = "";
  }
  





  public void disallow(Result result, String message)
  {
    this.result = result;
    this.message = message;
  }
  




  public String getName()
  {
    return this.name;
  }
  




  public InetAddress getAddress()
  {
    return this.ipAddress;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  






  public static enum Result
  {
    ALLOWED, 
    


    KICK_FULL, 
    


    KICK_BANNED, 
    


    KICK_WHITELIST, 
    


    KICK_OTHER;
    
    private Result() {}
  }
}
