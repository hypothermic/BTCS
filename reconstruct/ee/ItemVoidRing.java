package ee;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class ItemVoidRing extends ItemEECharged
{
  private int ticksLastSpent;
  
  public ItemVoidRing(int var1)
  {
    super(var1, 0);
  }
  
  public int getIconFromDamage(int var1)
  {
    return var1 < 2 ? this.textureId + var1 : this.textureId;
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
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doTeleport(var1, var2, var3);
  }
  
  private void doTeleport(ItemStack var1, World var2, EntityHuman var3)
  {
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
    double var21 = 150.0D;
    Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
    MovingObjectPosition var24 = var2.rayTrace(var13, var23, true);
    
    if (var24 != null)
    {
      if (var24.type == net.minecraft.server.EnumMovingObjectType.TILE)
      {
        int var25 = var24.b;
        int var26 = var24.c;
        int var27 = var24.d;
        int var28 = var24.face;
        
        if (var28 == 0)
        {
          var26 -= 2;
        }
        
        if (var28 == 1)
        {
          var26++;
        }
        
        if (var28 == 2)
        {
          var27--;
        }
        
        if (var28 == 3)
        {
          var27++;
        }
        
        if (var28 == 4)
        {
          var25--;
        }
        
        if (var28 == 5)
        {
          var25++;
        }
        
        for (int var29 = 0; var29 < 32; var29++)
        {
          var3.world.a("portal", var25, var26 + var3.world.random.nextDouble() * 2.0D, var27, var3.world.random.nextGaussian(), 0.0D, var3.world.random.nextGaussian());
        }
        
        if ((var3.world.getTypeId(var25, var26, var27) == 0) && (var3.world.getTypeId(var25, var26 + 1, var27) == 0))
        {
          var3.enderTeleportTo(var25, var26, var27);
        }
        
        var3.fallDistance = 0.0F;
      }
    }
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doDisintegrate(var1, var2, var3);
    return var1;
  }
  

  private void doDisintegrate(ItemStack var1, World var2, EntityHuman var3)
  {
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
    
    if (var24 != null)
    {
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
    }
  }
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (isActivated(var1))
    {
      doAttraction(var1, var2, var3);
      doCondense(var1, var2, var3);
    }
    
    if (!isActivated(var1.getData()))
    {
      dumpContents(var1, var3);
    }
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
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3) {}
  
  private void doAttraction(ItemStack var1, World var2, EntityHuman var3)
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
      
      List var12 = var3.world.a(net.minecraft.server.EntityExperienceOrb.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
      Iterator var10 = var12.iterator();
      
      while (var10.hasNext())
      {
        Entity var9 = (Entity)var10.next();
        PullItems(var9, var3);
      }
    }
  }
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
  {
    if (isActivated(var1))
    {
      var1.setData(0);
      var1.tag.setBoolean("active", false);
      var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
    else
    {
      var1.setData(1);
      var1.tag.setBoolean("active", true);
      var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
  }
  
  public boolean canActivate()
  {
    return true;
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
}
