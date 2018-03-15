package net.minecraft.server;


public class RecipesCrafting
{
  public void a(CraftingManager paramCraftingManager)
  {
    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.CHEST), new Object[] { "###", "# #", "###", Character.valueOf('#'), Block.WOOD });
    





    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.FURNACE), new Object[] { "###", "# #", "###", Character.valueOf('#'), Block.COBBLESTONE });
    





    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.WORKBENCH), new Object[] { "##", "##", Character.valueOf('#'), Block.WOOD });
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE), new Object[] { "##", "##", Character.valueOf('#'), Block.SAND });
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE, 4, 2), new Object[] { "##", "##", Character.valueOf('#'), Block.SANDSTONE });
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE, 1, 1), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Block.STEP, 1, 1) });
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.SMOOTH_BRICK, 4), new Object[] { "##", "##", Character.valueOf('#'), Block.STONE });
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.IRON_FENCE, 16), new Object[] { "###", "###", Character.valueOf('#'), Item.IRON_INGOT });
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.THIN_GLASS, 16), new Object[] { "###", "###", Character.valueOf('#'), Block.GLASS });
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Block.REDSTONE_LAMP_OFF, 1), new Object[] { " R ", "RGR", " R ", Character.valueOf('R'), Item.REDSTONE, Character.valueOf('G'), Block.GLOWSTONE });
  }
}
