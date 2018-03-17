package ee;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.BlockLongGrass;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemMercurialEye extends ItemEECharged
{
  private static String prefix_ = "eye_";
  
  public ItemMercurialEye(int var1)
  {
    super(var1, 4);
  }
  
  public static boolean ConsumeReagent(EntityHuman var0, int var1, int var2, MercurialEyeData var3)
  {
    if (var1 == 0)
    {
      return false;
    }
    

    ItemStack var4 = new ItemStack(var1, 1, var2);
    
    if (EEMaps.getEMC(var4) == 0)
    {
      return false;
    }
    if (getKleinStarPoints(var3.getItem(0)) >= EEMaps.getEMC(var1, var2))
    {
      decKleinStarPoints(var3.getItem(0), EEMaps.getEMC(var1, var2));
      return true;
    }
    

    return false;
  }
  


  private boolean ConsumeTransmuteReagent(EntityHuman var1, int var2, int var3, int var4, int var5, MercurialEyeData var6)
  {
    if (var2 == 0)
    {
      return false;
    }
    

    ItemStack var7 = new ItemStack(var2, 1, var3);
    
    if (EEMaps.getEMC(var7) == 0)
    {
      return false;
    }
    if (var4 == 0)
    {
      return false;
    }
    if (EEMaps.getEMC(var4, var5) == 0)
    {
      return false;
    }
    if (getKleinStarPoints(var6.getItem(0)) >= EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5))
    {
      if ((EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5) < 0) && (getKleinStarPoints(var6.getItem(0)) - (EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5)) > ((ItemKleinStar)var6.getItem(0).getItem()).getMaxPoints(var6.getItem(0))))
      {
        return false;
      }
      

      decKleinStarPoints(var6.getItem(0), EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5));
      return true;
    }
    


    return false;
  }
  


  public static void decKleinStarPoints(ItemStack var0, int var1)
  {
    if (var0 != null)
    {
      if ((var0.getItem() instanceof ItemKleinStar))
      {
        ItemKleinStar var2 = (ItemKleinStar)var0.getItem();
        var2.setKleinPoints(var0, var2.getKleinPoints(var0) - var1 >= 0 ? var2.getKleinPoints(var0) - var1 : 0);
        var2.onUpdate(var0);
      }
    }
  }
  
  public static int getKleinStarPoints(ItemStack var0)
  {
    return (var0.getItem() instanceof ItemKleinStar) ? ((ItemKleinStar)var0.getItem()).getKleinPoints(var0) : var0 == null ? 0 : 0;
  }
  
  public static MercurialEyeData getEyeData(EntityHuman var0, World var1)
  {
    String var2 = var0.name;
    String var3 = prefix_ + var2;
    MercurialEyeData var4 = (MercurialEyeData)var1.a(MercurialEyeData.class, var3);
    
    if (var4 == null)
    {
      var4 = new MercurialEyeData(var3);
      var4.a();
      var1.a(var3, var4);
    }
    
    return var4;
  }
  
  public static MercurialEyeData getEyeData(ItemStack var0, EntityHuman var1, World var2)
  {
    String var3 = var1.name;
    String var4 = prefix_ + var3;
    MercurialEyeData var5 = (MercurialEyeData)var2.a(MercurialEyeData.class, var4);
    
    if (var5 == null)
    {
      var5 = new MercurialEyeData(var4);
      var5.a();
      var2.a(var4, var5);
    }
    
    return var5;
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
  {
    doExtra(var2, var1, var3);
  }
  
  public void doExtra(World var1, ItemStack var2, EntityHuman var3)
  {
    if (EEProxy.isServer())
    {
      var3.openGui(net.minecraft.server.mod_EE.getInstance(), ee.core.GuiIds.MERCURIAL_EYE, var1, (int)var3.locX, (int)var3.locY, (int)var3.locZ);
    }
  }
  
  public void doExtra(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7)
  {
    if (EEProxy.isServer())
    {
      var3.openGui(net.minecraft.server.mod_EE.getInstance(), ee.core.GuiIds.MERCURIAL_EYE, var1, var5, var6, var7);
    }
  }
  



  public void d(ItemStack var1, World var2, EntityHuman var3)
  {
    if (!EEProxy.isClient(var2))
    {
      String var4 = var3.name;
      String var5 = prefix_ + var4;
      MercurialEyeData var6 = (MercurialEyeData)var2.a(MercurialEyeData.class, var5);
      
      if (var6 == null)
      {
        var6 = new MercurialEyeData(var5);
        var2.a(var5, var6);
        var6.a();
      }
    }
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (EEProxy.isClient(var3))
    {
      return false;
    }
    

    MercurialEyeData var8 = getEyeData(var1, var2, var3);
    
    if ((var8.getItem(0) != null) && (var8.getItem(1) != null))
    {
      if (EEMaps.getEMC(var8.getItem(1)) == 0)
      {
        return false;
      }
      if (!EEBase.isKleinStar(var8.getItem(0).id))
      {
        return false;
      }
      if (var8.getItem(1).id >= Block.byId.length)
      {
        return false;
      }
      

      if (Block.byId[var3.getTypeId(var4, var5, var6)].hasTileEntity(var3.getData(var4, var5, var6)))
      {
        if ((var3.getTypeId(var4, var5, var6) == EEBlock.eeStone.id) && (var3.getData(var4, var5, var6) <= 7))
        {
          return false;
        }
        
        if (var3.getTypeId(var4, var5, var6) != EEBlock.eeStone.id)
        {
          return false;
        }
      }
      
      if (Block.byId[var8.getItem(1).id].hasTileEntity(var8.getItem(1).getData()))
      {
        if ((var8.getItem(1).id == EEBlock.eeStone.id) && (var8.getItem(1).getData() <= 7))
        {
          return false;
        }
        
        if (var8.getItem(1).id != EEBlock.eeStone.id)
        {
          return false;
        }
      }
      
      int var9 = var8.getItem(1).id;
      int var10 = var8.getItem(1).getData();
      double var11 = EEBase.direction(var2);
      
      if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
      {
        if (var7 == 1)
        {
          var5--;
        }
        
        if (var7 == 2)
        {
          var6++;
          var5++;
        }
        
        if (var7 == 3)
        {
          var6--;
          var5++;
        }
        
        if (var7 == 4)
        {
          var4++;
          var5++;
        }
        
        if (var7 == 5)
        {
          var4--;
          var5++;
        }
      }
      
      if (EEBase.getBuildMode(var2) == 1)
      {
        if (var7 == 0)
        {
          var5--;
        }
        
        if (var7 == 1)
        {
          var5++;
        }
        
        if (var7 == 2)
        {
          var6--;
        }
        
        if (var7 == 3)
        {
          var6++;
        }
        
        if (var7 == 4)
        {
          var4--;
        }
        
        if (var7 == 5)
        {
          var4++;
        }
      }
      
      byte var13 = 0;
      byte var14 = 0;
      byte var15 = 0;
      byte var16 = 0;
      byte var17 = 0;
      byte var18 = 0;
      
      if (EEBase.getBuildMode(var2) != 3)
      {
        if ((var11 != 0.0D) && (var11 != 1.0D))
        {
          if ((var11 != 2.0D) && (var11 != 4.0D))
          {
            if ((var11 == 3.0D) || (var11 == 5.0D))
            {
              if (var7 == 0)
              {
                var17 = -1;
                var18 = 1;
                var15 = -2;
              }
              else if (var7 == 1)
              {
                var17 = -1;
                var18 = 1;
                var16 = 2;
              }
              else if (var7 == 2)
              {
                var17 = -2;
                var15 = -1;
                var16 = 1;
              }
              else if (var7 == 3)
              {
                var18 = 2;
                var15 = -1;
                var16 = 1;
              }
              else if ((var7 == 4) || (var7 == 5))
              {
                var17 = -1;
                var18 = 1;
                var15 = -1;
                var16 = 1;
              }
            }
          }
          else if (var7 == 0)
          {
            var13 = -1;
            var14 = 1;
            var15 = -2;
          }
          else if (var7 == 1)
          {
            var13 = -1;
            var14 = 1;
            var16 = 2;
          }
          else if ((var7 != 2) && (var7 != 3))
          {
            if (var7 == 4)
            {
              var13 = -2;
              var15 = -1;
              var16 = 1;
            }
            else if (var7 == 5)
            {
              var14 = 2;
              var15 = -1;
              var16 = 1;
            }
          }
          else
          {
            var13 = -1;
            var14 = 1;
            var15 = -1;
            var16 = 1;
          }
        }
        else if ((var7 != 0) && (var7 != 1))
        {
          if (var7 == 2)
          {
            var13 = -1;
            var14 = 1;
            var17 = -2;
          }
          else if (var7 == 3)
          {
            var13 = -1;
            var14 = 1;
            var18 = 2;
          }
          else if (var7 == 4)
          {
            var13 = -2;
            var17 = -1;
            var18 = 1;
          }
          else if (var7 == 5)
          {
            var14 = 2;
            var17 = -1;
            var18 = 1;
          }
        }
        else
        {
          var13 = -1;
          var14 = 1;
          var17 = -1;
          var18 = 1;
        }
      }
      
      if (EEBase.getBuildMode(var2) != 3)
      {
        doWall(var3, var1, var2, var4, var5, var6, var13, var15, var17, var14, var16, var18, var9, var10);
      }
      else
      {
        doPillar(var3, var1, var2, var4, var5, var6, var7, var9, var10);
      }
      
      return true;
    }
    


    return false;
  }
  


  private void doPillar(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, int var8, int var9)
  {
    MercurialEyeData var10 = getEyeData(var2, var3, var3.world);
    boolean var11 = false;
    byte var12 = 1;
    byte var13 = 1;
    byte var14 = 1;
    byte var15 = -1;
    byte var16 = -1;
    byte var17 = -1;
    int var18 = 3 + 3 * chargeLevel(var2) - 1;
    



    if (var7 == 0)
    {
      byte var27 = 0;
      int var23 = var18;
      
      for (int var19 = var15; var19 <= var12; var19++)
      {
        for (int var20 = var17; var20 <= var14; var20++)
        {
          for (int var21 = var27; (var21 <= var23) && (doPillarBlock(var1, var4 + var19, var5 + var21, var6 + var20, var8, var9, var11, var3, var10, var21 - var27)); var21++) {}
        }
        
      }
      

    }
    else if (var7 == 1)
    {
      int var26 = -var18;
      var13 = 0;
      
      for (int var19 = var15; var19 <= var12; var19++)
      {
        for (int var20 = var17; var20 <= var14; var20++)
        {
          for (int var21 = var13; (var21 >= var26) && (doPillarBlock(var1, var4 + var19, var5 + var21, var6 + var20, var8, var9, var11, var3, var10, -var21)); var21--) {}
        }
        
      }
      

    }
    else if (var7 == 2)
    {
      byte var30 = 0;
      int var25 = var18;
      
      for (int var19 = var15; var19 <= var12; var19++)
      {
        for (int var20 = var16; var20 <= var13; var20++)
        {
          for (int var21 = var30; (var21 <= var25) && (doPillarBlock(var1, var4 + var19, var5 + var20, var6 + var21, var8, var9, var11, var3, var10, var21 - var30)); var21++) {}
        }
        
      }
      

    }
    else if (var7 == 3)
    {
      int var29 = -var18;
      var14 = 0;
      
      for (int var19 = var15; var19 <= var12; var19++)
      {
        for (int var20 = var16; var20 <= var13; var20++)
        {
          for (int var21 = var14; (var21 >= var29) && (doPillarBlock(var1, var4 + var19, var5 + var20, var6 + var21, var8, var9, var11, var3, var10, -var21)); var21--) {}
        }
        
      }
      

    }
    else if (var7 == 4)
    {
      byte var24 = 0;
      int var22 = var18;
      
      for (int var19 = var16; var19 <= var13; var19++)
      {
        for (int var20 = var17; var20 <= var14; var20++)
        {
          for (int var21 = var24; (var21 <= var22) && (doPillarBlock(var1, var4 + var21, var5 + var19, var6 + var20, var8, var9, var11, var3, var10, var21 - var24)); var21++) {}
        }
        
      }
      

    }
    else if (var7 == 5)
    {
      int var28 = -var18;
      var12 = 0;
      
      for (int var19 = var16; var19 <= var13; var19++)
      {
        for (int var20 = var17; var20 <= var14; var20++)
        {
          for (int var21 = var12; (var21 >= var28) && (doPillarBlock(var1, var4 + var21, var5 + var19, var6 + var20, var8, var9, var11, var3, var10, -var21)); var21--) {}
        }
      }
    }
  }
  



  public boolean doPillarBlock(World var1, int var2, int var3, int var4, int var5, int var6, boolean var7, EntityHuman var8, MercurialEyeData var9, int var10)
  {
    int var11 = var1.getTypeId(var2, var3, var4);
    
    if ((var11 != 0) && (var11 != 8) && (var11 != 9) && (var11 != 10) && (var11 != 11) && (var11 != BlockFlower.LONG_GRASS.id) && (var11 != 78))
    {
      return var10 <= 4;
    }
    

    if (var11 == BlockFlower.LONG_GRASS.id)
    {
      Block.byId[var11].dropNaturally(var1, var2, var3 + 1, var4, 1, 1.0F, 1);
    }
    
    if (ConsumeReagent(var8, var5, var6, var9))
    {
      if (!var7)
      {
        var1.makeSound(var8, "wall", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
        var7 = true;
      }
      
      var1.setTypeIdAndData(var2, var3, var4, var5, var6);
      
      if (var1.random.nextInt(8) == 0)
      {
        var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
      }
    }
    
    return true;
  }
  

  public void doWall(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14)
  {
    MercurialEyeData var15 = getEyeData(var2, var3, var3.world);
    boolean var16 = false;
    
    for (int var17 = chargeLevel(var2) * var7; var17 <= chargeLevel(var2) * var10; var17++)
    {
      for (int var18 = chargeLevel(var2) * var8; var18 <= chargeLevel(var2) * var11; var18++)
      {
        for (int var19 = chargeLevel(var2) * var9; var19 <= chargeLevel(var2) * var12; var19++)
        {
          int var20 = var1.getTypeId(var17 + var4, var18 + var5, var19 + var6);
          
          if ((var20 != 0) && (var20 != 8) && (var20 != 9) && (var20 != 10) && (var20 != 11) && (var20 != BlockFlower.LONG_GRASS.id) && (var20 != 78))
          {
            if (((Integer)EEBase.playerBuildMode.get(var3)).intValue() == 2)
            {
              int var21 = var1.getTypeId(var17 + var4, var18 + var5, var19 + var6);
              int var22 = var1.getData(var17 + var4, var18 + var5, var19 + var6);
              
              if ((EEMaps.getEMC(var21, var22) != 0) && (ConsumeTransmuteReagent(var3, var13, var14, var21, var22, var15)))
              {
                if (!var16)
                {
                  var1.makeSound(var3, "wall", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
                  var16 = true;
                }
                
                var1.setTypeIdAndData(var17 + var4, var18 + var5, var19 + var6, var13, var14);
                
                if (var1.random.nextInt(8) == 0)
                {
                  var1.a("largesmoke", var4 + var17, var5 + var18 + 1, var6 + var19, 0.0D, 0.0D, 0.0D);
                }
              }
            }
          }
          else if (((Integer)EEBase.playerBuildMode.get(var3)).intValue() != 2)
          {
            if (var20 == BlockFlower.LONG_GRASS.id)
            {
              Block.byId[var20].dropNaturally(var1, var17 + var4, var18 + var5 + 1, var19 + var6, 1, 1.0F, 1);
            }
            
            if (ConsumeReagent(var3, var13, var14, var15))
            {
              if (!var16)
              {
                var1.makeSound(var3, "wall", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
                var16 = true;
              }
              
              var1.setTypeIdAndData(var17 + var4, var18 + var5, var19 + var6, var13, var14);
              
              if (var1.random.nextInt(8) == 0)
              {
                var1.a("largesmoke", var4 + var17, var5 + var18 + 1, var6 + var19, 0.0D, 0.0D, 0.0D);
              }
            }
          }
        }
      }
    }
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3) {}
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
  {
    EEBase.updateBuildMode(var3);
  }
}
