package ee.core;

import ee.item.ItemLootBall;
import forge.IPickupHandler;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;

public class PickupHandler implements IPickupHandler
{
  public boolean onItemPickup(EntityHuman var1, EntityItem var2)
  {
    ItemStack var3 = var2.itemStack;
    
    if ((var3 != null) && (var3.count > 0))
    {
      if (!(var2.itemStack.getItem() instanceof ItemLootBall))
      {
        return true;
      }
      

      ItemLootBall var4 = (ItemLootBall)var3.getItem();
      ItemStack[] var5 = var4.getDroplist(var3);
      
      if (var5 != null)
      {

        EntityItem var6 = new EntityItem(var1.world);
        var6.setLocation(var1.locX, var1.locY, var1.locZ, 0.0F, 0.0F);
        
        if (var6 != null)
        {
          ItemStack[] var7 = var5;
          int var8 = var5.length;
          
          for (int var9 = 0; var9 < var8; var9++)
          {
            ItemStack var10 = var7[var9];
            
            if (var10 != null)
            {
              var6.itemStack = var10;
              
              if ((forge.ForgeHooks.onItemPickup(var1, var6)) && (!var1.inventory.pickup(var6.itemStack)))
              {
                var1.drop(var6.itemStack);
              }
            }
          }
        }
      }
      
      var3.count = 0;
      return false;
    }
    


    return false;
  }
}
