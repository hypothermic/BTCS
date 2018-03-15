package net.minecraft.server;

import java.util.List;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;


public class ContainerDispenser
  extends Container
{
  public TileEntityDispenser items;
  private CraftInventoryView bukkitEntity = null;
  private PlayerInventory player;
  
  public ContainerDispenser(IInventory iinventory, TileEntityDispenser tileentitydispenser)
  {
    this.items = tileentitydispenser;
    

    this.player = ((PlayerInventory)iinventory);
    




    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        a(new Slot(tileentitydispenser, j + i * 3, 62 + j * 18, 17 + i * 18));
      }
    }
    
    for (int i = 0; i < 3; i++) { // BTCS: added 'int '
      for (int j = 0; j < 9; j++) {
        a(new Slot(iinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }
    
    for (int i = 0; i < 9; i++) { // BTCS: added 'int '
      a(new Slot(iinventory, i, 8 + i * 18, 142));
    }
  }
  
  public boolean b(EntityHuman entityhuman) {
    if (!this.checkReachable) return true;
    return this.items.a(entityhuman);
  }
  
  public ItemStack a(int i) {
    ItemStack itemstack = null;
    Slot slot = (Slot)this.e.get(i);
    
    if ((slot != null) && (slot.c())) {
      ItemStack itemstack1 = slot.getItem();
      
      itemstack = itemstack1.cloneItemStack();
      if (i < 9) {
        if (!a(itemstack1, 9, 45, true)) {
          return null;
        }
      } else if (!a(itemstack1, 0, 9, false)) {
        return null;
      }
      
      if (itemstack1.count == 0) {
        slot.set((ItemStack)null);
      } else {
        slot.d();
      }
      
      if (itemstack1.count == itemstack.count) {
        return null;
      }
      
      slot.c(itemstack1);
    }
    
    return itemstack;
  }
  
  public CraftInventoryView getBukkitView()
  {
    if (this.bukkitEntity != null) {
      return this.bukkitEntity;
    }
    CraftInventory inventory = new CraftInventory(this.items);
    this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    return this.bukkitEntity;
  }
}
