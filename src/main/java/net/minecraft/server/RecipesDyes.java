package net.minecraft.server;








public class RecipesDyes
{
  public void a(CraftingManager paramCraftingManager)
  {
    for (int i = 0; i < 16; i++)
    {
      paramCraftingManager.registerShapelessRecipe(new ItemStack(Block.WOOL, 1, BlockCloth.e(i)), new Object[] { new ItemStack(Item.INK_SACK, 1, i), new ItemStack(Item.byId[Block.WOOL.id], 1, 0) });
    }
    


    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 11), new Object[] { Block.YELLOW_FLOWER });
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 1), new Object[] { Block.RED_ROSE });
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 15), new Object[] { Item.BONE });
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 9), new Object[] { new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 15) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 14), new Object[] { new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 11) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 10), new Object[] { new ItemStack(Item.INK_SACK, 1, 2), new ItemStack(Item.INK_SACK, 1, 15) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 8), new Object[] { new ItemStack(Item.INK_SACK, 1, 0), new ItemStack(Item.INK_SACK, 1, 15) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 7), new Object[] { new ItemStack(Item.INK_SACK, 1, 8), new ItemStack(Item.INK_SACK, 1, 15) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 7), new Object[] { new ItemStack(Item.INK_SACK, 1, 0), new ItemStack(Item.INK_SACK, 1, 15), new ItemStack(Item.INK_SACK, 1, 15) });
    




    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 12), new Object[] { new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 15) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 6), new Object[] { new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 2) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 5), new Object[] { new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 1) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 13), new Object[] { new ItemStack(Item.INK_SACK, 1, 5), new ItemStack(Item.INK_SACK, 1, 9) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 13), new Object[] { new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 9) });
    
    paramCraftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 4, 13), new Object[] { new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 15) });
  }
}
