package net.minecraft.server;

import java.util.List;
import org.bukkit.craftbukkit.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;


public class ContainerWorkbench
  extends Container
{
  public InventoryCrafting craftInventory;
  public IInventory resultInventory;
  private World c;
  private int h;
  private int i;
  private int j;
  private CraftInventoryView bukkitEntity = null;
  
  private PlayerInventory player;
  
  public ContainerWorkbench(PlayerInventory playerinventory, World world, int i, int j, int k)
  {
    this.resultInventory = new InventoryCraftResult();
    this.craftInventory = new InventoryCrafting(this, 3, 3, playerinventory.player);
    this.craftInventory.resultInventory = this.resultInventory;
    this.player = playerinventory;
    
    this.c = world;
    this.h = i;
    this.i = j;
    this.j = k;
    a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 124, 35));
    



    for (int l = 0; l < 3; l++) {
      for (int i1 = 0; i1 < 3; i1++) {
        a(new Slot(this.craftInventory, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
      }
    }
    
    for (int l = 0; l < 3; l++) { // BTCS: added 'int '
      for (int i1 = 0; i1 < 9; i1++) {
        a(new Slot(playerinventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
      }
    }
    
    for (int l = 0; l < 9; l++) { // BTCS: added 'int '
      a(new Slot(playerinventory, l, 8 + l * 18, 142));
    }
    
    a(this.craftInventory);
  }
  
  public void a(IInventory iinventory)
  {
    CraftingManager.getInstance().lastCraftView = getBukkitView();
    ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory);
    this.resultInventory.setItem(0, craftResult);
    if (this.listeners.size() < 1) {
      return;
    }
    
    EntityPlayer player = (EntityPlayer)this.listeners.get(0);
    player.netServerHandler.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, 0, craftResult));
  }
  
  public void a(EntityHuman entityhuman)
  {
    super.a(entityhuman);
    if (!this.c.isStatic) {
      for (int i = 0; i < 9; i++) {
        ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
        
        if (itemstack != null) {
          entityhuman.drop(itemstack);
        }
      }
    }
  }
  
  public boolean b(EntityHuman entityhuman) {
    if (!this.checkReachable) return true;
    return this.c.getTypeId(this.h, this.i, this.j) == Block.WORKBENCH.id;
  }
  
  public ItemStack a(int i) {
    ItemStack itemstack = null;
    Slot slot = (Slot)this.e.get(i);
    
    if ((slot != null) && (slot.c())) {
      ItemStack itemstack1 = slot.getItem();
      
      itemstack = itemstack1.cloneItemStack();
      if (i == 0) {
        if (!a(itemstack1, 10, 46, true)) {
          return null;
        }
        
        slot.a(itemstack1, itemstack);
      } else if ((i >= 10) && (i < 37)) {
        if (!a(itemstack1, 37, 46, false)) {
          return null;
        }
      } else if ((i >= 37) && (i < 46)) {
        if (!a(itemstack1, 10, 37, false)) {
          return null;
        }
      } else if (!a(itemstack1, 10, 46, false)) {
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
    CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
    this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
    return this.bukkitEntity;
  }
}
