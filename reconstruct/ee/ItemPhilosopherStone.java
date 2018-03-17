package ee;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockDeadBush;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.BlockGrass;
import net.minecraft.server.BlockLeaves;
import net.minecraft.server.BlockLongGrass;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class ItemPhilosopherStone extends ItemEECharged
{
  public ItemPhilosopherStone(int var1)
  {
    super(var1, 4);
  }
  
  public void doExtra(World var1, ItemStack var2, EntityHuman var3)
  {
    var3.openGui(net.minecraft.server.mod_EE.getInstance(), ee.core.GuiIds.PORT_CRAFTING, var1, (int)var3.locX, (int)var3.locY, (int)var3.locZ);
  }
  




  public void a(ItemStack var1, World var2, net.minecraft.server.Entity var3, int var4, boolean var5)
  {
    if (cooldown(var1) > 0)
    {
      setCooldown(var1, cooldown(var1) - 1);
    }
    
    super.a(var1, var2, var3, var4, var5);
  }
  
  private void setCooldown(ItemStack var1, int var2)
  {
    setShort(var1, "cooldown", var2);
  }
  
  private int cooldown(ItemStack var1)
  {
    return getShort(var1, "cooldown");
  }
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    var3.C_();
    var2.makeSound(var3, "transmute", 0.6F, 1.0F);
    var2.addEntity(new EntityPhilosopherEssence(var2, var3, chargeLevel(var1)));
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
  {
    doExtra(var2, var1, var3);
  }
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doTransmute(World var1, int var2, int var3, int var4, int var5, EntityHuman var6)
  {
    int var7 = var1.getTypeId(var2, var3, var4);
    
    if (var7 != var5)
    {
      if (((var5 == Block.DIRT.id) && (var7 != Block.DIRT.id) && (var7 != Block.GRASS.id)) || ((var5 == Block.GRASS.id) && (var7 != Block.DIRT.id) && (var7 != Block.GRASS.id)))
      {
        return;
      }
      
      if ((var5 != Block.DIRT.id) && (var5 != Block.GRASS.id))
      {
        return;
      }
    }
    
    int var8 = var1.getData(var2, var3, var4);
    int var9 = var1.getTypeId(var2, var3 + 1, var4);
    int var10 = var1.getData(var2, var3 + 1, var4);
    var1.getMaterial(var2, var3, var4);
    
    if ((var7 != Block.DIRT.id) && (var7 != Block.GRASS.id))
    {
      if (var7 == Block.NETHERRACK.id)
      {
        var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.GLASS.id)
      {
        if (var6.isSneaking())
        {
          var1.setTypeId(var2, var3, var4, Block.SAND.id);
        }
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.COBBLESTONE.id)
      {
        if (var1.worldProvider.d)
        {
          var1.setTypeId(var2, var3, var4, Block.NETHERRACK.id);
        }
        else if (var6.isSneaking())
        {
          if (var9 == 0)
          {
            var1.setTypeId(var2, var3, var4, Block.GRASS.id);
          }
          else
          {
            var1.setTypeId(var2, var3, var4, Block.DIRT.id);
          }
          
        }
        else {
          var1.setTypeId(var2, var3, var4, Block.STONE.id);
        }
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.SAND.id)
      {
        if (var6.isSneaking())
        {
          var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
        }
        else if ((var9 == Block.DEAD_BUSH.id) && (var10 == 0))
        {
          var1.setRawTypeIdAndData(var2, var3 + 1, var4, Block.LONG_GRASS.id, 1);
          var1.setTypeId(var2, var3, var4, Block.GRASS.id);
        }
        else if (var9 == 0)
        {
          var1.setTypeId(var2, var3, var4, Block.GRASS.id);
        }
        else
        {
          var1.setTypeId(var2, var3, var4, Block.DIRT.id);
        }
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.SANDSTONE.id)
      {
        var1.setTypeId(var2, var3, var4, Block.GRAVEL.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if ((var7 == Block.DEAD_BUSH.id) && (var8 == 0))
      {
        var1.setRawTypeIdAndData(var2, var3, var4, Block.LONG_GRASS.id, 1);
        var1.setTypeId(var2, var3 - 1, var4, Block.GRASS.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if ((var7 == Block.LONG_GRASS.id) && (var8 == 1))
      {
        var1.setRawTypeIdAndData(var2, var3, var4, Block.DEAD_BUSH.id, 0);
        var1.setTypeId(var2, var3 - 1, var4, Block.SAND.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.GRAVEL.id)
      {
        var1.setTypeId(var2, var3, var4, Block.SANDSTONE.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.SAPLING.id)
      {
        int var12 = 0;
        
        if ((var8 & 0x3) != 2)
        {
          var12++;
        }
        
        var1.setTypeIdAndData(var2, var3, var4, Block.SAPLING.id, var12);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.STONE.id)
      {
        if (var6.isSneaking())
        {
          if (var9 == 0)
          {
            var1.setTypeId(var2, var3, var4, Block.GRASS.id);
          }
          else
          {
            var1.setTypeId(var2, var3, var4, Block.DIRT.id);
          }
          
        }
        else {
          var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
        }
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.PUMPKIN.id)
      {
        var1.setTypeId(var2, var3, var4, Block.MELON.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == Block.MELON.id)
      {
        var1.setTypeId(var2, var3, var4, Block.PUMPKIN.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == BlockFlower.RED_ROSE.id)
      {
        var1.setTypeId(var2, var3, var4, BlockFlower.YELLOW_FLOWER.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == BlockFlower.YELLOW_FLOWER.id)
      {
        var1.setTypeId(var2, var3, var4, BlockFlower.RED_ROSE.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == BlockFlower.RED_MUSHROOM.id)
      {
        var1.setTypeId(var2, var3, var4, BlockFlower.BROWN_MUSHROOM.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
      else if (var7 == BlockFlower.BROWN_MUSHROOM.id)
      {
        var1.setTypeId(var2, var3, var4, BlockFlower.RED_MUSHROOM.id);
        
        if (var1.random.nextInt(8) == 0)
        {
          var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
        }
      }
    }
    else
    {
      if (var6.isSneaking())
      {
        var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
      }
      else
      {
        if ((var9 == Block.LONG_GRASS.id) && (var10 == 1))
        {
          var1.setRawTypeIdAndData(var2, var3 + 1, var4, Block.DEAD_BUSH.id, 0);
        }
        
        var1.setTypeId(var2, var3, var4, Block.SAND.id);
      }
      
      if (var1.random.nextInt(8) == 0)
      {
        var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
      }
    }
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (net.minecraft.server.EEProxy.isClient(var3))
    {
      return false;
    }
    if (cooldown(var1) > 0)
    {
      return false;
    }
    

    setCooldown(var1, 10);
    var2.C_();
    var3.makeSound(var2, "transmute", 0.6F, 1.0F);
    
    if ((var3.getTypeId(var4, var5, var6) == Block.SNOW.id) && (var7 == 1))
    {
      var5--;
    }
    
    int var8 = var3.getTypeId(var4, var5, var6);
    
    if ((var8 != Block.LOG.id) && (var8 != Block.LEAVES.id))
    {
      int var9 = chargeLevel(var1);
      






      if (getMode(var1) == 0)
      {
        for (int var10 = -var9 * (var7 == 4 ? 0 : var7 == 5 ? 2 : 1); var10 <= var9 * (var7 == 5 ? 0 : var7 == 4 ? 2 : 1); var10++)
        {
          for (int var11 = -var9 * (var7 == 0 ? 0 : var7 == 1 ? 2 : 1); var11 <= var9 * (var7 == 1 ? 0 : var7 == 0 ? 2 : 1); var11++)
          {
            for (int var12 = -var9 * (var7 == 2 ? 0 : var7 == 3 ? 2 : 1); var12 <= var9 * (var7 == 3 ? 0 : var7 == 2 ? 2 : 1); var12++)
            {
              int var13 = var4 + var10;
              int var14 = var5 + var11;
              int var15 = var6 + var12;
              doTransmute(var3, var13, var14, var15, var8, var2);
            }
            
          }
        }
      } else if (getMode(var1) == 1)
      {
        for (int var10 = -1 * (var7 == 4 ? 0 : var7 == 5 ? var9 * var9 : 1); var10 <= 1 * (var7 == 5 ? 0 : var7 == 4 ? var9 * var9 : 1); var10++)
        {
          for (int var11 = -1 * (var7 == 0 ? 0 : var7 == 1 ? var9 * var9 : 1); var11 <= 1 * (var7 == 1 ? 0 : var7 == 0 ? var9 * var9 : 1); var11++)
          {
            for (int var12 = -1 * (var7 == 2 ? 0 : var7 == 3 ? var9 * var9 : 1); var12 <= 1 * (var7 == 3 ? 0 : var7 == 2 ? var9 * var9 : 1); var12++)
            {
              int var13 = var4 + var10;
              int var14 = var5 + var11;
              int var15 = var6 + var12;
              doTransmute(var3, var13, var14, var15, var8, var2);
            }
            
          }
        }
      } else if (getMode(var1) == 2)
      {
        for (int var10 = -1 * ((var7 != 4) && (var7 != 5) ? var9 : 0); var10 <= 1 * ((var7 != 4) && (var7 != 5) ? var9 : 0); var10++)
        {
          for (int var11 = -1 * ((var7 != 0) && (var7 != 1) ? var9 : 0); var11 <= 1 * ((var7 != 0) && (var7 != 1) ? var9 : 0); var11++)
          {
            for (int var12 = -1 * ((var7 != 2) && (var7 != 3) ? var9 : 0); var12 <= 1 * ((var7 != 2) && (var7 != 3) ? var9 : 0); var12++)
            {
              int var13 = var4 + var10;
              int var14 = var5 + var11;
              int var15 = var6 + var12;
              doTransmute(var3, var13, var14, var15, var8, var2);
            }
          }
        }
      }
    }
    else
    {
      doTreeTransmute(var1, var2, var3, var4, var5, var6);
    }
    
    return false;
  }
  

  private void doTreeTransmute(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6)
  {
    int var7 = var3.getData(var4, var5, var6) & 0x3;
    byte var8 = 0;
    
    if (var7 == 0)
    {
      var8 = 1;
    }
    else if (var7 == 1)
    {
      var8 = 2;
    }
    else if (var7 == 2)
    {
      var8 = 0;
    }
    
    for (int var9 = -1; var9 <= 1; var9++)
    {
      for (int var10 = -1; var10 <= 1; var10++)
      {
        for (int var11 = -1; var11 <= 1; var11++)
        {
          if ((var9 == 0) && (var10 == 0) && (var11 == 0))
          {
            int var12 = var4 + var9;
            int var13 = var5 + var10;
            int var14 = var6 + var11;
            int var15 = var3.getTypeId(var12, var13, var14);
            int var16 = var3.getData(var12, var13, var14) & 0x3;
            
            if ((var16 == var7) && ((var15 == Block.LOG.id) || (var15 == Block.LEAVES.id)))
            {
              var3.setData(var12, var13, var14, var8);
              doTreeSearch(var3, var12, var13, var14, var7, var8);
            }
          }
        }
      }
    }
  }
  
  private void doTreeSearch(World var1, int var2, int var3, int var4, int var5, int var6)
  {
    for (int var7 = -1; var7 <= 1; var7++)
    {
      for (int var8 = -1; var8 <= 1; var8++)
      {
        for (int var9 = -1; var9 <= 1; var9++)
        {
          if ((var7 == 0) && (var8 == 0) && (var9 == 0))
          {
            int var10 = var2 + var7;
            int var11 = var3 + var8;
            int var12 = var4 + var9;
            int var13 = var1.getTypeId(var10, var11, var12);
            int var14 = var1.getData(var10, var11, var12) & 0x3;
            
            if ((var14 == var5) && ((var13 == Block.LOG.id) || (var13 == Block.LEAVES.id)))
            {
              var1.setData(var10, var11, var12, var6);
              doTreeSearch(var1, var10, var11, var12, var5, var6);
            }
          }
        }
      }
    }
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (net.minecraft.server.EEProxy.isClient(var2))
    {
      return var1;
    }
    if (cooldown(var1) > 0)
    {
      return var1;
    }
    

    setCooldown(var1, 10);
    float var4 = 1.0F;
    float var5 = var3.lastPitch + (var3.pitch - var3.lastPitch) * var4;
    float var6 = var3.lastYaw + (var3.yaw - var3.lastYaw) * var4;
    double var7 = var3.lastX + (var3.locX - var3.lastX) * var4;
    double var9 = var3.lastY + (var3.locY - var3.lastY) * var4 + 1.62D - var3.height;
    double var11 = var3.lastZ + (var3.locZ - var3.lastZ) * var4;
    Vec3D var13 = Vec3D.create(var7, var9, var11);
    float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.1415927F);
    float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.1415927F);
    float var16 = -MathHelper.cos(-var5 * 0.01745329F);
    float var17 = MathHelper.sin(-var5 * 0.01745329F);
    float var18 = var15 * var16;
    float var20 = var14 * var16;
    double var21 = 5.0D;
    Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
    MovingObjectPosition var24 = var2.rayTrace(var13, var23, true);
    
    if (var24 == null)
    {
      return var1;
    }
    

    if (var24.type == net.minecraft.server.EnumMovingObjectType.TILE)
    {
      int var25 = var24.b;
      int var26 = var24.c;
      int var27 = var24.d;
      Material var28 = var2.getMaterial(var25, var26, var27);
      
      if ((var28 != Material.LAVA) && (var28 != Material.WATER))
      {
        return var1;
      }
      
      for (int var29 = -1 * chargeLevel(var1); var29 <= chargeLevel(var1); var29++)
      {
        for (int var30 = -1 * chargeLevel(var1); var30 <= chargeLevel(var1); var30++)
        {
          for (int var31 = -1 * chargeLevel(var1); var31 <= chargeLevel(var1); var31++)
          {
            int var32 = var25 + var29;
            int var33 = var26 + var30;
            int var34 = var27 + var31;
            Material var35 = var2.getMaterial(var32, var33, var34);
            
            if (var35 == var28)
            {
              int var36 = var2.getData(var32, var33, var34);
              
              if (var35 == Material.WATER)
              {
                if (var2.getTypeId(var32, var33 + 1, var34) == 0)
                {
                  var2.setTypeId(var32, var33, var34, Block.ICE.id);
                  
                  if (var2.random.nextInt(8) == 0)
                  {
                    var2.a("largesmoke", var32, var33 + 1, var34, 0.0D, 0.0D, 0.0D);
                  }
                }
              }
              else if ((var35 == Material.LAVA) && (var36 == 0) && (var2.getTypeId(var32, var33 + 1, var34) == 0))
              {
                var2.setTypeId(var32, var33, var34, Block.OBSIDIAN.id);
                
                if (var2.random.nextInt(8) == 0)
                {
                  var2.a("largesmoke", var32, var33 + 1, var34, 0.0D, 0.0D, 0.0D);
                }
              }
            }
          }
        }
      }
    }
    
    return var1;
  }
  


  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
  {
    changeMode(var1, var3);
  }
  
  public int getMode(ItemStack var1)
  {
    return getInteger(var1, "transmode");
  }
  
  public void setMode(ItemStack var1, int var2)
  {
    setInteger(var1, "transmode", var2);
  }
  
  public void changeMode(ItemStack var1, EntityHuman var2)
  {
    if (getMode(var1) == 2)
    {
      setMode(var1, 0);
    }
    else
    {
      setMode(var1, getMode(var1) + 1);
    }
    
    var2.a("Philosopher Stone transmuting " + (getMode(var1) == 0 ? "in a cube" : getMode(var1) == 1 ? "in a line" : "in a panel") + ".");
  }
  




  public boolean e(ItemStack var1)
  {
    return false;
  }
}
