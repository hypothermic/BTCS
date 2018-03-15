package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;


public class ContainerEnchantTableSubcontainer
  implements IInventory
{
  private String a;
  private int b;
  private ItemStack[] items;
  private List d;
  public List<HumanEntity> transaction = new ArrayList();
  public Player player;
  private int maxStack = 64;
  
  public ItemStack[] getContents() {
    return this.items;
  }
  
  public void onOpen(CraftHumanEntity who) {
    this.transaction.add(who);
  }
  
  public void onClose(CraftHumanEntity who) {
    this.transaction.remove(who);
  }
  
  public List<HumanEntity> getViewers() {
    return this.transaction;
  }
  
  public InventoryHolder getOwner() {
    return this.player;
  }
  
  public void setMaxStackSize(int size) {
    this.maxStack = size;
  }
  
  public ContainerEnchantTableSubcontainer(String s, int i)
  {
    this.a = s;
    this.b = i;
    this.items = new ItemStack[i];
  }
  
  public ItemStack getItem(int i) {
    return this.items[i];
  }
  
  public ItemStack splitStack(int i, int j) {
    if (this.items[i] != null)
    {

      if (this.items[i].count <= j) {
        ItemStack itemstack = this.items[i];
        this.items[i] = null;
        update();
        return itemstack;
      }
      ItemStack itemstack = this.items[i].a(j);
      if (this.items[i].count == 0) {
        this.items[i] = null;
      }
      
      update();
      return itemstack;
    }
    
    return null;
  }
  
  public ItemStack splitWithoutUpdate(int i)
  {
    if (this.items[i] != null) {
      ItemStack itemstack = this.items[i];
      
      this.items[i] = null;
      return itemstack;
    }
    return null;
  }
  
  public void setItem(int i, ItemStack itemstack)
  {
    this.items[i] = itemstack;
    if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
      itemstack.count = getMaxStackSize();
    }
    
    update();
  }
  
  public int getSize() {
    return this.b;
  }
  
  public String getName() {
    return this.a;
  }
  
  public int getMaxStackSize() {
    return this.maxStack;
  }
  
  public void update() {
    if (this.d != null) {
      for (int i = 0; i < this.d.size(); i++) {
        ((IInventoryListener)this.d.get(i)).a(this);
      }
    }
  }
  
  public boolean a(EntityHuman entityhuman) {
    return true;
  }
  
  public void f() {}
  
  public void g() {}
}
