package net.minecraft.server;

import net.minecraft.src.IRecipe;

public interface CraftingRecipe extends net.minecraft.src.IRecipe {

    boolean a(InventoryCrafting inventorycrafting);

    ItemStack b(InventoryCrafting inventorycrafting);

    int a();

    ItemStack b();

    org.bukkit.inventory.Recipe toBukkitRecipe(); // CraftBukkit
}
