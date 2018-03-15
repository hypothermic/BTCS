package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityMinecart;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftStorageMinecart extends CraftMinecart implements org.bukkit.entity.StorageMinecart
{
  private CraftInventory inventory;
  
  public CraftStorageMinecart(CraftServer server, EntityMinecart entity)
  {
    super(server, entity);
    this.inventory = new CraftInventory(entity);
  }
  
  public Inventory getInventory() {
    return this.inventory;
  }
  
  public String toString()
  {
    return "CraftStorageMinecart{inventory=" + this.inventory + '}';
  }
}
