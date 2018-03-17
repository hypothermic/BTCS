package ee;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.StepSound;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class ItemEvertide extends ItemEECharged
{
  public ItemEvertide(int var1)
  {
    super(var1, 4);
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (EEProxy.isClient(var3))
    {
      return false;
    }
    

    Block var8 = Block.byId[8];
    
    if (chargeLevel(var1) > 0)
    {
      var3.makeSound(var2, "waterball", 0.8F, 1.5F);
      var2.C_();
      double var9 = EEBase.direction(var2);
      boolean var13;
      if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
      {
        var13 = false;
      }
      else
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
      
      if (var5 == 127)
      {
        return false;
      }
      




      if (var9 == 0.0D)
      {
        for (int var11 = -(chargeLevel(var1) / 7 + 1); var11 <= chargeLevel(var1) / 7 + 1; var11++)
        {
          for (int var12 = -(chargeLevel(var1) / 7 + 1); var12 <= chargeLevel(var1) / 7 + 1; var12++)
          {
            if ((var3.getTypeId(var4 + var11, var5, var6 + var12) == 0) || (var3.getTypeId(var4 + var11, var5, var6 + var12) == 78))
            {
              var3.setTypeId(var4 + var11, var5, var6 + var12, 8);
            }
            
          }
        }
      } else if (var9 == 1.0D)
      {
        for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
        {
          if ((var3.getTypeId(var4, var5 + var11, var6) == 0) || (var3.getTypeId(var4, var5 + var11, var6) == 78))
          {
            var3.setTypeId(var4, var5 + var11, var6, 8);
          }
          
          if (chargeLevel(var1) == 7)
          {
            for (int var12 = 1; var12 < 4; var12++)
            {
              if ((var3.getTypeId(var4, var5 + var11 + var12, var6) == 0) || (var3.getTypeId(var4, var5 + var11, var6) == 78))
              {
                var3.setTypeId(var4, var5 + var11 + var12, var6, 8);
              }
              
            }
          }
        }
      } else if (var9 == 2.0D)
      {
        for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
        {
          if ((var3.getTypeId(var4, var5, var6 + var11) == 0) || (var3.getTypeId(var4, var5, var6 + var11) == 78))
          {
            var3.setTypeId(var4, var5, var6 + var11, 8);
          }
          
          if (chargeLevel(var1) == 7)
          {
            if ((var3.getTypeId(var4 - 1, var5, var6 + var11) == 0) || (var3.getTypeId(var4 - 1, var5, var6 + var11) == 78))
            {
              var3.setTypeId(var4 - 1, var5, var6 + var11, 8);
            }
            
            if ((var3.getTypeId(var4 + 1, var5, var6 + var11) == 0) || (var3.getTypeId(var4 + 1, var5, var6 + var11) == 78))
            {
              var3.setTypeId(var4 + 1, var5, var6 + var11, 8);
            }
            
          }
        }
      } else if (var9 == 3.0D)
      {
        for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
        {
          if ((var3.getTypeId(var4 - var11, var5, var6) == 0) || (var3.getTypeId(var4 - var11, var5, var6) == 78))
          {
            var3.setTypeId(var4 - var11, var5, var6, 8);
          }
          
          if (chargeLevel(var1) == 7)
          {
            if ((var3.getTypeId(var4 - var11, var5, var6 - 1) == 0) || (var3.getTypeId(var4 - var11, var5, var6 - 1) == 78))
            {
              var3.setTypeId(var4 - var11, var5, var6 - 1, 8);
            }
            
            if ((var3.getTypeId(var4 - var11, var5, var6 + 1) == 0) || (var3.getTypeId(var4 - var11, var5, var6 + 1) == 78))
            {
              var3.setTypeId(var4 - var11, var5, var6 + 1, 8);
            }
            
          }
        }
      } else if (var9 == 4.0D)
      {
        for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
        {
          if ((var3.getTypeId(var4, var5, var6 - var11) == 0) || (var3.getTypeId(var4, var5, var6 - var11) == 78))
          {
            var3.setTypeId(var4, var5, var6 - var11, 8);
          }
          
          if (chargeLevel(var1) == 7)
          {
            if ((var3.getTypeId(var4 - 1, var5, var6 - var11) == 0) || (var3.getTypeId(var4 - 1, var5, var6 - var11) == 78))
            {
              var3.setTypeId(var4 - 1, var5, var6 - var11, 8);
            }
            
            if ((var3.getTypeId(var4 + 1, var5, var6 - var11) == 0) || (var3.getTypeId(var4 + 1, var5, var6 - var11) == 78))
            {
              var3.setTypeId(var4 + 1, var5, var6 - var11, 8);
            }
            
          }
        }
      } else if (var9 == 5.0D)
      {
        for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
        {
          if ((var3.getTypeId(var4 + var11, var5, var6) == 0) || (var3.getTypeId(var4 + var11, var5, var6) == 78))
          {
            var3.setTypeId(var4 + var11, var5, var6, 8);
          }
          
          if (chargeLevel(var1) == 7)
          {
            if ((var3.getTypeId(var4 + var11, var5, var6 - 1) == 0) || (var3.getTypeId(var4 + var11, var5, var6 - 1) == 78))
            {
              var3.setTypeId(var4 + var11, var5, var6 - 1, 8);
            }
            
            if ((var3.getTypeId(var4 + var11, var5, var6 + 1) == 0) || (var3.getTypeId(var4 + var11, var5, var6 + 1) == 78))
            {
              var3.setTypeId(var4 + var11, var5, var6 + 1, 8);
            }
          }
        }
      }
      
      resetCharge(var1, var3, var2, false);
      return true;
    }
    


    if (chargeLevel(var1) < 1)
    {
      if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
      {
        var7 = 0;
      }
      else
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
      
      if (var5 == 127)
      {
        return false;
      }
      
      if (var3.mayPlace(8, var4, var5, var6, false, var7))
      {
        if (var3.setTypeIdAndData(var4, var5, var6, 8, filterData(var1.getData())))
        {
          Block.byId[8].postPlace(var3, var4, var5, var6, var7);
          Block.byId[8].postPlace(var3, var4, var5, var6, var2);
          var3.makeSound(var4 + 0.5F, var5 + 0.5F, var6 + 0.5F, var8.stepSound.getName(), (var8.stepSound.getVolume1() + 1.0F) / 2.0F, var8.stepSound.getVolume2() * 0.8F);
        }
        
        return true;
      }
    }
    
    return false;
  }
  





  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

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
    MovingObjectPosition var24 = var2.rayTrace(var13, var23, false);
    
    if (var24 == null)
    {
      return var1;
    }
    

    if (var24.type == net.minecraft.server.EnumMovingObjectType.TILE)
    {
      int var25 = var24.b;
      int var26 = var24.c;
      int var27 = var24.d;
      
      if (!var2.a(var3, var25, var26, var27))
      {
        return var1;
      }
      
      if (var24.face == 0)
      {
        var26--;
      }
      
      if (var24.face == 1)
      {
        var26++;
      }
      
      if (var24.face == 2)
      {
        var27--;
      }
      
      if (var24.face == 3)
      {
        var27++;
      }
      
      if (var24.face == 4)
      {
        var25--;
      }
      
      if (var24.face == 5)
      {
        var25++;
      }
      
      if (!var3.d(var25, var26, var27))
      {
        return var1;
      }
      
      if ((var2.isEmpty(var25, var26, var27)) || (!var2.getMaterial(var25, var26, var27).isBuildable()))
      {
        var2.setTypeIdAndData(var25, var26, var27, 8, 0);
      }
    }
    
    return var1;
  }
  


  public boolean onItemUseFirst(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    return false;
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3) {}
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (var3.getAirTicks() < 0)
    {
      var3.setAirTicks(0);
    }
    
    AxisAlignedBB var4 = AxisAlignedBB.b(var3.boundingBox.a, var3.boundingBox.b - 0.2D, var3.boundingBox.c, var3.boundingBox.d, var3.boundingBox.e, var3.boundingBox.f);
    EEBase.updatePlayerInWater(var3, var2.b(var4, Material.WATER));
  }
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    var3.C_();
    var2.makeSound(var3, "waterball", 0.6F, 1.0F);
    var2.addEntity(new EntityWaterEssence(var2, var3));
  }
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3) {}
}
