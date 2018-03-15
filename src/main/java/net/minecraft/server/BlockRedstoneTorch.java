package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.PluginManager;

public class BlockRedstoneTorch extends BlockTorch
{
  private boolean isOn = false;
  private static List b = new ArrayList();
  
  public int a(int i, int j) {
    return i == 1 ? Block.REDSTONE_WIRE.a(i, j) : super.a(i, j);
  }
  
  private boolean a(World world, int i, int j, int k, boolean flag) {
    if (flag) {
      b.add(new RedstoneUpdateInfo(i, j, k, world.getTime()));
    }
    
    int l = 0;
    
    for (int i1 = 0; i1 < b.size(); i1++) {
      RedstoneUpdateInfo redstoneupdateinfo = (RedstoneUpdateInfo)b.get(i1);
      
      if ((redstoneupdateinfo.a == i) && (redstoneupdateinfo.b == j) && (redstoneupdateinfo.c == k)) {
        l++;
        if (l >= 8) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  protected BlockRedstoneTorch(int i, int j, boolean flag) {
    super(i, j);
    this.isOn = flag;
    a(true);
  }
  
  public int d() {
    return 2;
  }
  
  public void onPlace(World world, int i, int j, int k) {
    if (world.getData(i, j, k) == 0) {
      super.onPlace(world, i, j, k);
    }
    
    if (this.isOn) {
      world.applyPhysics(i, j - 1, k, this.id);
      world.applyPhysics(i, j + 1, k, this.id);
      world.applyPhysics(i - 1, j, k, this.id);
      world.applyPhysics(i + 1, j, k, this.id);
      world.applyPhysics(i, j, k - 1, this.id);
      world.applyPhysics(i, j, k + 1, this.id);
    }
  }
  
  public void remove(World world, int i, int j, int k) {
    if (this.isOn) {
      world.applyPhysics(i, j - 1, k, this.id);
      world.applyPhysics(i, j + 1, k, this.id);
      world.applyPhysics(i - 1, j, k, this.id);
      world.applyPhysics(i + 1, j, k, this.id);
      world.applyPhysics(i, j, k - 1, this.id);
      world.applyPhysics(i, j, k + 1, this.id);
    }
  }
  
  public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    if (!this.isOn) {
      return false;
    }
    int i1 = iblockaccess.getData(i, j, k);
    
    return (i1 != 5) || (l != 1);
  }
  
  private boolean g(World world, int i, int j, int k)
  {
    int l = world.getData(i, j, k);
    
    return (l == 5) && (world.isBlockFaceIndirectlyPowered(i, j - 1, k, 0));
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    boolean flag = g(world, i, j, k);
    
    while ((b.size() > 0) && (world.getTime() - ((RedstoneUpdateInfo)b.get(0)).d > 60L)) {
      b.remove(0);
    }
    

    PluginManager manager = world.getServer().getPluginManager();
    org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
    int oldCurrent = this.isOn ? 15 : 0;
    
    BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
    

    if (this.isOn) {
      if (flag)
      {
        if (oldCurrent != 0) {
          event.setNewCurrent(0);
          manager.callEvent(event);
          if (event.getNewCurrent() != 0) {
            return;
          }
        }
        

        world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_OFF.id, world.getData(i, j, k));
        if (a(world, i, j, k, true)) {
          world.makeSound(i + 0.5F, j + 0.5F, k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
          
          for (int l = 0; l < 5; l++) {
            double d0 = i + random.nextDouble() * 0.6D + 0.2D;
            double d1 = j + random.nextDouble() * 0.6D + 0.2D;
            double d2 = k + random.nextDouble() * 0.6D + 0.2D;
            
            world.a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
          }
        }
      }
    } else if ((!flag) && (!a(world, i, j, k, false)))
    {
      if (oldCurrent != 15) {
        event.setNewCurrent(15);
        manager.callEvent(event);
        if (event.getNewCurrent() != 15) {
          return;
        }
      }
      

      world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_ON.id, world.getData(i, j, k));
    }
  }
  
  public void doPhysics(World world, int i, int j, int k, int l) {
    super.doPhysics(world, i, j, k, l);
    world.c(i, j, k, this.id, d());
  }
  
  public boolean d(World world, int i, int j, int k, int l) {
    return l == 0 ? a(world, i, j, k, l) : false;
  }
  
  public int getDropType(int i, Random random, int j) {
    return Block.REDSTONE_TORCH_ON.id;
  }
  
  public boolean isPowerSource() {
    return true;
  }
}
