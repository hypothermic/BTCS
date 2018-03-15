package net.minecraft.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FurnaceRecipes
{
  private static final FurnaceRecipes a = new FurnaceRecipes();
  public Map recipies = new HashMap();
  private Map metaSmeltingList = new HashMap();
  
  public static final FurnaceRecipes getInstance() {
    return a;
  }
  
  public FurnaceRecipes() {
    registerRecipe(Block.IRON_ORE.id, new ItemStack(Item.IRON_INGOT));
    registerRecipe(Block.GOLD_ORE.id, new ItemStack(Item.GOLD_INGOT));
    registerRecipe(Block.DIAMOND_ORE.id, new ItemStack(Item.DIAMOND));
    registerRecipe(Block.SAND.id, new ItemStack(Block.GLASS));
    registerRecipe(Item.PORK.id, new ItemStack(Item.GRILLED_PORK));
    registerRecipe(Item.RAW_BEEF.id, new ItemStack(Item.COOKED_BEEF));
    registerRecipe(Item.RAW_CHICKEN.id, new ItemStack(Item.COOKED_CHICKEN));
    registerRecipe(Item.RAW_FISH.id, new ItemStack(Item.COOKED_FISH));
    registerRecipe(Block.COBBLESTONE.id, new ItemStack(Block.STONE));
    registerRecipe(Item.CLAY_BALL.id, new ItemStack(Item.CLAY_BRICK));
    registerRecipe(Block.CACTUS.id, new ItemStack(Item.INK_SACK, 1, 2));
    registerRecipe(Block.LOG.id, new ItemStack(Item.COAL, 1, 1));
    registerRecipe(Block.COAL_ORE.id, new ItemStack(Item.COAL));
    registerRecipe(Block.REDSTONE_ORE.id, new ItemStack(Item.REDSTONE));
    registerRecipe(Block.LAPIS_ORE.id, new ItemStack(Item.INK_SACK, 1, 4));
  }
  
  public void registerRecipe(int i, ItemStack itemstack) {
    this.recipies.put(Integer.valueOf(i), itemstack);
  }
  
  @Deprecated
  public ItemStack getResult(int i) {
    return (ItemStack)this.recipies.get(Integer.valueOf(i));
  }
  
  public Map getRecipies() {
    return this.recipies;
  }
  









  public void addSmelting(int itemID, int metadata, ItemStack itemstack)
  {
    this.metaSmeltingList.put(Arrays.asList(new Integer[] { Integer.valueOf(itemID), Integer.valueOf(metadata) }), itemstack);
  }
  






  public ItemStack getSmeltingResult(ItemStack item)
  {
    if (item == null) {
      return null;
    }
    ItemStack ret = (ItemStack)this.metaSmeltingList.get(Arrays.asList(new Integer[] { Integer.valueOf(item.id), Integer.valueOf(item.getData()) }));
    if (ret != null) {
      return ret;
    }
    return (ItemStack)this.recipies.get(Integer.valueOf(item.id));
  }
}
