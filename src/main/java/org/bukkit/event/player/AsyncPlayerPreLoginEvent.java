package org.bukkit.event.player;

import java.net.InetAddress;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;




public class AsyncPlayerPreLoginEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private PlayerPreLoginEvent.Result result;
  private String message;
  private final String name;
  private final InetAddress ipAddress;
  
  public AsyncPlayerPreLoginEvent(String name, InetAddress ipAddress) {
    super(true);
    this.result = PlayerPreLoginEvent.Result.ALLOWED;
    this.message = "";
    this.name = name;
    this.ipAddress = ipAddress;
  }
  




  public PlayerPreLoginEvent.Result getResult()
  {
    return this.result;
  }
  




  public void setResult(PlayerPreLoginEvent.Result result)
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
    this.result = PlayerPreLoginEvent.Result.ALLOWED;
    this.message = "";
  }
  





  public void disallow(PlayerPreLoginEvent.Result result, String message)
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
}
