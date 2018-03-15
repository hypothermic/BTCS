package net.minecraft.server;

import java.util.List;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public abstract interface IInventory
{
  public static final int MAX_STACK = 64;
  
  public abstract int getSize();
  
  public abstract ItemStack getItem(int paramInt);
  
  public abstract ItemStack splitStack(int paramInt1, int paramInt2);
  
  public abstract ItemStack splitWithoutUpdate(int paramInt);
  
  public abstract void setItem(int paramInt, ItemStack paramItemStack);
  
  public abstract String getName();
  
  public abstract int getMaxStackSize();
  
  public abstract void update();
  
  public abstract boolean a(EntityHuman paramEntityHuman);
  
  public abstract void f();
  
  public abstract void g();
  
  public abstract ItemStack[] getContents();
  
  public abstract void onOpen(CraftHumanEntity paramCraftHumanEntity);
  
  public abstract void onClose(CraftHumanEntity paramCraftHumanEntity);
  
  public abstract List<HumanEntity> getViewers();
  
  public abstract InventoryHolder getOwner();
  
  public abstract void setMaxStackSize(int paramInt);
}
