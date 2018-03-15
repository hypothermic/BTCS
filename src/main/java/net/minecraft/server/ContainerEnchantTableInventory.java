package net.minecraft.server;

public class ContainerEnchantTableInventory extends ContainerEnchantTableSubcontainer
{
  public final ContainerEnchantTable enchantTable;
  
  ContainerEnchantTableInventory(ContainerEnchantTable containerenchanttable, String s, int i) {
    super(s, i);
    this.enchantTable = containerenchanttable;
    super.setMaxStackSize(1);
  }
  
  public int getMaxStackSize() {
    return super.getMaxStackSize();
  }
  
  public void update() {
    super.update();
    this.enchantTable.a(this);
  }
}
