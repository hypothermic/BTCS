package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;






@Deprecated
public class PlayerInventoryEvent
  extends PlayerEvent
{
  private static final HandlerList handlers = new HandlerList();
  protected Inventory inventory;
  
  public PlayerInventoryEvent(Player player, Inventory inventory) {
    super(player);
    this.inventory = inventory;
  }
  




  public Inventory getInventory()
  {
    return this.inventory;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
