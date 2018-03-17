package ee;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityExperienceOrb;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class ItemAttractionRing extends ItemEECharged
{
  public ItemAttractionRing(int var1)
  {
    super(var1, 0);
    this.maxStackSize = 1;
  }
  
  public int getIconFromDamage(int var1)
  {
    return !isActivated(var1) ? this.textureId : this.textureId + 1;
  }
  








  private void PullItems(Entity var1, EntityHuman var2)
  {
    if (var1.getClass().equals(EntityItem.class))
    {
      EntityItem var3 = (EntityItem)var1;
      double var4 = (float)var2.locX + 0.5F - var3.locX;
      double var6 = (float)var2.locY + 0.5F - var3.locY;
      double var8 = (float)var2.locZ + 0.5F - var3.locZ;
      double var10 = var4 * var4 + var6 * var6 + var8 * var8;
      var10 *= var10;
      
      if (var10 <= Math.pow(6.0D, 4.0D))
      {
        double var12 = var4 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        double var14 = var6 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        double var16 = var8 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        
        if (var12 > 0.1D)
        {
          var12 = 0.1D;
        }
        else if (var12 < -0.1D)
        {
          var12 = -0.1D;
        }
        
        if (var14 > 0.1D)
        {
          var14 = 0.1D;
        }
        else if (var14 < -0.1D)
        {
          var14 = -0.1D;
        }
        
        if (var16 > 0.1D)
        {
          var16 = 0.1D;
        }
        else if (var16 < -0.1D)
        {
          var16 = -0.1D;
        }
        
        var3.motX += var12 * 1.2D;
        var3.motY += var14 * 1.2D;
        var3.motZ += var16 * 1.2D;
      }
    }
    else if (var1.getClass().equals(EntityLootBall.class))
    {
      EntityLootBall var18 = (EntityLootBall)var1;
      double var4 = (float)var2.locX + 0.5F - var18.locX;
      double var6 = (float)var2.locY + 0.5F - var18.locY;
      double var8 = (float)var2.locZ + 0.5F - var18.locZ;
      double var10 = var4 * var4 + var6 * var6 + var8 * var8;
      var10 *= var10;
      
      if (var10 <= Math.pow(6.0D, 4.0D))
      {
        double var12 = var4 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        double var14 = var6 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        double var16 = var8 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        
        if (var12 > 0.1D)
        {
          var12 = 0.1D;
        }
        else if (var12 < -0.1D)
        {
          var12 = -0.1D;
        }
        
        if (var14 > 0.1D)
        {
          var14 = 0.1D;
        }
        else if (var14 < -0.1D)
        {
          var14 = -0.1D;
        }
        
        if (var16 > 0.1D)
        {
          var16 = 0.1D;
        }
        else if (var16 < -0.1D)
        {
          var16 = -0.1D;
        }
        
        var18.motX += var12 * 1.2D;
        var18.motY += var14 * 1.2D;
        var18.motZ += var16 * 1.2D;
      }
    }
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
      
      if (var2.getMaterial(var25, var26, var27) == Material.WATER)
      {
        var2.setTypeId(var25, var26, var27, 0);
      }
      else if (var2.getMaterial(var25, var26 + 1, var27) == Material.WATER)
      {
        var2.setTypeId(var25, var26 + 1, var27, 0);
      }
      else if (var2.getMaterial(var25, var26, var27) == Material.LAVA)
      {
        var2.setTypeId(var25, var26, var27, 0);
      }
      else if (var2.getMaterial(var25, var26 + 1, var27) == Material.LAVA)
      {
        var2.setTypeId(var25, var26 + 1, var27, 0);
      }
    }
    
    return var1;
  }
  


  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (!EEProxy.isClient(var2))
    {
      List var4 = var2.a(EntityItem.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
      Iterator var6 = var4.iterator();
      
      while (var6.hasNext())
      {
        Entity var5 = (Entity)var6.next();
        PullItems(var5, var3);
      }
      
      List var11 = var2.a(EntityLootBall.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
      Iterator var8 = var11.iterator();
      
      while (var8.hasNext())
      {
        Entity var7 = (Entity)var8.next();
        PullItems(var7, var3);
      }
      
      List var12 = var3.world.a(EntityExperienceOrb.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
      Iterator var10 = var12.iterator();
      
      while (var10.hasNext())
      {
        Entity var9 = (Entity)var10.next();
        PullItems(var9, var3);
      }
    }
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
  }
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
  {
    if (isActivated(var1.getData()))
    {
      var1.setData(0);
      var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
    else
    {
      var1.setData(1);
      var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
  
  public boolean canActivate()
  {
    return true;
  }
}
