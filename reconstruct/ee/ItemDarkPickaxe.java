package ee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class ItemDarkPickaxe extends ItemDarkTool
{
  private static Block[] blocksEffectiveAgainst = { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.OBSIDIAN };
  private static int breakMode = 0;
  
  protected ItemDarkPickaxe(int var1)
  {
    super(var1, 2, 6, blocksEffectiveAgainst);
  }
  
  public float getStrVsBlock(ItemStack var1, Block var2, int var3)
  {
    float var4 = 1.0F;
    return ((var2.id != EEBlock.eePedestal.id) || (var3 != 0)) && ((var2.id != EEBlock.eeStone.id) || (var3 != 8)) ? 12.0F / var4 : (var2.material != Material.STONE) && (var2.material != Material.ORE) ? super.getDestroySpeed(var1, var2) / var4 : ((var2.material == Material.STONE) || (var2.material == Material.ORE)) && (chargeLevel(var1) > 0) ? 12.0F + 12.0F * chargeLevel(var1) / var4 : 1200000.0F / var4;
  }
  
  public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, net.minecraft.server.EntityLiving var6)
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
  
  public void doBreak(ItemStack var1, World var2, EntityHuman var3)
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
              startSearch(var2, var1, var3, var10, var4 + var7, var5 + var8, var6 + var9, true);
            }
          }
        }
      }
    }
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
  
  public boolean canBreak(int var1, int var2)
  {
    if (Block.byId[var1] == null)
    {
      return false;
    }
    if (!Block.byId[var1].b())
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
        if (var1 == blocksEffectiveAgainst[var3].id)
        {
          return true;
        }
      }
      
      if (canDestroySpecialBlock(Block.byId[var1]))
      {
        return true;
      }
      

      return false;
    }
    



    return false;
  }
  





  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (EEProxy.isClient(var3))
    {
      return false;
    }
    if (chargeLevel(var1) >= 1)
    {
      cleanDroplist(var1);
      int var8 = var3.getTypeId(var4, var5, var6);
      
      if (isOre(var8))
      {
        startSearch(var3, var1, var2, var8, var4, var5, var6, false);
      }
      
      return true;
    }
    

    return false;
  }
  

  private boolean isOre(int var1)
  {
    return EEMaps.isOreBlock(var1);
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doBreak(var1, var2, var3);
    return var1;
  }
  

  public void startSearch(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, boolean var8)
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
      
      doBreakS(var1, var2, var3, var4, var5, var6, var7);
    }
  }
  
  public void doBreakS(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7)
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
              doBreakS(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
            }
          }
          else if ((var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.GLOWING_REDSTONE_ORE.id) || (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.REDSTONE_ORE.id))
          {
            doBreakS(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
          }
        }
      }
    }
    
    ejectDropList(var1, var2, EEBase.playerX(var3), EEBase.playerY(var3), EEBase.playerZ(var3));
  }
  



  public boolean canDestroySpecialBlock(Block var1)
  {
    return var1 == Block.OBSIDIAN;
  }
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doBreak(var1, var2, var3);
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
  {
    EEBase.updateToolMode(var3);
  }
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3) {}
}
