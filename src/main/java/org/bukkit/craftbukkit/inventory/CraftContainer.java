package org.bukkit.craftbukkit.inventory;

import java.util.List;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.Slot;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class CraftContainer extends net.minecraft.server.Container
{
  private InventoryView view;
  private InventoryType cachedType;
  private String cachedTitle;
  private int cachedSize;
  
  public CraftContainer(InventoryView view, int id)
  {
    this.view = view;
    this.windowId = id;
    
    IInventory top = ((CraftInventory)view.getTopInventory()).getInventory();
    IInventory bottom = ((CraftInventory)view.getBottomInventory()).getInventory();
    this.cachedType = view.getType();
    this.cachedTitle = view.getTitle();
    this.cachedSize = getSize();
    setupSlots(top, bottom);
  }
  
  public CraftContainer(final Inventory inventory, final HumanEntity player, int id) {
    this(new InventoryView() {
    	@Override 
      public Inventory getTopInventory() {
        return inventory;
      }
      
      public Inventory getBottomInventory()
      {
        return player.getInventory();
      }
      
      public HumanEntity getPlayer()
      {
        return player;
      }

      public InventoryType getType() { 
    	return inventory.getType(); 
      } 
    }, id);
  }
  


  public InventoryView getBukkitView()
  {
    return this.view;
  }
  
  private int getSize() {
    return this.view.getTopInventory().getSize();
  }
  
  public boolean b(EntityHuman entityhuman)
  {
    if ((this.cachedType == this.view.getType()) && (this.cachedSize == getSize()) && (this.cachedTitle.equals(this.view.getTitle()))) {
      return true;
    }
    


    boolean typeChanged = this.cachedType != this.view.getType();
    this.cachedType = this.view.getType();
    this.cachedTitle = this.view.getTitle();
    if ((this.view.getPlayer() instanceof CraftPlayer)) {
      CraftPlayer player = (CraftPlayer)this.view.getPlayer();
      int type = getNotchInventoryType(this.cachedType);
      IInventory top = ((CraftInventory)this.view.getTopInventory()).getInventory();
      IInventory bottom = ((CraftInventory)this.view.getBottomInventory()).getInventory();
      this.d.clear();
      this.e.clear();
      if (typeChanged) {
        setupSlots(top, bottom);
      }
      int size = getSize();
      player.getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet100OpenWindow(this.windowId, type, this.cachedTitle, size));
      player.updateInventory();
    }
    return true;
  }
  
  public static int getNotchInventoryType(InventoryType type) {
    int typeID;
    switch (type) {
    case WORKBENCH: 
      typeID = 1;
      break;
    case FURNACE: 
      typeID = 2;
      break;
    case DISPENSER: 
      typeID = 3;
      break;
    case ENCHANTING: 
      typeID = 4;
      break;
    case BREWING: 
      typeID = 5;
      break;
    default: 
      typeID = 0;
    }
    
    return typeID;
  }
  
  private void setupSlots(IInventory top, IInventory bottom) {
    switch (this.cachedType) {
    case CREATIVE: 
      break;
    case PLAYER: 
    case CHEST: 
      setupChest(top, bottom);
      break;
    case DISPENSER: 
      setupDispenser(top, bottom);
      break;
    case FURNACE: 
      setupFurnace(top, bottom);
      break;
    case WORKBENCH: 
    case CRAFTING: 
      setupWorkbench(top, bottom);
      break;
    case ENCHANTING: 
      setupEnchanting(top, bottom);
      break;
    case BREWING: 
      setupBrewing(top, bottom);
    }
  }
  
  private void setupChest(IInventory top, IInventory bottom)
  {
    int rows = top.getSize() / 9;
    


    int i = (rows - 4) * 18;
    int row; // BTCS: moved outside for loop
    for (row = 0; row < rows; row++) {
      for (int col = 0; col < 9; col++) {
        a(new Slot(top, col + row * 9, 8 + col * 18, 18 + row * 18));
      }
    }
    
    for (row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 103 + row * 18 + i));
      }
    }
    
    for (int col = 0; col < 9; col++) {
      a(new Slot(bottom, col, 8 + col * 18, 161 + i));
    }
  }
  

  private void setupWorkbench(IInventory top, IInventory bottom)
  {
    a(new Slot(top, 0, 124, 35));
    



    int row; // BTCS
    for (row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        a(new Slot(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
      }
    }
    
    for (row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
      }
    }
    
    for (int col = 0; col < 9; col++) {
      a(new Slot(bottom, col, 8 + col * 18, 142));
    }
  }
  

  private void setupFurnace(IInventory top, IInventory bottom)
  {
    a(new Slot(top, 0, 56, 17));
    a(new Slot(top, 1, 56, 53));
    a(new Slot(top, 2, 116, 35));
    



    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
      }
    }
    
    for (int col = 0; col < 9; col++) {
      a(new Slot(bottom, col, 8 + col * 18, 142));
    }
  }
  




  private void setupDispenser(IInventory top, IInventory bottom)
  {
	  int row; // BTCS
    for (row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        a(new Slot(top, col + row * 3, 61 + col * 18, 17 + row * 18));
      }
    }
    
    for (row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
      }
    }
    
    for (int col = 0; col < 9; col++) {
      a(new Slot(bottom, col, 8 + col * 18, 142));
    }
  }
  

  private void setupEnchanting(IInventory top, IInventory bottom)
  {
    a(new Slot(top, 0, 25, 47));
    


    int row; // BTCS
    for (row = 0; row < 3; row++) {
      for (int i1 = 0; i1 < 9; i1++) {
        a(new Slot(bottom, i1 + row * 9 + 9, 8 + i1 * 18, 84 + row * 18));
      }
    }
    
    for (row = 0; row < 9; row++) {
      a(new Slot(bottom, row, 8 + row * 18, 142));
    }
  }
  

  private void setupBrewing(IInventory top, IInventory bottom)
  {
    a(new Slot(top, 0, 56, 46));
    a(new Slot(top, 1, 79, 53));
    a(new Slot(top, 2, 102, 46));
    a(new Slot(top, 3, 79, 17));
    


    int i; // BTCS
    for (i = 0; i < 3; i++) {
      for (int j = 0; j < 9; j++) {
        a(new Slot(bottom, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }
    
    for (i = 0; i < 9; i++) {
      a(new Slot(bottom, i, 8 + i * 18, 142));
    }
  }
}
