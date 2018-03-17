package ee;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.BlockGrass;
import net.minecraft.server.BlockLongGrass;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenBigTree;
import net.minecraft.server.WorldGenTaiga2;
import net.minecraft.server.WorldGenerator;

public class ItemHarvestRing extends ItemEECharged
{
  private int ticksLastSpent;
  public int tempMeta = 0;
  public int costCounter = 0;
  
  public ItemHarvestRing(int var1)
  {
    super(var1, 0);
  }
  
  public int getIconFromDamage(int var1)
  {
    return !isActivated(var1) ? this.textureId : this.textureId + 1;
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
                    
                    if (var2.random.nextInt(8) == 0)
                    {
                      var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                    }
                    
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
                  
                  if (var2.random.nextInt(8) == 0)
                  {
                    var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                  }
                  
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
                    var15 = new net.minecraft.server.WorldGenTrees(true);
                    
                    if (var2.random.nextInt(10) == 0)
                    {
                      var15 = new WorldGenBigTree(true);
                    }
                  }
                  
                  if (var2.random.nextInt(8) == 0)
                  {
                    var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                  }
                  
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
                  var15 = new net.minecraft.server.WorldGenTrees(true);
                  
                  if (var2.random.nextInt(10) == 0)
                  {
                    var15 = new WorldGenBigTree(true);
                  }
                }
                
                if (var2.random.nextInt(8) == 0)
                {
                  var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
                }
                
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
  
  public boolean ConsumeReagentBonemeal(EntityHuman var1, boolean var2)
  {
    return EEBase.Consume(new ItemStack(Item.INK_SACK, 4, 15), var1, var2);
  }
  
  public boolean ConsumeReagentSapling(EntityHuman var1, boolean var2)
  {
    if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 0), var1, var2))
    {
      this.tempMeta = 0;
      return true;
    }
    if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 1), var1, var2))
    {
      this.tempMeta = 1;
      return true;
    }
    if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 2), var1, var2))
    {
      this.tempMeta = 2;
      return true;
    }
    

    return false;
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
  




  public float getDestroySpeed(ItemStack var1, Block var2)
  {
    return 0.0F;
  }
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3)
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
              
              if (var2.random.nextInt(8) == 0)
              {
                var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
              }
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
                  
                  if (var2.random.nextInt(8) == 0)
                  {
                    var2.a("largesmoke", var4 + var7 + var11, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
                  }
                }
              }
              else if ((var2.getTypeId(var4 + var7, var5 + var8, var6 + var9 + var11) == 0) && (var2.getTypeId(var4 + var7, var5 + var8 - 1, var6 + var9 + var11) == Block.GRASS.id) && (var2.random.nextInt(1800) == 0))
              {
                var2.setTypeId(var4 + var7, var5 + var8, var6 + var9 + var11, var10);
                
                if (var2.random.nextInt(8) == 0)
                {
                  var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9 + var11, 0.0D, 0.05D, 0.0D);
                }
              }
            }
          }
        }
      }
    }
  }
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
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
              
              if (var2.random.nextInt(8) == 0)
              {
                var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
              }
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
              
              if (var2.random.nextInt(8) == 0)
              {
                var2.a("largesmoke", var4 + var7, var5 + var8 - 3, var6 + var9, 0.0D, 0.05D, 0.0D);
              }
            }
          }
          else
          {
            Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.05F, 1);
            Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
            var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
            
            if (var2.random.nextInt(8) == 0)
            {
              var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
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
            
            if (var3.random.nextInt(8) == 0)
            {
              var3.a("largesmoke", var14 + var4, var5, var9 + var6, 0.0D, 0.05D, 0.0D);
            }
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
            
            if (var3.random.nextInt(8) == 0)
            {
              var3.a("largesmoke", var10 + var4, var5, var11 + var6, 0.0D, 0.05D, 0.0D);
            }
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
        else if (((var11 == Block.DIRT.id) || (var11 == Block.GRASS.id)) && ((var12 == 0) || (var12 == BlockFlower.LONG_GRASS.id)) && (var9 % 4 == 0) && (var10 % 4 == 0) && (ConsumeReagentSapling(var2, false)))
        {
          if (var12 == BlockFlower.LONG_GRASS.id)
          {
            Block.byId[var12].dropNaturally(var3, var9 + var4, var5 + 1, var10 + var6, 1, 1.0F, 1);
            var3.setTypeId(var9 + var4, var5 + 1, var10 + var6, 0);
          }
          
          var3.setTypeIdAndData(var9 + var4, var5 + 1, var10 + var6, Block.SAPLING.id, this.tempMeta);
          
          if (var3.random.nextInt(8) == 0)
          {
            var3.a("largesmoke", var9 + var4, var5, var10 + var6, 0.0D, 0.05D, 0.0D);
          }
        }
      }
    }
    
    return true;
  }
  




  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.ConsumeReagentForDuration(var1, var2, var3);
  }
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
  {
    doFertilize(var1, var2, var3);
  }
  
  public boolean canActivate()
  {
    return true;
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
}
