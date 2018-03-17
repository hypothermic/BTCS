package ee;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockDeadBush;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.BlockGrass;
import net.minecraft.server.BlockLongGrass;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemRedHoe extends ItemRedTool
{
  private static Block[] blocksEffectiveAgainst = { Block.DIRT, Block.GRASS };
  private static boolean breakMode;
  
  public ItemRedHoe(int var1)
  {
    super(var1, 3, 8, blocksEffectiveAgainst);
  }
  




  public float getDestroySpeed(ItemStack var1, Block var2)
  {
    if ((var2.material != net.minecraft.server.Material.EARTH) && (var2.material != net.minecraft.server.Material.GRASS))
    {
      return super.getDestroySpeed(var1, var2);
    }
    

    float var3 = 18.0F + chargeLevel(var1) * 4;
    
    if (breakMode)
    {
      var3 /= 10.0F;
    }
    
    return var3;
  }
  

  public void doBreak(ItemStack var1, World var2, EntityHuman var3)
  {
    if (chargeLevel(var1) > 0)
    {
      int var4 = (int)EEBase.playerX(var3);
      int var5 = (int)(EEBase.playerY(var3) - 2.0D);
      int var6 = (int)EEBase.playerZ(var3);
      
      if (chargeLevel(var1) < 1)
      {
        return;
      }
      
      var3.C_();
      var2.makeSound(var3, "flash", 0.8F, 1.5F);
      
      for (int var7 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var7 <= chargeLevel(var1) * chargeLevel(var1) + 1; var7++)
      {
        for (int var8 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var8 <= chargeLevel(var1) * chargeLevel(var1) + 1; var8++)
        {
          int var9 = var4 + var7;
          int var11 = var6 + var8;
          int var12 = var2.getTypeId(var9, var5, var11);
          int var13 = var2.getTypeId(var9, var5 + 1, var11);
          
          if ((var13 == BlockFlower.YELLOW_FLOWER.id) || (var13 == BlockFlower.RED_ROSE.id) || (var13 == BlockFlower.BROWN_MUSHROOM.id) || (var13 == BlockFlower.RED_MUSHROOM.id) || (var13 == BlockLongGrass.LONG_GRASS.id) || (var13 == BlockDeadBush.DEAD_BUSH.id))
          {
            Block.byId[var13].dropNaturally(var2, var9, var5 + 1, var11, var2.getData(var9, var5 + 1, var11), 1.0F, 1);
            var2.setTypeId(var9, var5 + 1, var11, 0);
          }
          
          if ((var13 == 0) && ((var12 == Block.DIRT.id) || (var12 == Block.GRASS.id)))
          {
            if (getFuelRemaining(var1) < 1)
            {
              ConsumeReagent(var1, var3, false);
            }
            
            if (getFuelRemaining(var1) > 0)
            {
              var2.setTypeId(var9, var5, var11, 60);
              setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
              
              if (var2.random.nextInt(8) == 0)
              {
                var2.a("largesmoke", var9, var5, var11, 0.0D, 0.0D, 0.0D);
              }
              
              if (var2.random.nextInt(8) == 0)
              {
                var2.a("explode", var9, var5, var11, 0.0D, 0.0D, 0.0D);
              }
            }
          }
        }
      }
    }
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (net.minecraft.server.EEProxy.isClient(var3))
    {
      return false;
    }
    




    if (chargeLevel(var1) > 0)
    {
      var2.C_();
      var3.makeSound(var2, "flash", 0.8F, 1.5F);
      
      if ((var3.getTypeId(var4, var5, var6) == BlockFlower.YELLOW_FLOWER.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.RED_ROSE.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.BROWN_MUSHROOM.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.RED_MUSHROOM.id) || (var3.getTypeId(var4, var5, var6) == BlockLongGrass.LONG_GRASS.id) || (var3.getTypeId(var4, var5, var6) == BlockDeadBush.DEAD_BUSH.id))
      {
        var5--;
      }
      
      for (int var8 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var8 <= chargeLevel(var1) * chargeLevel(var1) + 1; var8++)
      {
        for (int var9 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var9 <= chargeLevel(var1) * chargeLevel(var1) + 1; var9++)
        {
          int var15 = var4 + var8;
          int var12 = var6 + var9;
          int var13 = var3.getTypeId(var15, var5, var12);
          int var14 = var3.getTypeId(var15, var5 + 1, var12);
          
          if ((var14 == BlockFlower.YELLOW_FLOWER.id) || (var14 == BlockFlower.RED_ROSE.id) || (var14 == BlockFlower.BROWN_MUSHROOM.id) || (var14 == BlockFlower.RED_MUSHROOM.id) || (var14 == BlockLongGrass.LONG_GRASS.id) || (var14 == BlockDeadBush.DEAD_BUSH.id))
          {
            Block.byId[var14].dropNaturally(var3, var15, var5 + 1, var12, var3.getData(var15, var5 + 1, var12), 1.0F, 1);
            var3.setTypeId(var15, var5 + 1, var12, 0);
            var14 = 0;
          }
          
          if ((var14 == 0) && ((var13 == Block.DIRT.id) || (var13 == Block.GRASS.id)))
          {
            if (getFuelRemaining(var1) < 1)
            {
              ConsumeReagent(var1, var2, false);
            }
            
            if (getFuelRemaining(var1) > 0)
            {
              var3.setTypeId(var15, var5, var12, 60);
              setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
              
              if (var3.random.nextInt(8) == 0)
              {
                var3.a("largesmoke", var15, var5, var12, 0.0D, 0.0D, 0.0D);
              }
              
              if (var3.random.nextInt(8) == 0)
              {
                var3.a("explode", var15, var5, var12, 0.0D, 0.0D, 0.0D);
              }
            }
          }
        }
      }
      
      return false;
    }
    if ((var2 != null) && (!var2.d(var4, var5, var6)))
    {
      return false;
    }
    

    int var8 = var3.getTypeId(var4, var5, var6);
    int var9 = var3.getTypeId(var4, var5 + 1, var6);
    
    if (((var7 == 0) || (var9 != 0) || (var8 != Block.GRASS.id)) && (var8 != Block.DIRT.id))
    {
      return false;
    }
    

    Block var10 = Block.SOIL;
    var3.makeSound(var4 + 0.5F, var5 + 0.5F, var6 + 0.5F, var10.stepSound.getName(), (var10.stepSound.getVolume1() + 1.0F) / 2.0F, var10.stepSound.getVolume2() * 0.8F);
    
    if (var3.isStatic)
    {
      return true;
    }
    

    var3.setTypeId(var4, var5, var6, var10.id);
    return true;
  }
  







  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (net.minecraft.server.EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doBreak(var1, var2, var3);
    return var1;
  }
  

  public boolean isFull3D()
  {
    return true;
  }
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doBreak(var1, var2, var3);
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3) {}
}
