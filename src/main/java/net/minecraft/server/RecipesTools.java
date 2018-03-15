package net.minecraft.server;





public class RecipesTools
{
  private String[][] a = { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" }, { "XX", " #", " #" } };
  
















  private Object[][] b = { { Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.WOOD_PICKAXE, Item.STONE_PICKAXE, Item.IRON_PICKAXE, Item.DIAMOND_PICKAXE, Item.GOLD_PICKAXE }, { Item.WOOD_SPADE, Item.STONE_SPADE, Item.IRON_SPADE, Item.DIAMOND_SPADE, Item.GOLD_SPADE }, { Item.WOOD_AXE, Item.STONE_AXE, Item.IRON_AXE, Item.DIAMOND_AXE, Item.GOLD_AXE }, { Item.WOOD_HOE, Item.STONE_HOE, Item.IRON_HOE, Item.DIAMOND_HOE, Item.GOLD_HOE } };
  






  public void a(CraftingManager paramCraftingManager)
  {
    for (int i = 0; i < this.b[0].length; i++) {
      Object localObject = this.b[0][i];
      
      for (int j = 0; j < this.b.length - 1; j++) {
        Item localItem = (Item)this.b[(j + 1)][i];
        paramCraftingManager.registerShapedRecipe(new ItemStack(localItem), new Object[] { this.a[j], Character.valueOf('#'), Item.STICK, Character.valueOf('X'), localObject });
      }
    }
    


    paramCraftingManager.registerShapedRecipe(new ItemStack(Item.SHEARS), new Object[] { " #", "# ", Character.valueOf('#'), Item.IRON_INGOT });
  }
}
