package org.bukkit.event.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event; // BTCS
import org.bukkit.event.Event.Result; // BTCS
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryClickEvent extends InventoryEvent implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private InventoryType.SlotType slot_type;
  private boolean rightClick;
  private boolean shiftClick;
  private Event.Result result;
  private int whichSlot;
  private int rawSlot; private ItemStack current = null;
  
  public InventoryClickEvent(InventoryView what, InventoryType.SlotType type, int slot, boolean right, boolean shift) {
    super(what);
    this.slot_type = type;
    this.rightClick = right;
    this.shiftClick = shift;
    this.result = Event.Result.DEFAULT;
    this.rawSlot = slot;
    this.whichSlot = what.convertSlot(slot);
  }
  



  public InventoryType.SlotType getSlotType()
  {
    return this.slot_type;
  }
  



  public ItemStack getCursor()
  {
    return getView().getCursor();
  }
  



  public ItemStack getCurrentItem()
  {
    if (this.slot_type == InventoryType.SlotType.OUTSIDE) return this.current;
    return getView().getItem(this.rawSlot);
  }
  


  public boolean isRightClick()
  {
    return this.rightClick;
  }
  


  public boolean isLeftClick()
  {
    return !this.rightClick;
  }
  



  public boolean isShiftClick()
  {
    return this.shiftClick;
  }
  
  public void setResult(Event.Result newResult) {
    this.result = newResult;
  }
  
  public Event.Result getResult() {
    return this.result;
  }
  



  public HumanEntity getWhoClicked()
  {
    return getView().getPlayer();
  }
  



  public void setCursor(ItemStack what)
  {
    getView().setCursor(what);
  }
  



  public void setCurrentItem(ItemStack what)
  {
    if (this.slot_type == InventoryType.SlotType.OUTSIDE) this.current = what; else
      getView().setItem(this.rawSlot, what);
  }
  
  public boolean isCancelled() {
    return this.result == Event.Result.DENY;
  }
  
  public void setCancelled(boolean toCancel) {
    this.result = (toCancel ? Event.Result.DENY : Event.Result.ALLOW);
  }
  




  public int getSlot()
  {
    return this.whichSlot;
  }
  



  public int getRawSlot()
  {
    return this.rawSlot;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
