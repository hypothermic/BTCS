package net.minecraft.server;



public class RecipesFood
{
  public void a(CraftingManager paramCraftingManager)
  {
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[] { Block.BROWN_MUSHROOM, Block.RED_MUSHROOM, Item.BOWL });
    

    paramCraftingManager.registerShapedRecipe(new ItemStack(Item.COOKIE, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(Item.INK_SACK, 1, 3), Character.valueOf('#'), Item.WHEAT });
    



    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.MELON), new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), Item.MELON });
    





    paramCraftingManager.registerShapedRecipe(new ItemStack(Item.MELON_SEEDS), new Object[] { "M", Character.valueOf('M'), Item.MELON });
    



    paramCraftingManager.registerShapedRecipe(new ItemStack(Item.PUMPKIN_SEEDS, 4), new Object[] { "M", Character.valueOf('M'), Block.PUMPKIN });
    



    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.FERMENTED_SPIDER_EYE), new Object[] { Item.SPIDER_EYE, Block.BROWN_MUSHROOM, Item.SUGAR });
    

    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.SPECKLED_MELON), new Object[] { Item.MELON, Item.GOLD_NUGGET });
    

    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.BLAZE_POWDER, 2), new Object[] { Item.BLAZE_ROD });
    

    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.MAGMA_CREAM), new Object[] { Item.BLAZE_POWDER, Item.SLIME_BALL });
  }
}
