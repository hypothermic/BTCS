package ee;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFire;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.BlockGrass;
import net.minecraft.server.BlockLeaves;
import net.minecraft.server.BlockLongGrass;
import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;
import net.minecraft.server.WorldData;
import net.minecraft.server.WorldGenTaiga2;
import net.minecraft.server.WorldGenTrees;
import net.minecraft.server.WorldGenerator;

public class ItemArcaneRing extends ItemEECharged
{
  private boolean initialized;
  
  public ItemArcaneRing(int var1)
  {
    super(var1, 0);
  }
  
  public void doGale(ItemStack var1, World var2, EntityHuman var3)
  {
    var2.makeSound(var3, "gust", 0.6F, 1.0F);
    var2.addEntity(new EntityWindEssence(var2, var3));
  }
  
  public void doInterdiction(ItemStack var1, World var2, EntityHuman var3)
  {
    List var4 = var2.a(EntityMonster.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
    Iterator var6 = var4.iterator();
    
    while (var6.hasNext())
    {
      Entity var5 = (Entity)var6.next();
      PushEntities(var5, var3);
    }
    
    List var11 = var2.a(net.minecraft.server.EntityArrow.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
    Iterator var8 = var11.iterator();
    
    while (var8.hasNext())
    {
      Entity var7 = (Entity)var8.next();
      PushEntities(var7, var3);
    }
    
    List var12 = var2.a(net.minecraft.server.EntityFireball.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
    Iterator var10 = var12.iterator();
    
    while (var10.hasNext())
    {
      Entity var9 = (Entity)var10.next();
      PushEntities(var9, var3);
    }
    
    if (!EEProxy.getWorldInfo(var2).isThundering())
    {
      EEProxy.getWorldInfo(var2).setThundering(true);
      EEProxy.getWorldInfo(var2).setThunderDuration(300);
    }
  }
  
  public void doThunder(ItemStack var1, World var2, EntityHuman var3)
  {
    List var4 = var2.a(EntityMonster.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
    Iterator var6 = var4.iterator();
    
    while (var6.hasNext())
    {
      Entity var5 = (Entity)var6.next();
      doBolt(var5, var1, var3);
    }
  }
  
  private void doBolt(Entity var1, ItemStack var2, EntityHuman var3)
  {
    if ((getThunderCooldown(var2) <= 0) && (var3.world.isChunkLoaded((int)var1.locX, (int)var1.locY, (int)var1.locZ)))
    {
      var3.world.strikeLightning(new net.minecraft.server.EntityWeatherLighting(var3.world, var1.locX, var1.locY, var1.locZ));
      resetThunderCooldown(var2);
    }
  }
  
  private int getThunderCooldown(ItemStack var1)
  {
    return getShort(var1, "thunderCooldown");
  }
  
  private void decThunderCooldown(ItemStack var1)
  {
    setShort(var1, "thunderCooldown", getThunderCooldown(var1) <= 0 ? 0 : getThunderCooldown(var1) - 1);
  }
  
  private void resetThunderCooldown(ItemStack var1)
  {
    setShort(var1, "thunderCooldown", 20);
  }
  
  private void PushEntities(Entity var1, EntityHuman var2)
  {
    if (!(var1 instanceof EntityHuman))
    {
      if ((var2.world.random.nextInt(1200) == 0) && (var2.world.isChunkLoaded((int)var1.locX, (int)var1.locY, (int)var1.locZ)))
      {
        var2.world.strikeLightning(new net.minecraft.server.EntityWeatherLighting(var2.world, var1.locX, var1.locY, var1.locZ));
      }
      
      double var4 = var2.locX + 0.5D - var1.locX;
      double var6 = var2.locY + 0.5D - var1.locY;
      double var8 = var2.locZ + 0.5D - var1.locZ;
      double var10 = var4 * var4 + var6 * var6 + var8 * var8;
      var10 *= var10;
      
      if (var10 <= Math.pow(6.0D, 4.0D))
      {
        double var12 = -(var4 * 0.019999999552965164D / var10) * Math.pow(6.0D, 3.0D);
        double var14 = -(var6 * 0.019999999552965164D / var10) * Math.pow(6.0D, 3.0D);
        double var16 = -(var8 * 0.019999999552965164D / var10) * Math.pow(6.0D, 3.0D);
        
        if (var12 > 0.0D)
        {
          var12 = 0.12000000000000001D;
        }
        else if (var12 < 0.0D)
        {
          var12 = -0.12000000000000001D;
        }
        
        if (var14 > 0.2D)
        {
          var14 = 0.12000000000000001D;
        }
        else if (var14 < -0.1D)
        {
          var14 = 0.12000000000000001D;
        }
        
        if (var16 > 0.0D)
        {
          var16 = 0.12000000000000001D;
        }
        else if (var16 < 0.0D)
        {
          var16 = -0.12000000000000001D;
        }
        
        var1.motX += var12;
        var1.motY += var14;
        var1.motZ += var16;
      }
    }
  }
  
  public boolean ConsumeReagentBonemeal(EntityHuman var1, boolean var2)
  {
    return EEBase.Consume(new ItemStack(Item.INK_SACK, 4, 15), var1, var2);
  }
  
  public boolean ConsumeReagentSapling(ItemStack var1, EntityHuman var2, boolean var3)
  {
    if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 0), var2, var3))
    {
      tempMeta(var1, 0);
      return true;
    }
    if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 1), var2, var3))
    {
      tempMeta(var1, 1);
      return true;
    }
    if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 2), var2, var3))
    {
      tempMeta(var1, 2);
      return true;
    }
    

    return false;
  }
  

  private void tempMeta(ItemStack var1, int var2)
  {
    ((ItemEECharged)var1.getItem()).setInteger(var1, "tempMeta", var2);
  }
  



  public void doPassiveHarvest(ItemStack var1, World var2, EntityHuman var3)
  {
    for (int var4 = -1; var4 <= 1; var4++)
    {
      for (int var5 = -1; var5 <= 1; var5++)
      {
        if (var2.getTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5) == Block.FIRE.id)
        {
          var2.setTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5, 0);
        }
      }
    }
    
    var4 = (int)EEBase.playerX(var3);
    int var5 = (int)EEBase.playerY(var3);
    int var6 = (int)EEBase.playerZ(var3);
    
    for (int var7 = -5; var7 <= 5; var7++)
    {
      for (int var8 = -5; var8 <= 5; var8++)
      {
        for (int var9 = -5; var9 <= 5; var9++)
        {
          int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
          

          if (var10 == Block.CROPS.id)
          {
            int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
            
            if ((var11 < 7) && (var2.random.nextInt(600) == 0))
            {
              var11++;
              var2.setData(var4 + var7, var5 + var8, var6 + var9, var11);
            }
          }
          else if ((var10 != BlockFlower.YELLOW_FLOWER.id) && (var10 != BlockFlower.RED_ROSE.id) && (var10 != BlockFlower.BROWN_MUSHROOM.id) && (var10 != BlockFlower.RED_MUSHROOM.id))
          {
            if ((var10 == Block.GRASS.id) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.random.nextInt(4000) == 0))
            {
              var2.setTypeId(var4 + var7, var5 + var8 + 1, var6 + var9, BlockFlower.LONG_GRASS.id);
              var2.setData(var4 + var7, var5 + var8 + 1, var6 + var9, 1);
            }
            
            if ((var10 == Block.DIRT.id) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.random.nextInt(800) == 0))
            {
              var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, Block.GRASS.id);
            }
            else if (((var10 == Block.SUGAR_CANE_BLOCK.id) || (var10 == Block.CACTUS.id)) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) != Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) != Block.CACTUS.id) && (var2.random.nextInt(600) == 0))
            {
              var2.setTypeId(var4 + var7, var5 + var8 + 1, var6 + var9, var10);
              var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
            }
          }
          else if (var2.random.nextInt(2) == 0)
          {
            for (int var11 = -1; var11 < 0; var11++)
            {
              if ((var2.getTypeId(var4 + var7 + var11, var5 + var8, var6 + var9) == 0) && (var2.getTypeId(var4 + var7 + var11, var5 + var8 - 1, var6 + var9) == Block.GRASS.id))
              {
                if (var2.random.nextInt(800) == 0)
                {
                  var2.setTypeId(var4 + var7 + var11, var5 + var8, var6 + var9, var10);
                  var2.a("largesmoke", var4 + var7 + var11, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
                }
              }
              else if ((var2.getTypeId(var4 + var7, var5 + var8, var6 + var9 + var11) == 0) && (var2.getTypeId(var4 + var7, var5 + var8 - 1, var6 + var9 + var11) == Block.GRASS.id) && (var2.random.nextInt(1800) == 0))
              {
                var2.setTypeId(var4 + var7, var5 + var8, var6 + var9 + var11, var10);
                var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9 + var11, 0.0D, 0.05D, 0.0D);
              }
            }
          }
        }
      }
    }
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    if (EEProxy.isClient(var3))
    {
      return false;
    }
    





    if (var3.getTypeId(var4, var5, var6) == 60)
    {
      for (int var14 = -5; var14 <= 5; var14++)
      {
        for (int var9 = -5; var9 <= 5; var9++)
        {
          int var10 = var3.getTypeId(var14 + var4, var5, var9 + var6);
          int var11 = var3.getTypeId(var14 + var4, var5 + 1, var9 + var6);
          
          if ((var10 == 60) && (var11 == 0) && (ConsumeReagentSeeds(var2, false)))
          {
            var3.setTypeId(var14 + var4, var5 + 1, var9 + var6, Block.CROPS.id);
            var3.a("largesmoke", var14 + var4, var5, var9 + var6, 0.0D, 0.05D, 0.0D);
          }
        }
      }
      
      return true;
    }
    



    if (var3.getTypeId(var4, var5, var6) == 12)
    {
      double var15 = EEBase.direction(var2);
      
      if (var15 == 5.0D)
      {
        var4 += 5;
      }
      else if (var15 == 4.0D)
      {
        var6 -= 5;
      }
      else if (var15 == 3.0D)
      {
        var4 -= 5;
      }
      else if (var15 == 2.0D)
      {
        var6 += 5;
      }
      
      for (int var10 = -5; var10 <= 5; var10++)
      {
        for (int var11 = -5; var11 <= 5; var11++)
        {
          int var12 = var3.getTypeId(var10 + var4, var5, var11 + var6);
          int var13 = var3.getTypeId(var10 + var4, var5 + 1, var11 + var6);
          
          if ((var12 == 12) && (var13 == 0) && (var10 % 5 == 0) && (var11 % 5 == 0) && (ConsumeReagentCactus(var2, false)))
          {
            var3.setTypeId(var10 + var4, var5 + 1, var11 + var6, Block.CACTUS.id);
            var3.a("largesmoke", var10 + var4, var5, var11 + var6, 0.0D, 0.05D, 0.0D);
          }
        }
      }
      
      return true;
    }
    

    boolean var8 = false;
    
    if ((var3.getTypeId(var4, var5, var6) != Block.DIRT.id) && (var3.getTypeId(var4, var5, var6) != Block.GRASS.id) && (var3.getTypeId(var4, var5, var6) != BlockFlower.LONG_GRASS.id))
    {
      return false;
    }
    

    if (var3.getTypeId(var4, var5, var6) == BlockFlower.LONG_GRASS.id)
    {
      Block.byId[BlockFlower.LONG_GRASS.id].dropNaturally(var3, var4, var5, var6, 1, 1.0F, 1);
      var3.setTypeId(var4, var5, var6, 0);
      var5--;
    }
    
    if ((var3.getMaterial(var4 + 1, var5, var6) == Material.WATER) || (var3.getMaterial(var4 - 1, var5, var6) == Material.WATER) || (var3.getMaterial(var4, var5, var6 + 1) == Material.WATER) || (var3.getMaterial(var4, var5, var6 - 1) == Material.WATER))
    {
      var8 = true;
    }
    
    for (int var9 = -8; var9 <= 8; var9++)
    {
      for (int var10 = -8; var10 <= 8; var10++)
      {
        int var11 = var3.getTypeId(var9 + var4, var5, var10 + var6);
        int var12 = var3.getTypeId(var9 + var4, var5 + 1, var10 + var6);
        
        if (var8)
        {
          if (((var3.getMaterial(var9 + var4 + 1, var5, var10 + var6) == Material.WATER) || (var3.getMaterial(var9 + var4 - 1, var5, var10 + var6) == Material.WATER) || (var3.getMaterial(var9 + var4, var5, var10 + var6 + 1) == Material.WATER) || (var3.getMaterial(var9 + var4, var5, var10 + var6 - 1) == Material.WATER)) && ((var11 == Block.DIRT.id) || (var11 == Block.GRASS.id)) && (var12 == 0) && (ConsumeReagentReed(var2, false)))
          {
            var3.setTypeId(var9 + var4, var5 + 1, var10 + var6, Block.SUGAR_CANE_BLOCK.id);
          }
        }
        else if (((var11 == Block.DIRT.id) || (var11 == Block.GRASS.id)) && ((var12 == 0) || (var12 == BlockFlower.LONG_GRASS.id)) && (var9 % 4 == 0) && (var10 % 4 == 0) && (ConsumeReagentSapling(var1, var2, false)))
        {
          if (var12 == BlockFlower.LONG_GRASS.id)
          {
            Block.byId[var12].dropNaturally(var3, var9 + var4, var5 + 1, var10 + var6, 1, 1.0F, 1);
            var3.setTypeId(var9 + var4, var5 + 1, var10 + var6, 0);
          }
          
          var3.setTypeIdAndData(var9 + var4, var5 + 1, var10 + var6, Block.SAPLING.id, getTempMeta(var1));
          var3.a("largesmoke", var9 + var4, var5, var10 + var6, 0.0D, 0.05D, 0.0D);
        }
      }
    }
    
    return true;
  }
  




  private int getTempMeta(ItemStack var1)
  {
    return ((ItemEECharged)var1.getItem()).getInteger(var1, "tempMeta");
  }
  
  public void doActiveHarvest(ItemStack var1, World var2, EntityHuman var3)
  {
    int var4 = (int)EEBase.playerX(var3);
    int var5 = (int)EEBase.playerY(var3);
    int var6 = (int)EEBase.playerZ(var3);
    
    for (int var7 = -5; var7 <= 5; var7++)
    {
      for (int var8 = -5; var8 <= 5; var8++)
      {
        for (int var9 = -5; var9 <= 5; var9++)
        {
          int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
          
          if (var10 == Block.CROPS.id)
          {
            int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
            
            if (var11 >= 7)
            {
              Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.05F, 1);
              Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
              var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
              var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
            }
            else if (var2.random.nextInt(400) == 0)
            {
              var11++;
              var2.setData(var4 + var7, var5 + var8, var6 + var9, var11);
            }
          }
          else if ((var10 != BlockFlower.YELLOW_FLOWER.id) && (var10 != BlockFlower.RED_ROSE.id) && (var10 != BlockFlower.BROWN_MUSHROOM.id) && (var10 != BlockFlower.RED_MUSHROOM.id) && (var10 != BlockFlower.LONG_GRASS.id))
          {
            if (((var10 == Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) == Block.SUGAR_CANE_BLOCK.id)) || ((var10 == Block.CACTUS.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) == Block.CACTUS.id)))
            {
              if (var10 == Block.SUGAR_CANE_BLOCK.id)
              {
                Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 3, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.25F, 1);
                Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 3, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
                var2.setTypeId(var4 + var7, var5 + var8 - 3, var6 + var9, 0);
              }
              else
              {
                Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 4, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.25F, 1);
                Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 4, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
                var2.setTypeId(var4 + var7, var5 + var8 - 4, var6 + var9, 0);
              }
              
              var2.a("largesmoke", var4 + var7, var5 + var8 - 3, var6 + var9, 0.0D, 0.05D, 0.0D);
            }
          }
          else
          {
            Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.05F, 1);
            Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
            var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
            var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
          }
        }
      }
    }
  }
  
  public boolean ConsumeReagentCactus(EntityHuman var1, boolean var2)
  {
    return EEBase.Consume(new ItemStack(Block.CACTUS, 1), var1, var2);
  }
  
  public boolean ConsumeReagentSeeds(EntityHuman var1, boolean var2)
  {
    return EEBase.Consume(new ItemStack(Item.SEEDS, 1), var1, var2);
  }
  
  public boolean ConsumeReagentReed(EntityHuman var1, boolean var2)
  {
    return EEBase.Consume(new ItemStack(Item.SUGAR_CANE, 1), var1, var2);
  }
  
  public void doFertilize(ItemStack var1, World var2, EntityHuman var3)
  {
    boolean var4 = false;
    boolean var5 = true;
    boolean var6 = true;
    int var7 = (int)EEBase.playerX(var3);
    int var8 = (int)EEBase.playerY(var3);
    int var9 = (int)EEBase.playerZ(var3);
    
    for (int var10 = -15; var10 <= 15; var10++)
    {
      for (int var11 = -15; var11 <= 15; var11++)
      {
        for (int var12 = -15; var12 <= 15; var12++)
        {
          int var13 = var2.getTypeId(var7 + var10, var8 + var11, var9 + var12);
          

          if ((3 >= var10) && (var10 >= -3) && (3 >= var11) && (var11 >= -3) && (3 >= var12) && (var12 >= -3))
          {
            if (var13 == Block.CROPS.id)
            {
              int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12);
              
              if (var14 < 7)
              {
                if (!var4)
                {
                  if (ConsumeReagentBonemeal(var3, var5))
                  {
                    if (var6)
                    {
                      var2.makeSound(var3, "flash", 0.7F, 1.0F);
                      var6 = false;
                    }
                    
                    var4 = true;
                    var14++;
                    var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                    var2.setData(var7 + var10, var8 + var11, var9 + var12, var14);
                  }
                  
                  var5 = false;
                }
                else
                {
                  if (var6)
                  {
                    var2.makeSound(var3, "flash", 0.7F, 1.0F);
                    var6 = false;
                  }
                  
                  var14++;
                  var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                  var2.setData(var7 + var10, var8 + var11, var9 + var12, var14);
                }
              }
            }
            else if ((var13 == Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var7 + var10, var8 + var11 - 4, var9 + var12) != Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var7 + var10, var8 + var11 + 1, var9 + var12) == 0))
            {
              if (!var4)
              {
                if (ConsumeReagentBonemeal(var3, var5))
                {
                  if (var6)
                  {
                    var2.makeSound(var3, "flash", 0.7F, 1.0F);
                    var6 = false;
                  }
                  
                  var4 = true;
                  var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.SUGAR_CANE_BLOCK.id);
                }
                
                var5 = false;
              }
              else
              {
                if (var6)
                {
                  var2.makeSound(var3, "flash", 0.7F, 1.0F);
                  var6 = false;
                }
                
                var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.SUGAR_CANE_BLOCK.id);
              }
            }
            else if ((var13 == Block.CACTUS.id) && (var2.getTypeId(var7 + var10, var8 + var11 - 4, var9 + var12) != Block.CACTUS.id) && (var2.getTypeId(var7 + var10, var8 + var11 + 1, var9 + var12) == 0))
            {
              if (!var4)
              {
                if (ConsumeReagentBonemeal(var3, var5))
                {
                  if (var6)
                  {
                    var2.makeSound(var3, "flash", 0.7F, 1.0F);
                    var6 = false;
                  }
                  
                  var4 = true;
                  var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.CACTUS.id);
                }
                
                var5 = false;
              }
              else
              {
                if (var6)
                {
                  var2.makeSound(var3, "flash", 0.7F, 1.0F);
                  var6 = false;
                }
                
                var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.CACTUS.id);
              }
            }
            
            if ((var13 == BlockFlower.RED_ROSE.id) || (var13 == BlockFlower.YELLOW_FLOWER.id) || (var13 == BlockFlower.BROWN_MUSHROOM.id) || (var13 == BlockFlower.RED_MUSHROOM.id))
            {
              for (int var14 = -1; var14 <= 0; var14++)
              {
                if (var2.getTypeId(var7 + var10 + var14, var8 + var11, var9 + var12) == 0)
                {
                  if (!var4)
                  {
                    if (ConsumeReagentBonemeal(var3, var5))
                    {
                      if (var6)
                      {
                        var2.makeSound(var3, "flash", 0.7F, 1.0F);
                        var6 = false;
                      }
                      
                      var4 = true;
                      var2.setTypeId(var7 + var10 + var14, var8 + var11, var9 + var12, var13);
                    }
                    
                    var5 = false;
                  }
                  else
                  {
                    if (var6)
                    {
                      var2.makeSound(var3, "flash", 0.7F, 1.0F);
                      var6 = false;
                    }
                    
                    var2.setTypeId(var7 + var10 + var14, var8 + var11, var9 + var12, var13);
                  }
                }
                else if (var2.getTypeId(var7 + var10, var8 + var11, var9 + var12 + var14) == 0)
                {
                  if (var4)
                  {
                    if (var6)
                    {
                      var2.makeSound(var3, "flash", 0.7F, 1.0F);
                      var6 = false;
                    }
                    
                    var2.setTypeId(var7 + var10, var8 + var11, var9 + var12 + var14, var13);
                    break;
                  }
                  
                  if (ConsumeReagentBonemeal(var3, var5))
                  {
                    if (var6)
                    {
                      var2.makeSound(var3, "flash", 0.7F, 1.0F);
                      var6 = false;
                    }
                    
                    var4 = true;
                    var2.setTypeId(var7 + var10, var8 + var11, var9 + var12 + var14, var13);
                  }
                  
                  var5 = false;
                }
              }
            }
          }
          
          if (var13 == Block.SAPLING.id)
          {


            if (!var4)
            {
              if (ConsumeReagentBonemeal(var3, var5))
              {
                if (var6)
                {
                  var2.makeSound(var3, "flash", 0.7F, 1.0F);
                  var6 = false;
                }
                
                var4 = true;
                
                if (var2.random.nextInt(100) < 25)
                {
                  int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12) & 0x3;
                  var2.setRawTypeId(var7 + var10, var8 + var11, var9 + var12, 0);
                  Object var15 = null;
                  
                  if (var14 == 1)
                  {
                    var15 = new WorldGenTaiga2(true);
                  }
                  else if (var14 == 2)
                  {
                    var15 = new net.minecraft.server.WorldGenForest(true);
                  }
                  else
                  {
                    var15 = new WorldGenTrees(true);
                    
                    if (var2.random.nextInt(10) == 0)
                    {
                      var15 = new net.minecraft.server.WorldGenBigTree(true);
                    }
                  }
                  
                  var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                  
                  if (!((WorldGenerator)var15).a(var2, var2.random, var7 + var10, var8 + var11, var9 + var12))
                  {
                    var2.setRawTypeIdAndData(var7 + var10, var8 + var11, var9 + var12, Block.SAPLING.id, var14);
                  }
                }
              }
              
              var5 = false;
            }
            else
            {
              if (var6)
              {
                var2.makeSound(var3, "flash", 0.7F, 1.0F);
                var6 = false;
              }
              
              if (var2.random.nextInt(100) < 25)
              {
                int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12) & 0x3;
                var2.setRawTypeId(var7 + var10, var8 + var11, var9 + var12, 0);
                Object var15 = null;
                
                if (var14 == 1)
                {
                  var15 = new WorldGenTaiga2(true);
                }
                else if (var14 == 2)
                {
                  var15 = new net.minecraft.server.WorldGenForest(true);
                }
                else
                {
                  var15 = new WorldGenTrees(true);
                  
                  if (var2.random.nextInt(10) == 0)
                  {
                    var15 = new net.minecraft.server.WorldGenBigTree(true);
                  }
                }
                
                var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                
                if (!((WorldGenerator)var15).a(var2, var2.random, var7 + var10, var8 + var11, var9 + var12))
                {
                  var2.setRawTypeIdAndData(var7 + var10, var8 + var11, var9 + var12, Block.SAPLING.id, var14);
                }
              }
            }
          }
        }
      }
    }
  }
  
  public void doFireWall(ItemStack var1, World var2, EntityHuman var3)
  {
    byte var4 = 10;
    var2.makeSound(var3, "wall", 1.0F, 1.0F);
    int var5 = (int)EEBase.playerX(var3);
    int var6 = (int)EEBase.playerY(var3);
    int var7 = (int)EEBase.playerZ(var3);
    double var8 = net.minecraft.server.MathHelper.floor(var3.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    
    for (int var10 = -1; var10 <= 1; var10++)
    {
      for (int var11 = -2; var11 <= 1; var11++)
      {
        for (int var12 = -var4 * 3; var12 <= var4 * 3; var12++)
        {
          if (var8 == 3.0D)
          {
            if (((var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 + var10, var6 + var11 - 1, var7 + var12) != 0))
            {
              var2.setTypeId(var5 + var10, var6 + var11, var7 + var12, Block.FIRE.id);
            }
          }
          else if (var8 == 2.0D)
          {
            if (((var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 - var10) != 0))
            {
              var2.setTypeId(var5 + var12, var6 + var11, var7 - var10, Block.FIRE.id);
            }
          }
          else if (var8 == 1.0D)
          {
            if (((var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 - var10, var6 + var11 - 1, var7 + var12) != 0))
            {
              var2.setTypeId(var5 - var10, var6 + var11, var7 + var12, Block.FIRE.id);
            }
          }
          else if ((var8 == 0.0D) && ((var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 + var10) != 0))
          {
            var2.setTypeId(var5 + var12, var6 + var11, var7 + var10, Block.FIRE.id);
          }
        }
      }
    }
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.ConsumeReagentForDuration(var1, var2, var3);
  }
  




  public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
  {
    if (!EEProxy.isClient(var2))
    {
      if (!isInitialized(var1))
      {
        changeModes(var1);
        initialize(var1);
      }
      
      updateIcon(var1);
    }
  }
  
  public void doBurnOverTime(ItemStack var1, World var2, EntityHuman var3)
  {
    int var4 = (int)EEBase.playerX(var3);
    int var5 = (int)EEBase.playerY(var3);
    int var6 = (int)EEBase.playerZ(var3);
    List var7 = var2.a(EntityMonster.class, AxisAlignedBB.b(var3.locX - 5.0D, var3.locY - 5.0D, var3.locZ - 5.0D, var3.locX + 5.0D, var3.locY + 5.0D, var3.locZ + 5.0D));
    

    for (int var8 = 0; var8 < var7.size(); var8++)
    {
      if (var2.random.nextInt(30) == 0)
      {
        Entity var9 = (Entity)var7.get(var8);
        EEProxy.dealFireDamage(var9, 5);
        var9.setOnFire(60);
      }
    }
    
    for (var8 = -4; var8 <= 4; var8++)
    {
      for (int var13 = -4; var13 <= 4; var13++)
      {
        for (int var10 = -4; var10 <= 4; var10++)
        {
          if (((var8 <= -2) || (var8 >= 2) || (var13 != 0)) && ((var10 <= -2) || (var10 >= 2) || (var13 != 0)) && (var2.random.nextInt(120) == 0))
          {
            if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10) == 0) && (var2.getTypeId(var4 + var8, var5 + var13 - 1, var6 + var10) != 0))
            {
              var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
            }
            else
            {
              boolean var11 = false;
              

              for (int var12 = -1; var12 <= 1; var12++)
              {
                if ((var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LOG.id))
                {
                  var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
                  var11 = true;
                  break;
                }
              }
              
              if (!var11)
              {
                for (var12 = -1; var12 <= 1; var12++)
                {
                  if ((var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LOG.id))
                  {
                    var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
                    var11 = true;
                    break;
                  }
                }
              }
              
              if (!var11)
              {
                for (var12 = -1; var12 <= 1; var12++)
                {
                  if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LOG.id))
                  {
                    var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
                    var11 = true;
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public void doFreezeOverTime(ItemStack var1, World var2, EntityHuman var3)
  {
    int var4 = (int)EEBase.playerX(var3);
    int var5 = (int)EEBase.playerY(var3);
    int var6 = (int)EEBase.playerZ(var3);
    List var7 = var2.a(EntityMonster.class, AxisAlignedBB.b(var3.locX - 5.0D, var3.locY - 5.0D, var3.locZ - 5.0D, var3.locX + 5.0D, var3.locY + 5.0D, var3.locZ + 5.0D));
    

    for (int var8 = 0; var8 < var7.size(); var8++)
    {
      Entity var9 = (Entity)var7.get(var8);
      
      if ((var9.motX > 0.0D) || (var9.motZ > 0.0D))
      {
        var9.motX *= 0.2D;
        var9.motZ *= 0.2D;
      }
    }
    
    for (var8 = -4; var8 <= 4; var8++)
    {
      for (int var12 = -4; var12 <= 4; var12++)
      {
        for (int var10 = -4; var10 <= 4; var10++)
        {
          if (((var8 <= -2) || (var8 >= 2) || (var12 != 0)) && ((var10 <= -2) || (var10 >= 2) || (var12 != 0)))
          {
            if (var2.random.nextInt(20) == 0)
            {
              int var11 = var2.getTypeId(var4 + var8, var5 + var12 - 1, var6 + var10);
              
              if ((var11 != 0) && (Block.byId[var11].a()) && (var2.getMaterial(var4 + var8, var5 + var12 - 1, var6 + var10).isBuildable()) && (var2.getTypeId(var4 + var8, var5 + var12, var6 + var10) == 0))
              {
                var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.SNOW.id);
              }
            }
            
            if ((var2.random.nextInt(3) == 0) && (var2.getMaterial(var4 + var8, var5 + var12, var6 + var10) == Material.WATER) && (var2.getTypeId(var4 + var8, var5 + var12 + 1, var6 + var10) == 0))
            {
              var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.ICE.id);
            }
            
            if ((var2.random.nextInt(3) == 0) && (var2.getMaterial(var4 + var8, var5 + var12, var6 + var10) == Material.LAVA) && (var2.getTypeId(var4 + var8, var5 + var12 + 1, var6 + var10) == 0) && (var2.getData(var4 + var8, var5 + var12, var6 + var10) == 0))
            {
              var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.OBSIDIAN.id);
            }
          }
        }
      }
    }
  }
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    if (getMode(var1) == "wind")
    {
      doThunder(var1, var2, var3);
    }
    
    if (getMode(var1) == "ice")
    {
      doFreeze(var1, var2, var3);
    }
    
    if (getMode(var1) == "fire")
    {
      doFireWall(var1, var2, var3);
    }
    
    if (getMode(var1) == "earth")
    {
      doFertilize(var1, var2, var3);
    }
  }
  
  public void doFreeze(ItemStack var1, World var2, EntityHuman var3)
  {
    int var4 = chargeLevel(var1);
    var2.makeSound(var3, "wall", 1.0F, 1.0F);
    int var5 = (int)EEBase.playerX(var3);
    int var6 = (int)EEBase.playerY(var3);
    int var7 = (int)EEBase.playerZ(var3);
    
    for (int var8 = -var4 - 1; var8 <= var4 + 1; var8++)
    {
      for (int var9 = -2; var9 <= 1; var9++)
      {
        for (int var10 = -var4 - 1; var10 <= var4 + 1; var10++)
        {
          int var11 = var2.getTypeId(var5 + var10, var6 + var9 - 1, var7 + var8);
          
          if ((var11 != 0) && (Block.byId[var11].a()) && (var2.getMaterial(var5 + var10, var6 + var9 - 1, var7 + var8).isBuildable()) && (var2.getTypeId(var5 + var10, var6 + var9, var7 + var8) == 0))
          {
            var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.SNOW.id);
          }
          
          if ((var2.getMaterial(var5 + var10, var6 + var9, var7 + var8) == Material.WATER) && (var2.getTypeId(var5 + var10, var6 + var9 + 1, var7 + var8) == 0))
          {
            var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.ICE.id);
          }
          
          if ((var2.getMaterial(var5 + var10, var6 + var9, var7 + var8) == Material.LAVA) && (var2.getTypeId(var5 + var10, var6 + var9 + 1, var7 + var8) == 0) && (var2.getData(var5 + var10, var6 + var9, var7 + var8) == 0))
          {
            var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.OBSIDIAN.id);
          }
        }
      }
    }
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    if (getMode(var1) == "wind")
    {
      doGale(var1, var2, var3);
    }
    
    if (getMode(var1) == "ice")
    {
      doSnowball(var1, var2, var3);
    }
    
    if (getMode(var1) == "fire")
    {
      doFireball(var1, var2, var3);
    }
    
    return var1;
  }
  

  private void doSnowball(ItemStack var1, World var2, EntityHuman var3)
  {
    var3.C_();
    var2.makeSound(var3, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
    
    if (!var2.isStatic)
    {
      var2.addEntity(new net.minecraft.server.EntitySnowball(var2, var3));
    }
  }
  
  private void doFireball(ItemStack var1, World var2, EntityHuman var3)
  {
    var3.C_();
    var2.makeSound(var3, "wall", 1.0F, 1.0F);
    var2.addEntity(new EntityPyrokinesis(var2, var3));
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (var3.fallDistance >= 0.0F)
    {
      var3.fallDistance = 0.0F;
    }
    
    decThunderCooldown(var1);
    
    if (isActivated(var1))
    {
      if (getMode(var1) == "wind")
      {
        if (EEBase.getPlayerEffect(var1.getItem(), var3) <= 0)
        {
          ConsumeReagent(var1, var3, false);
        }
        
        if (EEBase.getPlayerEffect(var1.getItem(), var3) > 0)
        {
          doInterdiction(var1, var2, var3);
          EEBase.updatePlayerEffect(var1.getItem(), EEBase.getPlayerEffect(var1.getItem(), var3) - 1, var3);
        }
        else
        {
          doToggle(var1, var2, var3);
        }
      }
      
      if (getMode(var1) == "ice")
      {
        doFreezeOverTime(var1, var2, var3);
      }
      
      if (getMode(var1) == "fire")
      {
        doBurnOverTime(var1, var2, var3);
      }
    }
    
    if (getMode(var1) == "earth")
    {
      doPassiveHarvest(var1, var2, var3);
    }
  }
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (getMode(var1) == "earth")
    {
      doActiveHarvest(var1, var2, var3);
    }
  }
  
  private String getMode(ItemStack var1)
  {
    if ((getString(var1, "mode") == "") || (getString(var1, "mode") == null))
    {
      setMode(var1, "ice");
    }
    
    return getString(var1, "mode");
  }
  
  private void setMode(ItemStack var1, String var2)
  {
    setString(var1, "mode", var2);
  }
  
  private boolean isInitialized(ItemStack var1)
  {
    return getBoolean(var1, "init");
  }
  
  private void initialize(ItemStack var1)
  {
    setBoolean(var1, "init", true);
  }
  
  private void changeModes(ItemStack var1)
  {
    if (getMode(var1) == "ice")
    {
      setMode(var1, "fire");
      var1.setData(var1.getData() + 2);
    }
    else if (getMode(var1) == "fire")
    {
      setMode(var1, "wind");
      var1.setData(var1.getData() + 2);
    }
    else if (getMode(var1) == "wind")
    {
      setMode(var1, "earth");
      var1.setData(var1.getData() + 2);
    }
    else
    {
      setMode(var1, "ice");
      var1.setData(var1.getData() - 6);
    }
    
    updateIcon(var1);
  }
  
  private void updateIcon(ItemStack var1)
  {
    if ((getMode(var1) == "") || (getMode(var1) == null))
    {
      var1.setData(0);
      setMode(var1, "ice");
    }
    
    if (getMode(var1) == "earth")
    {
      var1.setData(6);
    }
    
    if (getMode(var1) == "wind")
    {
      var1.setData(4);
    }
    
    if (getMode(var1) == "fire")
    {
      var1.setData(2);
    }
    
    if (getMode(var1) == "ice")
    {
      var1.setData(0);
    }
    
    if ((isActivated(var1)) && ((var1.getData() & 0x1) == 0))
    {
      var1.setData(var1.getData() + 1);
    }
    else if ((!isActivated(var1)) && ((var1.getData() & 0x1) == 1))
    {
      var1.setData(var1.getData() - 1);
    }
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
  {
    changeModes(var1);
  }
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
  {
    if (isActivated(var1))
    {
      if ((var1.getData() & 0x1) == 0)
      {
        var1.setData(var1.getData() + 1);
      }
      
      var1.tag.setBoolean("active", false);
      var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
    else
    {
      if ((var1.getData() & 0x1) == 1)
      {
        var1.setData(var1.getData() - 1);
      }
      
      var1.tag.setBoolean("active", true);
      var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
    
    if (!EEProxy.isClient(var2))
    {
      updateIcon(var1);
    }
  }
  
  public boolean canActivate()
  {
    return true;
  }
}
