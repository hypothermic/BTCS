package org.bukkit.craftbukkit.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityItem;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class CraftItem extends CraftEntity implements org.bukkit.entity.Item
{
  private EntityItem item;
  
  public CraftItem(CraftServer server, Entity entity, EntityItem item)
  {
    super(server, entity);
    this.item = item;
  }
  
  public CraftItem(CraftServer server, EntityItem entity) {
    this(server, entity, entity);
  }
  
  public ItemStack getItemStack() {
    return new CraftItemStack(this.item.itemStack);
  }
  
  public void setItemStack(ItemStack stack) {
    this.item.itemStack = CraftItemStack.createNMSItemStack(stack);
  }
  
  public int getPickupDelay() {
    return this.item.pickupDelay;
  }
  
  public void setPickupDelay(int delay) {
    this.item.pickupDelay = delay;
  }
  
  public String toString()
  {
    return "CraftItem";
  }
  
  public EntityType getType() {
    return EntityType.DROPPED_ITEM;
  }
}
