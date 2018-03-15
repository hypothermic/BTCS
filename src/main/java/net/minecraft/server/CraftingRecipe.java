package net.minecraft.server;

import net.minecraft.src.IRecipe;
import org.bukkit.inventory.Recipe;

public abstract interface CraftingRecipe
  extends IRecipe
{
  public abstract boolean a(InventoryCrafting paramInventoryCrafting);
  
  public abstract ItemStack b(InventoryCrafting paramInventoryCrafting);
  
  public abstract int a();
  
  public abstract ItemStack b();
  
  public abstract Recipe toBukkitRecipe();
}
