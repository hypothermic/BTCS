package net.minecraft.server;

import java.io.PrintStream;
import java.util.Random;

public class WorldGenDungeons extends WorldGenerator
{
  public boolean a(World world, Random random, int i, int j, int k)
  {
    byte b0 = 3;
    int l = random.nextInt(2) + 2;
    int i1 = random.nextInt(2) + 2;
    int j1 = 0;
    for (int k1 = i - l - 1; k1 <= i + l + 1; k1++) {
      for (int l1 = j - 1; l1 <= j + b0 + 1; l1++) {
        for (int i2 = k - i1 - 1; i2 <= k + i1 + 1; i2++) {
          Material material = world.getMaterial(k1, l1, i2);
          
          if ((l1 == j - 1) && (!material.isBuildable())) {
            return false;
          }
          
          if ((l1 == j + b0 + 1) && (!material.isBuildable())) {
            return false;
          }
          
          if (((k1 == i - l - 1) || (k1 == i + l + 1) || (i2 == k - i1 - 1) || (i2 == k + i1 + 1)) && (l1 == j) && (world.isEmpty(k1, l1, i2)) && (world.isEmpty(k1, l1 + 1, i2))) {
            j1++;
          }
        }
      }
    }
    
    if ((j1 >= 1) && (j1 <= 5)) {
    int k1; // BTCS: moved outside of for loop
      for (k1 = i - l - 1; k1 <= i + l + 1; k1++) {
        for (int l1 = j + b0; l1 >= j - 1; l1--) {
          for (int i2 = k - i1 - 1; i2 <= k + i1 + 1; i2++) {
            if ((k1 != i - l - 1) && (l1 != j - 1) && (i2 != k - i1 - 1) && (k1 != i + l + 1) && (l1 != j + b0 + 1) && (i2 != k + i1 + 1)) {
              world.setTypeId(k1, l1, i2, 0);
            } else if ((l1 >= 0) && (!world.getMaterial(k1, l1 - 1, i2).isBuildable())) {
              world.setTypeId(k1, l1, i2, 0);
            } else if (world.getMaterial(k1, l1, i2).isBuildable()) {
              if ((l1 == j - 1) && (random.nextInt(4) != 0)) {
                world.setTypeId(k1, l1, i2, Block.MOSSY_COBBLESTONE.id);
              } else {
                world.setTypeId(k1, l1, i2, Block.COBBLESTONE.id);
              }
            }
          }
        }
      }
      
      k1 = 0; // BTCS: added decl 'int '
      
      while (k1 < 2) {
        int l1 = 0;
        

        while (l1 < 3)
        {
          int i2 = i + random.nextInt(l * 2 + 1) - l;
          int j2 = k + random.nextInt(i1 * 2 + 1) - i1;
          
          if (world.isEmpty(i2, j, j2)) {
            int k2 = 0;
            
            if (world.getMaterial(i2 - 1, j, j2).isBuildable()) {
              k2++;
            }
            
            if (world.getMaterial(i2 + 1, j, j2).isBuildable()) {
              k2++;
            }
            
            if (world.getMaterial(i2, j, j2 - 1).isBuildable()) {
              k2++;
            }
            
            if (world.getMaterial(i2, j, j2 + 1).isBuildable()) {
              k2++;
            }
            
            if (k2 == 1) {
              world.setTypeId(i2, j, j2, Block.CHEST.id);
              TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(i2, j, j2);
              
              if (tileentitychest == null) break;
              for (int l2 = 0; l2 < 8; l2++) {
                ItemStack itemstack = a(random);
                
                if (itemstack != null) {
                  tileentitychest.setItem(random.nextInt(tileentitychest.getSize()), itemstack);
                }
              }
              
              break;
            }
          }
          
          l1++;
        }
        


        k1++;
      }
      


      world.setTypeId(i, j, k, Block.MOB_SPAWNER.id);
      TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(i, j, k);
      
      if (tileentitymobspawner != null) {
        tileentitymobspawner.a(b(random));
      } else {
        System.err.println("Failed to fetch mob spawner entity at (" + i + ", " + j + ", " + k + ")");
      }
      
      return true;
    }
    return false;
  }
  
  private ItemStack a(Random random)
  {
    int i = random.nextInt(11);
    
    return i == 10 ? new ItemStack(Item.INK_SACK, 1, 3) : (i == 9) && (random.nextInt(10) == 0) ? new ItemStack(Item.byId[(Item.RECORD_1.id + random.nextInt(2))]) : (i == 8) && (random.nextInt(2) == 0) ? new ItemStack(Item.REDSTONE, random.nextInt(4) + 1) : (i == 7) && (random.nextInt(100) == 0) ? new ItemStack(Item.GOLDEN_APPLE) : i == 6 ? new ItemStack(Item.BUCKET) : i == 5 ? new ItemStack(Item.STRING, random.nextInt(4) + 1) : i == 4 ? new ItemStack(Item.SULPHUR, random.nextInt(4) + 1) : i == 3 ? new ItemStack(Item.WHEAT, random.nextInt(4) + 1) : i == 2 ? new ItemStack(Item.BREAD) : i == 1 ? new ItemStack(Item.IRON_INGOT, random.nextInt(4) + 1) : i == 0 ? new ItemStack(Item.SADDLE) : null;
  }
  
  private String b(Random random) {
    int i = random.nextInt(4);
    
    return i == 3 ? "Spider" : i == 2 ? "Zombie" : i == 1 ? "Zombie" : i == 0 ? "Skeleton" : "";
  }
}
