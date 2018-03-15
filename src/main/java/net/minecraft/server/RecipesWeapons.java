package net.minecraft.server;



public class RecipesWeapons
{
  private String[][] a = { { "X", "X", "#" } };
  




  private Object[][] b = { { Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.WOOD_SWORD, Item.STONE_SWORD, Item.IRON_SWORD, Item.DIAMOND_SWORD, Item.GOLD_SWORD } };
  


  public void a(CraftingManager paramCraftingManager)
  {
    for (int i = 0; i < this.b[0].length; i++) {
      Object localObject = this.b[0][i];
      
      for (int j = 0; j < this.b.length - 1; j++) {
        Item localItem = (Item)this.b[(j + 1)][i];
        paramCraftingManager.registerShapedRecipe(new ItemStack(localItem), new Object[] { this.a[j], Character.valueOf('#'), Item.STICK, Character.valueOf('X'), localObject });
      }
    }
    




    paramCraftingManager.registerShapedRecipe(new ItemStack(Item.BOW, 1), new Object[] { " #X", "# X", " #X", Character.valueOf('X'), Item.STRING, Character.valueOf('#'), Item.STICK });
    






    paramCraftingManager.registerShapedRecipe(new ItemStack(Item.ARROW, 4), new Object[] { "X", "#", "Y", Character.valueOf('Y'), Item.FEATHER, Character.valueOf('X'), Item.FLINT, Character.valueOf('#'), Item.STICK });
  }
}
