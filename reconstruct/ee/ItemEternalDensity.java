package ee;

import net.minecraft.server.Block;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.World;

public class ItemEternalDensity extends ItemEECharged
{
  public ItemEternalDensity(int var1)
  {
    super(var1, 0);
    this.maxStackSize = 1;
  }
  
  public int getIconFromDamage(int var1)
  {
    return !isActivated(var1) ? this.textureId : this.textureId + 1;
  }
  
  public boolean roomFor(ItemStack var1, EntityHuman var2)
  {
    if (var1 == null)
    {
      return false;
    }
    

    for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
    {
      if (var2.inventory.items[var3] == null)
      {
        return true;
      }
      
      if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
      {
        return true;
      }
    }
    
    return false;
  }
  

  public void PushStack(ItemStack var1, EntityHuman var2)
  {
    if (var1 != null)
    {
      for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
      {
        if (var2.inventory.items[var3] == null)
        {
          var2.inventory.items[var3] = var1.cloneItemStack();
          var1 = null;
          return;
        }
        
        if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
        {
          var2.inventory.items[var3].count += var1.count;
          var1 = null;
          return;
        }
        
        if (var2.inventory.items[var3].doMaterialsMatch(var1))
        {
          while ((var2.inventory.items[var3].count < var2.inventory.items[var3].getMaxStackSize()) && (var1 != null))
          {
            var2.inventory.items[var3].count += 1;
            var1.count -= 1;
            
            if (var1.count <= 0)
            {
              var1 = null;
              return;
            }
          }
        }
      }
    }
  }
  
  private void dumpContents(ItemStack var1, EntityHuman var2)
  {
    for (;;)
    {
      takeEMC(var1, EEMaps.getEMC(EEItem.redMatter.id));
      PushStack(new ItemStack(EEItem.redMatter, 1), var2);
      if (emc(var1) >= EEMaps.getEMC(EEItem.redMatter.id)) { if (!roomFor(new ItemStack(EEItem.redMatter, 1), var2)) {
          break;
        }
      }
    }
    
    for (;;)
    {
      takeEMC(var1, EEMaps.getEMC(EEItem.darkMatter.id));
      PushStack(new ItemStack(EEItem.darkMatter, 1), var2);
      if (emc(var1) >= EEMaps.getEMC(EEItem.darkMatter.id)) { if (!roomFor(new ItemStack(EEItem.darkMatter, 1), var2)) {
          break;
        }
      }
    }
    
    for (;;)
    {
      takeEMC(var1, EEMaps.getEMC(Item.DIAMOND.id));
      PushStack(new ItemStack(Item.DIAMOND, 1), var2);
      if (emc(var1) >= EEMaps.getEMC(Item.DIAMOND.id)) { if (!roomFor(new ItemStack(Item.DIAMOND, 1), var2)) {
          break;
        }
      }
    }
    
    for (;;)
    {
      takeEMC(var1, EEMaps.getEMC(Item.GOLD_INGOT.id));
      PushStack(new ItemStack(Item.GOLD_INGOT, 1), var2);
      if (emc(var1) >= EEMaps.getEMC(Item.GOLD_INGOT.id)) { if (!roomFor(new ItemStack(Item.GOLD_INGOT, 1), var2)) {
          break;
        }
      }
    }
    
    do
    {
      takeEMC(var1, EEMaps.getEMC(Item.IRON_INGOT.id));
      PushStack(new ItemStack(Item.IRON_INGOT, 1), var2);
      if (emc(var1) < EEMaps.getEMC(Item.IRON_INGOT.id)) break; } while (roomFor(new ItemStack(Item.IRON_INGOT, 1), var2));
    




    while ((emc(var1) >= EEMaps.getEMC(Block.COBBLESTONE.id)) && (roomFor(new ItemStack(Block.COBBLESTONE, 1), var2)))
    {
      takeEMC(var1, EEMaps.getEMC(Block.COBBLESTONE.id));
      PushStack(new ItemStack(Block.COBBLESTONE, 1), var2);
    }
  }
  
  public ItemStack target(ItemStack var1)
  {
    return getInteger(var1, "targetID") != 0 ? new ItemStack(getInteger(var1, "targetID"), 1, 0) : getInteger(var1, "targetMeta") != 0 ? new ItemStack(getInteger(var1, "targetID"), 1, getInteger(var1, "targetMeta")) : null;
  }
  
  public ItemStack product(ItemStack var1)
  {
    if (target(var1) != null)
    {
      int var2 = EEMaps.getEMC(target(var1));
      
      if (var2 < EEMaps.getEMC(Item.IRON_INGOT.id))
      {
        return new ItemStack(Item.IRON_INGOT, 1);
      }
      
      if (var2 < EEMaps.getEMC(Item.GOLD_INGOT.id))
      {
        return new ItemStack(Item.GOLD_INGOT, 1);
      }
      
      if (var2 < EEMaps.getEMC(Item.DIAMOND.id))
      {
        return new ItemStack(Item.DIAMOND, 1);
      }
      
      if (var2 < EEMaps.getEMC(EEItem.darkMatter.id))
      {
        return new ItemStack(EEItem.darkMatter, 1);
      }
      
      if (var2 < EEMaps.getEMC(EEItem.redMatter.id))
      {
        return new ItemStack(EEItem.redMatter, 1);
      }
    }
    
    return null;
  }
  
  public void doCondense(ItemStack var1, World var2, EntityHuman var3)
  {
    if (!EEProxy.isClient(var2))
    {
      if ((product(var1) != null) && (emc(var1) >= EEMaps.getEMC(product(var1))) && (roomFor(product(var1), var3)))
      {
        PushStack(product(var1), var3);
        takeEMC(var1, EEMaps.getEMC(product(var1)));
      }
      
      int var4 = 0;
      ItemStack[] var5 = var3.inventory.items;
      int var6 = var5.length;
      


      for (int var7 = 0; var7 < var6; var7++)
      {
        ItemStack var8 = var5[var7];
        
        if ((var8 != null) && (EEMaps.getEMC(var8) != 0) && (isValidMaterial(var8, var3)) && (EEMaps.getEMC(var8) > var4))
        {
          var4 = EEMaps.getEMC(var8);
        }
      }
      
      var5 = var3.inventory.items;
      var6 = var5.length;
      
      for (var7 = 0; var7 < var6; var7++)
      {
        ItemStack var8 = var5[var7];
        
        if ((var8 != null) && (EEMaps.getEMC(var8) != 0) && (isValidMaterial(var8, var3)) && (EEMaps.getEMC(var8) <= var4))
        {
          var4 = EEMaps.getEMC(var8);
          setInteger(var1, "targetID", var8.id);
          setInteger(var1, "targetMeta", var8.getData());
        }
      }
      
      if (target(var1) != null)
      {
        if (ConsumeMaterial(target(var1), var3))
        {
          addEMC(var1, EEMaps.getEMC(target(var1)));
        }
      }
    }
  }
  
  private boolean isLastCobbleStack(EntityHuman var1)
  {
    int var2 = 0;
    
    for (int var3 = 0; var3 < var1.inventory.items.length; var3++)
    {
      if ((var1.inventory.items[var3] != null) && (var1.inventory.items[var3].id == Block.COBBLESTONE.id))
      {
        var2 += var1.inventory.items[var3].count;
      }
    }
    
    if (var2 <= 64)
    {
      return true;
    }
    

    return false;
  }
  

  private boolean isValidMaterial(ItemStack var1, EntityHuman var2)
  {
    if (EEMaps.getEMC(var1) == 0)
    {
      return false;
    }
    if ((var1.id == Block.COBBLESTONE.id) && (isLastCobbleStack(var2)))
    {
      return false;
    }
    

    int var3 = var1.id;
    
    if (var3 >= Block.byId.length)
    {
      if ((var3 != Item.IRON_INGOT.id) && (var3 != Item.GOLD_INGOT.id) && (var3 != Item.DIAMOND.id) && (var3 != EEItem.darkMatter.id))
      {
        return false;
      }
      
      if (var3 == EEItem.redMatter.id)
      {
        return false;
      }
    }
    
    return !EEMaps.isFuel(var1);
  }
  

  private int emc(ItemStack var1)
  {
    return getInteger(var1, "emc");
  }
  
  private void takeEMC(ItemStack var1, int var2)
  {
    setInteger(var1, "emc", emc(var1) - var2);
  }
  
  private void addEMC(ItemStack var1, int var2)
  {
    setInteger(var1, "emc", emc(var1) + var2);
  }
  
  public boolean ConsumeMaterial(ItemStack var1, EntityHuman var2)
  {
    return EEBase.Consume(var1, var2, false);
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
  }
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (!isActivated(var1.getData()))
    {
      dumpContents(var1, var3);
    }
  }
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    doCondense(var1, var2, var3);
  }
  
  public boolean canActivate()
  {
    return true;
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
}
