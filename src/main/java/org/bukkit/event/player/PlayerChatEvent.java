package org.bukkit.event.player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerChatEvent
  extends PlayerEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancel = false;
  private String message;
  private String format = "<%1$s> %2$s";
  private final Set<Player> recipients;
  
  public PlayerChatEvent(Player player, String message) {
    super(player);
    this.recipients = new HashSet(Arrays.asList(player.getServer().getOnlinePlayers()));
    this.message = message;
  }
  
  public boolean isCancelled() {
    return this.cancel;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }
  




  public String getMessage()
  {
    return this.message;
  }
  




  public void setMessage(String message)
  {
    this.message = message;
  }
  





  public void setPlayer(Player player)
  {
    this.player = player;
  }
  




  public String getFormat()
  {
    return this.format;
  }
  




  public void setFormat(String format)
  {
    try
    {
      String.format(format, new Object[] { this.player, this.message });
    } catch (RuntimeException ex) {
      ex.fillInStackTrace();
      throw ex;
    }
    
    this.format = format;
  }
  




  public Set<Player> getRecipients()
  {
    return this.recipients;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
