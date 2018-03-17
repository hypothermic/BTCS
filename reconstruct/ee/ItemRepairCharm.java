package ee;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.World;

public class ItemRepairCharm extends ItemModEE
{
  public static Item[] repairItemArray = new Item[49];
  public static int[] covalenceValueArray = new int[49];
  
  public ItemRepairCharm(int var1)
  {
    super(var1);
    this.maxStackSize = 1;
  }
  




  public void a(ItemStack var1, World var2, net.minecraft.server.Entity var3, int var4, boolean var5)
  {
    if ((var3 instanceof EntityHuman))
    {
      doRepair((EntityHuman)var3, var2);
    }
  }
  




  public void doRepair(EntityHuman var1, World var2)
  {
    for (int var3 = 0; var3 < var1.inventory.items.length; var3++)
    {
      if (var1.inventory.items[var3] != null)
      {
        ItemStack var4 = null;
        
        for (int var5 = 0; var5 < repairItemArray.length; var5++)
        {
          if (var1.inventory.items[var3].getItem() == repairItemArray[var5])
          {
            var4 = var1.inventory.items[var3];
            
            if ((var4.getData() >= covalenceValueArray[var5]) && (ConsumeMaterial(var1, new ItemStack(EEItem.covalenceDust, 1, getCovalenceType(var5)))))
            {
              var4.setData(var4.getData() - covalenceValueArray[var5]);
              break;
            }
          }
        }
      }
    }
    
    for (var3 = 0; var3 < var1.inventory.armor.length; var3++)
    {
      if (var1.inventory.armor[var3] != null)
      {
        ItemStack var4 = null;
        
        for (int var5 = 0; var5 < repairItemArray.length; var5++)
        {
          if (var1.inventory.armor[var3].getItem() == repairItemArray[var5])
          {
            var4 = var1.inventory.armor[var3];
            
            if ((var4.getData() >= covalenceValueArray[var5]) && (ConsumeMaterial(var1, new ItemStack(EEItem.covalenceDust, 1, getCovalenceType(var5)))))
            {
              var4.setData(var4.getData() - covalenceValueArray[var5]);
              break;
            }
          }
        }
      }
    }
  }
  
  public int getCovalenceType(int var1)
  {
    return (var1 > 9) && ((var1 < 25) || (var1 > 28)) && (var1 != 46) ? 1 : ((var1 < 10) || (var1 > 19)) && ((var1 < 29) || (var1 > 40)) && (var1 != 45) && (var1 != 47) ? 2 : ((var1 < 20) || (var1 > 24)) && ((var1 < 41) || (var1 > 44)) ? 0 : 0;
  }
  
  public boolean ConsumeMaterial(EntityHuman var1, ItemStack var2)
  {
    return EEBase.Consume(var2, var1, false);
  }
  
  static
  {
    repairItemArray[0] = Item.WOOD_PICKAXE;
    covalenceValueArray[0] = 20;
    repairItemArray[1] = Item.WOOD_SPADE;
    covalenceValueArray[1] = 50;
    repairItemArray[2] = Item.WOOD_SWORD;
    covalenceValueArray[2] = 30;
    repairItemArray[3] = Item.WOOD_AXE;
    covalenceValueArray[3] = 20;
    repairItemArray[4] = Item.WOOD_HOE;
    covalenceValueArray[4] = 30;
    repairItemArray[5] = Item.STONE_PICKAXE;
    covalenceValueArray[5] = 44;
    repairItemArray[6] = Item.STONE_SPADE;
    covalenceValueArray[6] = 120;
    repairItemArray[7] = Item.STONE_SWORD;
    covalenceValueArray[7] = 65;
    repairItemArray[8] = Item.STONE_AXE;
    covalenceValueArray[8] = 44;
    repairItemArray[9] = Item.STONE_HOE;
    covalenceValueArray[9] = 65;
    repairItemArray[10] = Item.IRON_PICKAXE;
    covalenceValueArray[10] = 85;
    repairItemArray[11] = Item.IRON_SPADE;
    covalenceValueArray[11] = 250;
    repairItemArray[12] = Item.IRON_SWORD;
    covalenceValueArray[12] = 125;
    repairItemArray[13] = Item.IRON_AXE;
    covalenceValueArray[13] = 85;
    repairItemArray[14] = Item.IRON_HOE;
    covalenceValueArray[14] = 125;
    repairItemArray[15] = Item.GOLD_PICKAXE;
    covalenceValueArray[15] = 10;
    repairItemArray[16] = Item.GOLD_SPADE;
    covalenceValueArray[16] = 25;
    repairItemArray[17] = Item.GOLD_SWORD;
    covalenceValueArray[17] = 15;
    repairItemArray[18] = Item.GOLD_AXE;
    covalenceValueArray[18] = 10;
    repairItemArray[19] = Item.GOLD_HOE;
    covalenceValueArray[19] = 15;
    repairItemArray[20] = Item.DIAMOND_PICKAXE;
    covalenceValueArray[20] = 520;
    repairItemArray[21] = Item.DIAMOND_SPADE;
    covalenceValueArray[21] = 1550;
    repairItemArray[22] = Item.DIAMOND_SWORD;
    covalenceValueArray[22] = 785;
    repairItemArray[23] = Item.DIAMOND_AXE;
    covalenceValueArray[23] = 520;
    repairItemArray[24] = Item.DIAMOND_HOE;
    covalenceValueArray[24] = 785;
    repairItemArray[25] = Item.LEATHER_HELMET;
    covalenceValueArray[25] = 8;
    repairItemArray[26] = Item.LEATHER_CHESTPLATE;
    covalenceValueArray[26] = 6;
    repairItemArray[27] = Item.LEATHER_LEGGINGS;
    covalenceValueArray[27] = 7;
    repairItemArray[28] = Item.LEATHER_BOOTS;
    covalenceValueArray[28] = 7;
    repairItemArray[29] = Item.IRON_HELMET;
    covalenceValueArray[29] = 31;
    repairItemArray[30] = Item.IRON_CHESTPLATE;
    covalenceValueArray[30] = 24;
    repairItemArray[31] = Item.IRON_LEGGINGS;
    covalenceValueArray[31] = 26;
    repairItemArray[32] = Item.IRON_BOOTS;
    covalenceValueArray[32] = 33;
    repairItemArray[33] = Item.CHAINMAIL_HELMET;
    covalenceValueArray[33] = 16;
    repairItemArray[34] = Item.CHAINMAIL_CHESTPLATE;
    covalenceValueArray[34] = 12;
    repairItemArray[35] = Item.CHAINMAIL_LEGGINGS;
    covalenceValueArray[35] = 13;
    repairItemArray[36] = Item.CHAINMAIL_BOOTS;
    covalenceValueArray[36] = 16;
    repairItemArray[37] = Item.GOLD_HELMET;
    covalenceValueArray[37] = 16;
    repairItemArray[38] = Item.GOLD_CHESTPLATE;
    covalenceValueArray[38] = 12;
    repairItemArray[39] = Item.GOLD_LEGGINGS;
    covalenceValueArray[39] = 13;
    repairItemArray[40] = Item.GOLD_BOOTS;
    covalenceValueArray[40] = 16;
    repairItemArray[41] = Item.DIAMOND_HELMET;
    covalenceValueArray[41] = 78;
    repairItemArray[42] = Item.DIAMOND_CHESTPLATE;
    covalenceValueArray[42] = 46;
    repairItemArray[43] = Item.DIAMOND_LEGGINGS;
    covalenceValueArray[43] = 52;
    repairItemArray[44] = Item.DIAMOND_BOOTS;
    covalenceValueArray[44] = 64;
    repairItemArray[45] = Item.FLINT_AND_STEEL;
    covalenceValueArray[45] = 60;
    repairItemArray[46] = Item.FISHING_ROD;
    covalenceValueArray[46] = 60;
    repairItemArray[47] = Item.SHEARS;
    covalenceValueArray[47] = 235;
    repairItemArray[48] = Item.BOW;
    covalenceValueArray[48] = 128;
  }
}
