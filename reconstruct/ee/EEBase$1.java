package ee;

import forge.ICraftingHandler;
import java.util.HashSet;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
class EEBase$1
  implements ICraftingHandler
{
  public void onTakenFromCrafting(EntityHuman var1, ItemStack var2, IInventory var3)
  {
    int var4 = 0;
    

    if ((var2 != null) && (EEMergeLib.mergeOnCraft.contains(Integer.valueOf(var2.id))))
    {
      for (int var5 = 0; var5 < var3.getSize(); var5++)
      {
        ItemStack var6 = var3.getItem(var5);
        
        if ((var6 != null) && ((var6.getItem() instanceof ItemKleinStar)) && (((ItemKleinStar)var6.getItem()).getKleinPoints(var6) > 0))
        {
          var4 += ((ItemKleinStar)var6.getItem()).getKleinPoints(var6);
        }
      }
      
      ((ItemKleinStar)var2.getItem()).setKleinPoints(var2, var4);
    }
    else if ((var2 != null) && (EEMergeLib.destroyOnCraft.contains(Integer.valueOf(var2.id))) && (var2.id == EEItem.arcaneRing.id))
    {
      for (int var5 = 0; var5 < var3.getSize(); var5++)
      {
        var3.setItem(var5, null);
      }
    }
  }
}
