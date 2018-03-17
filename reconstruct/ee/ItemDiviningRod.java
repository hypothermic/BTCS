package ee;

import java.util.ArrayList;
import net.minecraft.server.Block;
import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemDiviningRod extends ItemEECharged
{
  protected ItemDiviningRod(int var1)
  {
    super(var1, 0);
    a(true);
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (EEProxy.isClient(var3))
    {
      return false;
    }
    if (getShort(var1, "cooldown") <= 0)
    {
      if (getFuelRemaining(var1) < (getMode(var1) == 1 ? 4 : getMode(var1) == 0 ? 2 : 8))
      {
        ConsumeReagent(var1, var2, true);
        
        if (getFuelRemaining(var1) < (getMode(var1) == 1 ? 4 : getMode(var1) == 0 ? 2 : 8))
        {
          ConsumeReagent(var1, var2, true);
        }
      }
      
      int var8 = getMode(var1) == 1 ? 16 : getMode(var1) == 0 ? 3 : 64;
      doDivining(var1, var2, var8, var4, var5, var6, var7);
      setShort(var1, "cooldown", 60 / (var1.getData() + 1));
      return true;
    }
    

    return false;
  }
  





  public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
  {
    if (getShort(var1, "cooldown") > 0)
    {
      setShort(var1, "cooldown", getShort(var1, "cooldown") - 1);
    }
  }
  
  public void addCreativeItems(ArrayList var1)
  {
    var1.add(new ItemStack(EEItem.diviningRod, 1, 0));
    var1.add(new ItemStack(EEItem.diviningRod, 1, 1));
    var1.add(new ItemStack(EEItem.diviningRod, 1, 2));
  }
  
  public void doDivining(ItemStack var1, EntityHuman var2, int var3, int var4, int var5, int var6, int var7)
  {
    setFuelRemaining(var1, getFuelRemaining(var1) - (getMode(var1) == 1 ? 4 : getMode(var1) == 0 ? 2 : 8));
    float var8 = 0.0F;
    int var9 = 0;
    boolean var10 = false;
    boolean var11 = false;
    int var12 = 0;
    int var13 = 0;
    int var14 = 0;
    int var15 = 0;
    int var16 = 0;
    World var17 = var2.world;
    
    for (int var18 = -1 * (var7 == 5 ? var3 : 1); var18 <= 1 * (var7 == 4 ? var3 : 1); var18++)
    {
      for (int var19 = -1 * (var7 == 1 ? var3 : 1); var19 <= 1 * (var7 == 0 ? var3 : 1); var19++)
      {
        for (int var20 = -1 * (var7 == 3 ? var3 : 1); var20 <= 1 * (var7 == 2 ? var3 : 1); var20++)
        {
          int var23 = var17.getTypeId(var4 + var18, var5 + var19, var6 + var20);
          int var22 = var17.getData(var4 + var18, var5 + var19, var6 + var20);
          
          if (EEMaps.getEMC(var23, var22) > 0)
          {
            if (EEMaps.getEMC(var23, var22) > var12)
            {
              var13 = var12;
              var12 = EEMaps.getEMC(var23, var22);
            }
            
            if (var13 > var14)
            {
              var15 = var14;
              var14 = var13;
            }
            
            if (var15 > var16)
            {
              var16 = var15;
            }
            
            var8 += EEMaps.getEMC(var23, var22);
            var9++;
          }
          else if ((Block.byId[var23] != null) && (EEMaps.getEMC(Block.byId[var23].getDropType(var23, var17.random, 0), var22) * Block.byId[var23].quantityDropped(var22, 0, var17.random) > 0))
          {
            int var26 = EEMaps.getEMC(Block.byId[var23].getDropType(var23, var17.random, 0), var22) * Block.byId[var23].quantityDropped(var22, 0, var17.random);
            
            if (var26 > var12)
            {
              var13 = var12;
              var12 = var26;
            }
            
            if (var13 > var14)
            {
              var15 = var14;
              var14 = var13;
            }
            
            if (var15 > var16)
            {
              var16 = var15;
            }
            
            var8 += var26;
            var9++;
          }
          else if (FurnaceRecipes.getInstance().getSmeltingResult(new ItemStack(var23, 1, var22)) != null)
          {
            ItemStack var21 = FurnaceRecipes.getInstance().getSmeltingResult(new ItemStack(var23, 1, var22));
            
            if (EEMaps.getEMC(var21.id, var21.getData()) != 0)
            {
              if (EEMaps.getEMC(var21.id, var21.getData()) > var12)
              {
                var13 = var12;
                var12 = EEMaps.getEMC(var21.id, var21.getData());
              }
              
              if (var13 > var14)
              {
                var15 = var14;
                var14 = var13;
              }
              
              if (var15 > var16)
              {
                var16 = var15;
              }
              
              var8 += EEMaps.getEMC(var21.id, var21.getData());
              var9++;
            }
          }
        }
      }
    }
    
    String var24 = "Divining suggests a value around... " + Math.floor(var8 / var9);
    String var25 = "";
    
    if (var1.getData() > 0)
    {
      var25 = " Best found: " + var12 + (var1.getData() == 2 ? " Second: " + var14 + " Third: " + var16 : "");
    }
    
    var2.a(var24);
    
    if (var25 != "")
    {
      var2.a(var25);
    }
  }
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
  {
    if (var1.getData() > 0)
    {
      changeModes(var1);
    }
    
    var3.a(getMode(var1) == 1 ? "Divining rod mid range (16x3x3)" : getMode(var1) == 0 ? "Divining rod short range (3x3x3)" : "Divining rod long range (64x3x3)");
  }
  
  public void changeModes(ItemStack var1)
  {
    if (var1.getData() > 1)
    {
      if (getMode(var1) == 2)
      {
        setMode(var1, 0);
      }
      else
      {
        setMode(var1, getMode(var1) + 1);
      }
    }
    else if (getMode(var1) == 1)
    {
      setMode(var1, 0);
    }
    else
    {
      setMode(var1, getMode(var1) + 1);
    }
  }
  
  public void setMode(ItemStack var1, int var2)
  {
    setShort(var1, "mode", (short)var2);
  }
  
  public int getMode(ItemStack var1)
  {
    return getShort(var1, "mode");
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
}
