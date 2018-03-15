package net.minecraft.server;

import java.util.List;
import java.util.Random;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockMinecartDetector extends BlockMinecartTrack
{
  public BlockMinecartDetector(int i, int j)
  {
    super(i, j, true);
    a(true);
  }
  
  public int d() {
    return 20;
  }
  
  public boolean isPowerSource() {
    return true;
  }
  
  public void a(World world, int i, int j, int k, Entity entity) {
    if (!world.isStatic) {
      int l = world.getData(i, j, k);
      
      if ((l & 0x8) == 0) {
        f(world, i, j, k, l);
      }
    }
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (!world.isStatic) {
      int l = world.getData(i, j, k);
      
      if ((l & 0x8) != 0) {
        f(world, i, j, k, l);
      }
    }
  }
  
  public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return (iblockaccess.getData(i, j, k) & 0x8) != 0;
  }
  
  public boolean d(World world, int i, int j, int k, int l) {
    return (world.getData(i, j, k) & 0x8) != 0;
  }
  
  private void f(World world, int i, int j, int k, int l) {
    boolean flag = (l & 0x8) != 0;
    boolean flag1 = false;
    float f = 0.125F;
    List list = world.a(EntityMinecart.class, AxisAlignedBB.b(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f));
    
    if (list.size() > 0) {
      flag1 = true;
    }
    

    if (flag != flag1) {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 1 : 0, flag1 ? 1 : 0);
      world.getServer().getPluginManager().callEvent(eventRedstone);
      
      flag1 = eventRedstone.getNewCurrent() > 0;
    }
    

    if ((flag1) && (!flag)) {
      world.setData(i, j, k, l | 0x8);
      world.applyPhysics(i, j, k, this.id);
      world.applyPhysics(i, j - 1, k, this.id);
      world.b(i, j, k, i, j, k);
    }
    
    if ((!flag1) && (flag)) {
      world.setData(i, j, k, l & 0x7);
      world.applyPhysics(i, j, k, this.id);
      world.applyPhysics(i, j - 1, k, this.id);
      world.b(i, j, k, i, j, k);
    }
    
    if (flag1) {
      world.c(i, j, k, this.id, d());
    }
  }
}
