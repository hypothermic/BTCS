package ee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class ItemRedMace extends ItemRedTool
{
  private static Block[] blocksEffectiveAgainst = { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.OBSIDIAN, Block.GRASS, Block.DIRT, Block.SOUL_SAND, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL, EEBlock.eeStone, EEBlock.eePedestal, EEBlock.eeDevice, EEBlock.eeChest };
  
  protected ItemRedMace(int var1)
  {
    super(var1, 4, 16, blocksEffectiveAgainst);
  }
  
  public float getStrVsBlock(ItemStack var1, Block var2, int var3)
  {
    float var4 = 1.0F;
    return ((var2.id != EEBlock.eePedestal.id) || (var3 != 0)) && ((var2.id != EEBlock.eeStone.id) || ((var3 != 8) && (var3 != 9))) ? 16.0F / var4 : (var2.material != Material.STONE) && (var2.material != Material.ORE) ? (super.getDestroySpeed(var1, var2) + 4.0F * chargeLevel(var1)) / var4 : ((var2.material == Material.STONE) || (var2.material == Material.ORE)) && (chargeLevel(var1) > 0) ? 16.0F + 16.0F * chargeLevel(var1) / var4 : 1200000.0F / var4;
  }
  
  public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
  {
    EntityHuman var7 = null;
    
    if ((var6 instanceof EntityHuman))
    {
      var7 = (EntityHuman)var6;
      
      if (EEBase.getToolMode(var7) != 0)
      {
        if (EEBase.getToolMode(var7) == 1)
        {
          doTallImpact(var7.world, var1, var7, var3, var4, var5, EEBase.direction(var7));
        }
        else if (EEBase.getToolMode(var7) == 2)
        {
          doWideImpact(var7.world, var1, var3, var4, var5, EEBase.heading(var7));
        }
        else if (EEBase.getToolMode(var7) == 3)
        {
          doLongImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
        }
      }
      else if (EEBase.getHammerMode(var7))
      {
        doMegaImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
      }
      
      return true;
    }
    

    return true;
  }
  

  public void doLongImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
  {
    cleanDroplist(var2);
    
    for (int var8 = 1; var8 <= 2; var8++)
    {
      int var9 = var3;
      int var10 = var4;
      int var11 = var5;
      
      if (var6 == 0.0D)
      {
        var10 = var4 - var8;
      }
      
      if (var6 == 1.0D)
      {
        var10 += var8;
      }
      
      if (var6 == 2.0D)
      {
        var11 = var5 + var8;
      }
      
      if (var6 == 3.0D)
      {
        var9 = var3 - var8;
      }
      
      if (var6 == 4.0D)
      {
        var11 -= var8;
      }
      
      if (var6 == 5.0D)
      {
        var9 += var8;
      }
      
      int var12 = var1.getTypeId(var9, var10, var11);
      int var13 = var1.getData(var9, var10, var11);
      
      if (canBreak(var12, var13))
      {
        scanBlockAndBreak(var1, var2, var9, var10, var11);
      }
    }
    
    ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
  }
  
  public void doWideImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
  {
    cleanDroplist(var2);
    
    for (int var8 = -1; var8 <= 1; var8++)
    {
      int var9 = var3;
      int var11 = var5;
      
      if (var8 != 0)
      {
        if ((var6 != 2.0D) && (var6 != 4.0D))
        {
          var11 = var5 + var8;
        }
        else
        {
          var9 = var3 + var8;
        }
        
        int var12 = var1.getTypeId(var9, var4, var11);
        int var13 = var1.getData(var9, var4, var11);
        
        if (canBreak(var12, var13))
        {
          scanBlockAndBreak(var1, var2, var9, var4, var11);
        }
      }
    }
    
    ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
  }
  
  public void doTallImpact(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, double var7)
  {
    cleanDroplist(var2);
    
    for (int var9 = -1; var9 <= 1; var9++)
    {
      int var10 = var4;
      int var11 = var5;
      int var12 = var6;
      
      if (var9 != 0)
      {
        if ((var7 != 0.0D) && (var7 != 1.0D))
        {
          var11 = var5 + var9;
        }
        else if ((EEBase.heading(var3) != 2.0D) && (EEBase.heading(var3) != 4.0D))
        {
          var10 = var4 + var9;
        }
        else
        {
          var12 = var6 + var9;
        }
        
        int var13 = var1.getTypeId(var10, var11, var12);
        int var14 = var1.getData(var10, var11, var12);
        
        if (canBreak(var13, var14))
        {
          scanBlockAndBreak(var1, var2, var10, var11, var12);
        }
      }
    }
    
    ejectDropList(var1, var2, var4, var5 + 0.5D, var6);
  }
  
  public void doMegaImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
  {
    cleanDroplist(var2);
    
    for (int var8 = -1; var8 <= 1; var8++)
    {
      for (int var9 = -1; var9 <= 1; var9++)
      {
        int var10 = var3;
        int var11 = var4;
        int var12 = var5;
        
        if ((var8 != 0) || (var9 != 0))
        {
          if ((var6 != 0.0D) && (var6 != 1.0D))
          {
            if ((var6 != 2.0D) && (var6 != 4.0D))
            {
              if ((var6 == 3.0D) || (var6 == 5.0D))
              {
                var11 = var4 + var8;
                var12 = var5 + var9;
              }
            }
            else
            {
              var10 = var3 + var8;
              var11 = var4 + var9;
            }
          }
          else
          {
            var10 = var3 + var8;
            var12 = var5 + var9;
          }
          
          int var13 = var1.getTypeId(var10, var11, var12);
          int var14 = var1.getData(var10, var11, var12);
          
          if (canBreak(var13, var14))
          {
            scanBlockAndBreak(var1, var2, var10, var11, var12);
          }
        }
      }
    }
    
    ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
  }
  
  public void scanBlockAndBreak(World var1, ItemStack var2, int var3, int var4, int var5)
  {
    int var6 = var1.getTypeId(var3, var4, var5);
    int var7 = var1.getData(var3, var4, var5);
    ArrayList var8 = Block.byId[var6].getBlockDropped(var1, var3, var4, var5, var7, 0);
    Iterator var9 = var8.iterator();
    
    while (var9.hasNext())
    {
      ItemStack var10 = (ItemStack)var9.next();
      addToDroplist(var2, var10);
    }
    
    var1.setTypeId(var3, var4, var5, 0);
    
    if (var1.random.nextInt(8) == 0)
    {
      var1.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
    }
    
    if (var1.random.nextInt(8) == 0)
    {
      var1.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
    }
  }
  



  public boolean canDestroySpecialBlock(Block var1)
  {
    return var1 == Block.OBSIDIAN;
  }
  
  public boolean canBreak(int var1, int var2)
  {
    if (Block.byId[var1] == null)
    {
      return false;
    }
    if ((!Block.byId[var1].hasTileEntity(var2)) && (var1 != Block.BEDROCK.id))
    {
      if (Block.byId[var1].material == null)
      {
        return false;
      }
      

      for (int var3 = 0; var3 < blocksEffectiveAgainst.length; var3++)
      {
        if ((blocksEffectiveAgainst[var3] != null) && (var1 == blocksEffectiveAgainst[var3].id))
        {
          return true;
        }
      }
      
      if (Block.byId[var1].material == Material.STONE)
      {
        return true;
      }
      if ((Block.byId[var1].material != Material.GRASS) && (Block.byId[var1].material != Material.EARTH) && (Block.byId[var1].material != Material.SAND) && (Block.byId[var1].material != Material.SNOW_LAYER) && (Block.byId[var1].material != Material.CLAY))
      {
        if (canDestroySpecialBlock(Block.byId[var1]))
        {
          return true;
        }
        

        return false;
      }
      


      return true;
    }
    



    return false;
  }
  

  public void doPickaxeBreak(ItemStack var1, World var2, EntityHuman var3)
  {
    if (chargeLevel(var1) > 0)
    {
      int var4 = (int)EEBase.playerX(var3);
      int var5 = (int)EEBase.playerY(var3);
      int var6 = (int)EEBase.playerZ(var3);
      
      for (int var7 = -2; var7 <= 2; var7++)
      {
        for (int var8 = -2; var8 <= 2; var8++)
        {
          for (int var9 = -2; var9 <= 2; var9++)
          {
            int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
            
            if (isOre(var10))
            {
              startSearchPick(var2, var1, var3, var10, var4 + var7, var5 + var8, var6 + var9, true);
            }
          }
        }
      }
    }
  }
  
  private boolean isOre(int var1)
  {
    return EEMaps.isOreBlock(var1);
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
  {
    if ((EEBase.getToolMode(var3) == 0) && (EEBase.getHammerMode(var3)))
    {
      EEBase.updateToolMode(var3);
      EEBase.updateHammerMode(var3, false);
    }
    else if ((EEBase.getToolMode(var3) == 0) && (!EEBase.getHammerMode(var3)))
    {
      EEBase.updateHammerMode(var3, true);
    }
    else
    {
      EEBase.updateToolMode(var3);
    }
  }
  
  public void startSearchPick(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, boolean var8)
  {
    if (var4 == Block.BEDROCK.id)
    {
      var3.a("Nice try. You can't break bedrock.");
    }
    else
    {
      var1.makeSound(var3, "flash", 0.8F, 1.5F);
      
      if (var8)
      {
        var3.C_();
      }
      
      doBreakPick(var1, var2, var3, var4, var5, var6, var7);
    }
  }
  
  public void startSearchShovel(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7, boolean var8)
  {
    var1.makeSound(var2, "flash", 0.8F, 1.5F);
    
    if (var8)
    {
      var2.C_();
    }
    
    doBreakShovel(var1, var2, var3, var4, var5, var6, var7);
  }
  
  public void doBreakPick(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7)
  {
    scanBlockAndBreak(var1, var2, var5, var6, var7);
    
    for (int var8 = -1; var8 <= 1; var8++)
    {
      for (int var9 = -1; var9 <= 1; var9++)
      {
        for (int var10 = -1; var10 <= 1; var10++)
        {
          if ((var4 != Block.REDSTONE_ORE.id) && (var4 != Block.GLOWING_REDSTONE_ORE.id))
          {
            if (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == var4)
            {
              doBreakPick(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
            }
          }
          else if ((var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.GLOWING_REDSTONE_ORE.id) || (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.REDSTONE_ORE.id))
          {
            doBreakPick(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
          }
        }
      }
    }
    
    ejectDropList(var1, var2, EEBase.playerX(var3), EEBase.playerY(var3), EEBase.playerZ(var3));
  }
  
  public void doBreakShovel(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7)
  {
    if (getFuelRemaining(var3) < 1)
    {
      ConsumeReagent(var3, var2, false);
    }
    
    if (getFuelRemaining(var3) > 0)
    {
      int var8 = var1.getData(var5, var6, var7);
      ArrayList var9 = Block.byId[var4].getBlockDropped(var1, var5, var6, var7, var8, 0);
      Iterator var10 = var9.iterator();
      
      while (var10.hasNext())
      {
        ItemStack var11 = (ItemStack)var10.next();
        addToDroplist(var3, var11);
      }
      
      var1.setTypeId(var5, var6, var7, 0);
      setShort(var3, "fuelRemaining", getFuelRemaining(var3) - 1);
      
      for (int var14 = -1; var14 <= 1; var14++)
      {
        for (int var13 = -1; var13 <= 1; var13++)
        {
          for (int var12 = -1; var12 <= 1; var12++)
          {
            if (var1.getTypeId(var5 + var14, var6 + var13, var7 + var12) == var4)
            {
              doBreakShovelAdd(var1, var2, var3, var4, var5 + var14, var6 + var13, var7 + var12);
            }
          }
        }
      }
      
      if (var1.random.nextInt(8) == 0)
      {
        var1.a("largesmoke", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
      }
      
      if (var1.random.nextInt(8) == 0)
      {
        var1.a("explode", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
      }
      
      ejectDropList(var1, var3, var5, var6, var7);
    }
  }
  
  public void doBreakShovelAdd(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7)
  {
    if (getFuelRemaining(var3) < 1)
    {
      ConsumeReagent(var3, var2, false);
    }
    
    if (getFuelRemaining(var3) > 0)
    {
      int var8 = var1.getData(var5, var6, var7);
      ArrayList var9 = Block.byId[var4].getBlockDropped(var1, var5, var6, var7, var8, 0);
      Iterator var10 = var9.iterator();
      
      while (var10.hasNext())
      {
        ItemStack var11 = (ItemStack)var10.next();
        addToDroplist(var3, var11);
      }
      
      var1.setTypeId(var5, var6, var7, 0);
      setShort(var3, "fuelRemaining", getFuelRemaining(var3) - 1);
      
      for (int var14 = -1; var14 <= 1; var14++)
      {
        for (int var13 = -1; var13 <= 1; var13++)
        {
          for (int var12 = -1; var12 <= 1; var12++)
          {
            if (var1.getTypeId(var5 + var14, var6 + var13, var7 + var12) == var4)
            {
              doBreakShovelAdd(var1, var2, var3, var4, var5 + var14, var6 + var13, var7 + var12);
            }
          }
        }
      }
      
      if (var1.random.nextInt(8) == 0)
      {
        var1.a("largesmoke", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
      }
      
      if (var1.random.nextInt(8) == 0)
      {
        var1.a("explode", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
      }
    }
  }
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doBreak(var1, var2, var3);
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (net.minecraft.server.EEProxy.isClient(var3))
    {
      return false;
    }
    if (chargeLevel(var1) < 1)
    {
      return false;
    }
    

    cleanDroplist(var1);
    int var8 = var3.getTypeId(var4, var5, var6);
    
    if (isOre(var8))
    {
      startSearchPick(var3, var1, var2, var8, var4, var5, var6, false);
    }
    else if (var3.getMaterial(var4, var5, var6) == Material.STONE)
    {
      onItemUseHammer(var1, var2, var3, var4, var5, var6, var7);
    }
    else if ((var3.getMaterial(var4, var5, var6) == Material.EARTH) || (var3.getMaterial(var4, var5, var6) == Material.GRASS) || (var3.getMaterial(var4, var5, var6) == Material.CLAY) || (var3.getMaterial(var4, var5, var6) == Material.SAND) || (var3.getMaterial(var4, var5, var6) == Material.SNOW_LAYER))
    {
      onItemUseShovel(var1, var2, var3, var4, var5, var6, var7);
    }
    
    return true;
  }
  

  public boolean onItemUseHammer(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    boolean var8 = true;
    
    if (chargeLevel(var1) > 0)
    {
      cleanDroplist(var1);
      var2.C_();
      var3.makeSound(var2, "flash", 0.8F, 1.5F);
      
      for (int var9 = -(chargeLevel(var1) * (var7 == 4 ? 0 : var7 == 5 ? 2 : 1)); var9 <= chargeLevel(var1) * (var7 == 4 ? 2 : var7 == 5 ? 0 : 1); var9++)
      {
        for (int var10 = -(chargeLevel(var1) * (var7 == 0 ? 0 : var7 == 1 ? 2 : 1)); var10 <= chargeLevel(var1) * (var7 == 0 ? 2 : var7 == 1 ? 0 : 1); var10++)
        {
          for (int var11 = -(chargeLevel(var1) * (var7 == 2 ? 0 : var7 == 3 ? 2 : 1)); var11 <= chargeLevel(var1) * (var7 == 2 ? 2 : var7 == 3 ? 0 : 1); var11++)
          {
            int var12 = var4 + var9;
            int var13 = var5 + var10;
            int var14 = var6 + var11;
            int var15 = var3.getTypeId(var12, var13, var14);
            int var16 = var3.getData(var12, var13, var14);
            
            if (canBreak(var15, var16))
            {
              if (getFuelRemaining(var1) < 1)
              {
                ConsumeReagent(var1, var2, var8);
                var8 = false;
              }
              
              if (getFuelRemaining(var1) > 0)
              {
                ArrayList var17 = Block.byId[var15].getBlockDropped(var3, var12, var13, var14, var16, 0);
                Iterator var18 = var17.iterator();
                
                while (var18.hasNext())
                {
                  ItemStack var19 = (ItemStack)var18.next();
                  addToDroplist(var1, var19);
                }
                
                var3.setTypeId(var12, var13, var14, 0);
                
                if (var3.random.nextInt(8) == 0)
                {
                  var3.a("largesmoke", var12, var13, var14, 0.0D, 0.0D, 0.0D);
                }
                
                if (var3.random.nextInt(8) == 0)
                {
                  var3.a("explode", var12, var13, var14, 0.0D, 0.0D, 0.0D);
                }
                
                setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
              }
            }
          }
        }
      }
      
      ejectDropList(var3, var1, var4, var5, var6);
    }
    
    return false;
  }
  
  public boolean onItemUseShovel(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (chargeLevel(var1) >= 1)
    {
      cleanDroplist(var1);
      int var8 = var3.getTypeId(var4, var5, var6);
      
      if (var8 == Block.GRAVEL.id)
      {
        startSearchShovel(var3, var2, var1, var8, var4, var5, var6, false);
        return true;
      }
    }
    
    if (chargeLevel(var1) <= 0)
    {
      return false;
    }
    

    boolean var19 = true;
    cleanDroplist(var1);
    var2.C_();
    var3.makeSound(var2, "flash", 0.8F, 1.5F);
    
    for (int var9 = -chargeLevel(var1); var9 <= chargeLevel(var1); var9++)
    {
      for (int var10 = -chargeLevel(var1); var10 <= chargeLevel(var1); var10++)
      {
        int var11 = var4 + var9;
        int var13 = var6 + var10;
        
        if (var7 == 2)
        {
          var13 += chargeLevel(var1);
        }
        else if (var7 == 3)
        {
          var13 -= chargeLevel(var1);
        }
        else if (var7 == 4)
        {
          var11 += chargeLevel(var1);
        }
        else if (var7 == 5)
        {
          var11 -= chargeLevel(var1);
        }
        
        int var14 = var3.getTypeId(var11, var5, var13);
        int var15 = var3.getData(var11, var5, var13);
        
        if (canBreak(var14, var15))
        {
          if (getFuelRemaining(var1) < 1)
          {
            if ((var9 == chargeLevel(var1)) && (var10 == chargeLevel(var1)))
            {
              ConsumeReagent(var1, var2, var19);
              var19 = false;
            }
            else
            {
              ConsumeReagent(var1, var2, false);
            }
          }
          
          if (getFuelRemaining(var1) > 0)
          {
            ArrayList var16 = Block.byId[var14].getBlockDropped(var3, var11, var5, var13, var15, 0);
            Iterator var17 = var16.iterator();
            
            while (var17.hasNext())
            {
              ItemStack var18 = (ItemStack)var17.next();
              addToDroplist(var1, var18);
            }
            
            var3.setTypeId(var11, var5, var13, 0);
            
            if (var3.random.nextInt(8) == 0)
            {
              var3.a("largesmoke", var11, var5, var13, 0.0D, 0.0D, 0.0D);
            }
            
            if (var3.random.nextInt(8) == 0)
            {
              var3.a("explode", var11, var5, var13, 0.0D, 0.0D, 0.0D);
            }
          }
        }
      }
    }
    
    ejectDropList(var3, var1, var4, var5, var6);
    return true;
  }
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3) {}
}
