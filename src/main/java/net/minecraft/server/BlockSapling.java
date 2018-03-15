package net.minecraft.server;

import java.util.Random;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.TreeType;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.util.StructureGrowDelegate;
import org.bukkit.entity.Player;
import org.bukkit.event.world.StructureGrowEvent;

public class BlockSapling extends BlockFlower
{
  protected BlockSapling(int i, int j)
  {
    super(i, j);
    float f = 0.4F;
    
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      super.a(world, i, j, k, random);
      if ((world.getLightLevel(i, j + 1, k) >= 9) && (random.nextInt(7) == 0)) {
        int l = world.getData(i, j, k);
        
        if ((l & 0x8) == 0) {
          world.setData(i, j, k, l | 0x8);
        } else {
          grow(world, i, j, k, random, false, null, null);
        }
      }
    }
  }
  
  public int a(int i, int j) {
    j &= 0x3;
    return j == 3 ? 30 : j == 2 ? 79 : j == 1 ? 63 : super.a(i, j);
  }
  
  public void grow(World world, int i, int j, int k, Random random, boolean bonemeal, Player player, ItemStack itemstack)
  {
    int l = world.getData(i, j, k) & 0x3;
    int i1 = 0;
    int j1 = 0;
    
    StructureGrowDelegate delegate = new StructureGrowDelegate(world);
    TreeType treeType = null;
    TreeGenerator gen = null;
    boolean grownTree = false;
    boolean flag = false;
    
    if (l == 1) {
      treeType = TreeType.REDWOOD;
      gen = new WorldGenTaiga2(false);
    } else if (l == 2) {
      treeType = TreeType.BIRCH;
      gen = new WorldGenForest(false);
    } else if (l == 3) {
      for (i1 = 0; i1 >= -1; i1--) {
        for (j1 = 0; j1 >= -1; j1--) {
          if ((f(world, i + i1, j, k + j1, 3)) && (f(world, i + i1 + 1, j, k + j1, 3)) && (f(world, i + i1, j, k + j1 + 1, 3)) && (f(world, i + i1 + 1, j, k + j1 + 1, 3))) {
            treeType = TreeType.JUNGLE;
            gen = new WorldGenMegaTree(false, 10 + random.nextInt(20), 3, 3);
            flag = true;
            break;
          }
        }
        
        if (gen != null) {
          break;
        }
      }
      
      if (gen == null) {
        j1 = 0;
        i1 = 0;
        treeType = TreeType.SMALL_JUNGLE;
        gen = new WorldGenTrees(false, 4 + random.nextInt(7), 3, 3, false);
      }
    } else {
      treeType = TreeType.TREE;
      gen = new WorldGenTrees(false);
      if (random.nextInt(10) == 0) {
        treeType = TreeType.BIG_TREE;
        gen = new WorldGenBigTree(false);
      }
    }
    
    if (flag) {
      world.setRawTypeId(i + i1, j, k + j1, 0);
      world.setRawTypeId(i + i1 + 1, j, k + j1, 0);
      world.setRawTypeId(i + i1, j, k + j1 + 1, 0);
      world.setRawTypeId(i + i1 + 1, j, k + j1 + 1, 0);
    } else {
      world.setRawTypeId(i, j, k, 0);
    }
    grownTree = gen.generate((BlockChangeDelegate) delegate, random, i + i1, j, k + j1); // BTCS: added cast (BlockChangeDelecate)
    if (grownTree) {
      org.bukkit.Location location = new org.bukkit.Location(world.getWorld(), i, j, k);
      StructureGrowEvent event = new StructureGrowEvent(location, treeType, bonemeal, player, delegate.getBlocks());
      org.bukkit.Bukkit.getPluginManager().callEvent(event);
      if (event.isCancelled()) {
        grownTree = false;
      } else {
        for (BlockState state : event.getBlocks()) {
          state.update(true);
        }
        if ((event.isFromBonemeal()) && (itemstack != null)) {
          itemstack.count -= 1;
        }
      }
    }
    if (!grownTree) {
      if (flag) {
        world.setRawTypeIdAndData(i + i1, j, k + j1, this.id, l);
        world.setRawTypeIdAndData(i + i1 + 1, j, k + j1, this.id, l);
        world.setRawTypeIdAndData(i + i1, j, k + j1 + 1, this.id, l);
        world.setRawTypeIdAndData(i + i1 + 1, j, k + j1 + 1, this.id, l);
      } else {
        world.setRawTypeIdAndData(i, j, k, this.id, l);
      }
    }
  }
  
  public boolean f(World world, int i, int j, int k, int l)
  {
    return (world.getTypeId(i, j, k) == this.id) && ((world.getData(i, j, k) & 0x3) == l);
  }
  
  protected int getDropData(int i) {
    return i & 0x3;
  }
  
  public static abstract interface TreeGenerator
  {
    public abstract boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3);
    
    public abstract boolean generate(BlockChangeDelegate paramBlockChangeDelegate, Random paramRandom, int paramInt1, int paramInt2, int paramInt3);
  }
}
