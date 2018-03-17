package ee;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockDeadBush;
import net.minecraft.server.BlockLongGrass;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMushroomCow;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemRedShears extends ItemRedTool
{
  private static Block[] blocksEffectiveAgainst = { Block.LEAVES, Block.WEB, Block.WOOL };
  private static boolean leafHit;
  private static boolean vineHit;
  
  public ItemRedShears(int var1)
  {
    super(var1, 3, 12, blocksEffectiveAgainst);
  }
  
  public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
  {
    boolean var7 = false;
    
    if ((!EEMaps.isLeaf(var2)) && (var2 != Block.WEB.id) && (var2 != Block.VINE.id) && (var2 != net.minecraft.server.BlockFlower.LONG_GRASS.id) && (var2 != net.minecraft.server.BlockFlower.DEAD_BUSH.id))
    {
      var7 = true;
    }
    
    if (!var7)
    {
      net.minecraft.server.EEProxy.dropBlockAsItemStack(Block.byId[var2], var6, var3, var4, var5, new ItemStack(var2, 1, var2 == Block.LEAVES.id ? ((ItemEECharged)var1.getItem()).getShort(var1, "lastMeta") & 0x3 : ((ItemEECharged)var1.getItem()).getShort(var1, "lastMeta")));
    }
    
    return super.a(var1, var2, var3, var4, var5, var6);
  }
  



  public boolean canDestroySpecialBlock(Block var1)
  {
    return var1.id == Block.WEB.id;
  }
  




  public float getDestroySpeed(ItemStack var1, Block var2)
  {
    return (var2.id != Block.VINE.id) && (var2.id != Block.LEAVES.id) && (var2.id != Block.WEB.id) ? super.getDestroySpeed(var1, var2) : var2.id == Block.WOOL.id ? 5.0F : 15.0F;
  }
  
  public void doBreak(ItemStack var1, World var2, EntityHuman var3)
  {
    if (chargeLevel(var1) > 0)
    {
      int var4 = (int)EEBase.playerX(var3);
      int var5 = (int)EEBase.playerY(var3);
      int var6 = (int)EEBase.playerZ(var3);
      cleanDroplist(var1);
      
      if (chargeLevel(var1) < 1)
      {
        return;
      }
      
      var3.C_();
      var2.makeSound(var3, "flash", 0.8F, 1.5F);
      
      for (int var7 = -(chargeLevel(var1) + 2); var7 <= chargeLevel(var1) + 2; var7++)
      {
        for (int var8 = -(chargeLevel(var1) + 2); var8 <= chargeLevel(var1) + 2; var8++)
        {
          for (int var9 = -(chargeLevel(var1) + 2); var9 <= chargeLevel(var1) + 2; var9++)
          {
            int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
            
            if ((EEMaps.isLeaf(var10)) || (var10 == Block.VINE.id) || (var10 == Block.WEB.id) || (var10 == Block.LONG_GRASS.id) || (var10 == Block.DEAD_BUSH.id))
            {
              if (getFuelRemaining(var1) < 1)
              {
                ConsumeReagent(var1, var3, false);
              }
              
              if (getFuelRemaining(var1) > 0)
              {
                int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
                
                if ((!EEMaps.isLeaf(var10)) && (var10 != Block.VINE.id) && (var10 != Block.WEB.id) && (var10 != Block.LONG_GRASS.id) && (var10 != Block.DEAD_BUSH.id))
                {
                  java.util.ArrayList var12 = Block.byId[var10].getBlockDropped(var2, var4 + var7, var5 + var8, var6 + var9, var11, 0);
                  Iterator var13 = var12.iterator();
                  
                  while (var13.hasNext())
                  {
                    ItemStack var14 = (ItemStack)var13.next();
                    addToDroplist(var1, var14);
                  }
                }
                else if (var10 == Block.LEAVES.id)
                {
                  addToDroplist(var1, new ItemStack(Block.LEAVES.id, 1, var11 & 0x3));
                }
                else
                {
                  addToDroplist(var1, new ItemStack(Block.byId[var10], 1, var11));
                }
                
                setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
                var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
                
                if (var2.random.nextInt(8) == 0)
                {
                  var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.0D, 0.0D);
                }
                
                if (var2.random.nextInt(8) == 0)
                {
                  var2.a("explode", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.0D, 0.0D);
                }
              }
            }
          }
        }
      }
      
      ejectDropList(var2, var1, var4, var5, var6);
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
      boolean var8 = false;
      cleanDroplist(var1);
      
      if (chargeLevel(var1) < 1)
      {
        return false;
      }
      
      var2.C_();
      var3.makeSound(var2, "flash", 0.8F, 1.5F);
      
      for (int var9 = -(chargeLevel(var1) + 2); var9 <= chargeLevel(var1) + 2; var9++)
      {
        for (int var10 = -(chargeLevel(var1) + 2); var10 <= chargeLevel(var1) + 2; var10++)
        {
          for (int var11 = -(chargeLevel(var1) + 2); var11 <= chargeLevel(var1) + 2; var11++)
          {
            int var12 = var3.getTypeId(var4 + var9, var5 + var10, var6 + var11);
            
            if (((EEMaps.isLeaf(var12)) || (var12 == Block.VINE.id) || (var12 == Block.WEB.id) || (var12 == Block.LONG_GRASS.id) || (var12 == Block.DEAD_BUSH.id)) && (getFuelRemaining(var1) < 1))
            {
              int var13 = var3.getData(var4 + var9, var5 + var10, var6 + var11);
              java.util.ArrayList var14 = Block.byId[var12].getBlockDropped(var3, var4 + var9, var5 + var10, var6 + var11, var13, 0);
              Iterator var15 = var14.iterator();
              
              while (var15.hasNext())
              {
                ItemStack var16 = (ItemStack)var15.next();
                addToDroplist(var1, var16);
              }
              
              setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
              var3.setTypeId(var4 + var9, var5 + var10, var6 + var11, 0);
              
              if (var3.random.nextInt(8) == 0)
              {
                var3.a("largesmoke", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
              }
              
              if (var3.random.nextInt(8) == 0)
              {
                var3.a("explode", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
              }
            }
          }
        }
      }
      
      ejectDropList(var3, var1, var4, var5, var6);
    }
    
    return false;
  }
  




  public void doShear(ItemStack var1, World var2, EntityHuman var3, Entity var4)
  {
    if (chargeLevel(var1) > 0)
    {
      boolean var5 = false;
      int var6 = 0;
      
      if (getFuelRemaining(var1) < 10)
      {
        ConsumeReagent(var1, var3, false);
      }
      
      if (getFuelRemaining(var1) < 10)
      {
        ConsumeReagent(var1, var3, false);
      }
      
      if (getFuelRemaining(var1) < 10)
      {
        ConsumeReagent(var1, var3, false);
      }
      
      while ((getFuelRemaining(var1) >= 10) && (var6 < chargeLevel(var1)))
      {
        setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 10);
        var6++;
        
        if (getFuelRemaining(var1) < 10)
        {
          ConsumeReagent(var1, var3, false);
        }
        
        if (getFuelRemaining(var1) < 10)
        {
          ConsumeReagent(var1, var3, false);
        }
        
        if (getFuelRemaining(var1) < 10)
        {
          ConsumeReagent(var1, var3, false);
        }
      }
      
      if (var6 > 0)
      {
        var3.C_();
        var2.makeSound(var3, "flash", 0.8F, 1.5F);
        int var7 = 2 * var6;
        




        if ((var4 instanceof EntitySheep))
        {
          if (var2.random.nextInt(100) < var7)
          {
            EntitySheep var8 = new EntitySheep(var2);
            double var9 = var4.locX - var3.locX;
            double var11 = var4.locZ - var3.locZ;
            
            if (var9 < 0.0D)
            {
              var9 *= -1.0D;
            }
            
            if (var11 < 0.0D)
            {
              var11 *= -1.0D;
            }
            
            var9 += var4.locX;
            var11 += var4.locZ;
            double var13 = var4.locY;
            
            for (int var15 = -5; var15 <= 5; var15++)
            {
              if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
              {
                var8.setPosition(var9, var13 + var15 + 1.0D, var11);
                var8.setColor(((EntitySheep)var4).getColor());
                var2.addEntity(var8);
                break;
              }
            }
          }
          
          ((EntitySheep)var4).setSheared(true);
          int var19 = 3 + var2.random.nextInt(2) + chargeLevel(var1) / 4;
          EntityItem var21 = null;
          
          for (int var10 = 0; var10 < var19; var10++)
          {
            var21 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var19, ((EntitySheep)var4).getColor()));
          }
          
          var2.addEntity(var21);
        }
        else if ((var4 instanceof EntityMushroomCow))
        {
          if (var2.random.nextInt(100) < var7)
          {
            EntityMushroomCow var18 = new EntityMushroomCow(var2);
            double var9 = var4.locX - var3.locX;
            double var11 = var4.locZ - var3.locZ;
            
            if (var9 < 0.0D)
            {
              var9 *= -1.0D;
            }
            
            if (var11 < 0.0D)
            {
              var11 *= -1.0D;
            }
            
            var9 += var4.locX;
            var11 += var4.locZ;
            double var13 = var4.locY;
            
            for (int var15 = -5; var15 <= 5; var15++)
            {
              if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
              {
                var18.setPosition(var9, var13 + var15 + 1.0D, var11);
                var2.addEntity(var18);
                break;
              }
            }
          }
          
          ((EntityMushroomCow)var4).die();
          EntityCow var20 = new EntityCow(var2);
          var20.setPositionRotation(var4.locX, var4.locY, var4.locZ, var4.yaw, var4.pitch);
          var20.setHealth(((EntityMushroomCow)var4).getHealth());
          var20.V = ((EntityMushroomCow)var4).V;
          var2.addEntity(var20);
          var2.a("largeexplode", var4.locX, var4.locY + var4.length / 2.0F, var4.locZ, 0.0D, 0.0D, 0.0D);
          int var23 = 5 + var2.random.nextInt(2) + chargeLevel(var1) / 4;
          Object var22 = null;
          
          for (int var24 = 0; var24 < var23; var24++)
          {
            new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.RED_MUSHROOM, var23));
          }
        }
      }
    }
    else if ((var4 instanceof EntitySheep))
    {
      new EntitySheep(var2);
      ((EntitySheep)var4).setSheared(true);
      int var6 = 3 + var2.random.nextInt(2);
      EntityItem var17 = null;
      
      for (int var19 = 0; var19 < var6; var19++)
      {
        var17 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var6, ((EntitySheep)var4).getColor()));
      }
      
      var2.addEntity(var17);
    }
    else if ((var4 instanceof EntityMushroomCow))
    {
      ((EntityMushroomCow)var4).die();
      EntityCow var16 = new EntityCow(((EntityMushroomCow)var4).world);
      var16.setPositionRotation(((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY, ((EntityMushroomCow)var4).locZ, ((EntityMushroomCow)var4).yaw, ((EntityMushroomCow)var4).pitch);
      var16.setHealth(((EntityMushroomCow)var4).getHealth());
      var16.V = ((EntityMushroomCow)var4).V;
      ((EntityMushroomCow)var4).world.addEntity(var16);
      ((EntityMushroomCow)var4).world.a("largeexplode", ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length / 2.0F, ((EntityMushroomCow)var4).locZ, 0.0D, 0.0D, 0.0D);
      
      for (int var6 = 0; var6 < 5; var6++)
      {
        ((EntityMushroomCow)var4).world.addEntity(new EntityItem(((EntityMushroomCow)var4).world, ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length, ((EntityMushroomCow)var4).locZ, new ItemStack(Block.RED_MUSHROOM)));
      }
    }
  }
  



  public int a(Entity var1)
  {
    return (!(var1 instanceof EntitySheep)) && (!(var1 instanceof EntityMushroomCow)) ? this.weaponDamage : 1;
  }
  




  public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
  {
    if ((var3 instanceof EntityHuman))
    {
      EntityHuman var4 = (EntityHuman)var3;
      
      if ((var2 instanceof EntitySheep))
      {
        if (!((EntitySheep)var2).isSheared())
        {
          doShear(var1, var4.world, var4, var2);
        }
        
        var2.heal(1);
      }
      else if ((var2 instanceof EntityMushroomCow))
      {
        doShear(var1, var4.world, var4, var2);
        var2.heal(1);
      }
    }
    
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
  

  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doBreak(var1, var2, var3);
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3) {}
}
