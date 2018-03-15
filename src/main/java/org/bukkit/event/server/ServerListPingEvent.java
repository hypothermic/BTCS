package org.bukkit.event.server;

import java.net.InetAddress;
import org.bukkit.event.HandlerList;



public class ServerListPingEvent
  extends ServerEvent
{
  private static final HandlerList handlers = new HandlerList();
  private final InetAddress address;
  private String motd;
  private final int numPlayers;
  private int maxPlayers;
  
  public ServerListPingEvent(InetAddress address, String motd, int numPlayers, int maxPlayers) {
    this.address = address;
    this.motd = motd;
    this.numPlayers = numPlayers;
    this.maxPlayers = maxPlayers;
  }
  




  public InetAddress getAddress()
  {
    return this.address;
  }
  




  public String getMotd()
  {
    return this.motd;
  }
  




  public void setMotd(String motd)
  {
    this.motd = motd;
  }
  




  public int getNumPlayers()
  {
    return this.numPlayers;
  }
  




  public int getMaxPlayers()
  {
    return this.maxPlayers;
  }
  




  public void setMaxPlayers(int maxPlayers)
  {
    this.maxPlayers = maxPlayers;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
