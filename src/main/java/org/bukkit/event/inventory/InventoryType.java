package org.bukkit.event.inventory;



public enum InventoryType
{
  CHEST(27, "Chest"), 
  


  DISPENSER(9, "Dispenser"), 
  


  FURNACE(3, "Furnace"), 
  


  WORKBENCH(10, "Crafting"), 
  



  CRAFTING(5, "Crafting"), 
  


  ENCHANTING(1, "Enchanting"), 
  


  BREWING(4, "Brewing"), 
  



  PLAYER(36, "Player"), 
  



  CREATIVE(9, "Creative"), 
  


  MOD(0, "Mod Inventory");
  
  private final int size;
  private final String title;
  
  private InventoryType(int defaultSize, String defaultTitle) { this.size = defaultSize;
    this.title = defaultTitle;
  }
  
  public int getDefaultSize() {
    return this.size;
  }
  
  public String getDefaultTitle() {
    return this.title;
  }
  


  public static enum SlotType
  {
    RESULT, 
    



    CRAFTING, 
    


    ARMOR, 
    



    CONTAINER, 
    


    QUICKBAR, 
    


    OUTSIDE, 
    


    FUEL;
    
    private SlotType() {}
  }
}
