package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class BlockStationary
  extends BlockFluids
{
  protected BlockStationary(int i, Material material)
  {
    super(i, material);
    a(false);
    if (material == Material.LAVA) {
      a(true);
    }
  }
  
  public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
    return this.material != Material.LAVA;
  }
  
  public void doPhysics(World world, int i, int j, int k, int l) {
    super.doPhysics(world, i, j, k, l);
    if (world.getTypeId(i, j, k) == this.id) {
      i(world, i, j, k);
    }
  }
  
  private void i(World world, int i, int j, int k) {
    int l = world.getData(i, j, k);
    
    world.suppressPhysics = true;
    world.setRawTypeIdAndData(i, j, k, this.id - 1, l);
    world.b(i, j, k, i, j, k);
    world.c(i, j, k, this.id - 1, d());
    world.suppressPhysics = false;
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (this.material == Material.LAVA) {
      int l = random.nextInt(3);
      org.bukkit.World bworld = world.getWorld();
      BlockIgniteEvent.IgniteCause igniteCause = BlockIgniteEvent.IgniteCause.LAVA;
      int i1; // BTCS
      for (i1 = 0; i1 < l; i1++) {
        i += random.nextInt(3) - 1;
        j++;
        k += random.nextInt(3) - 1;
        int j1 = world.getTypeId(i, j, k);
        if (j1 == 0) {
          if ((j(world, i - 1, j, k)) || (j(world, i + 1, j, k)) || (j(world, i, j, k - 1)) || (j(world, i, j, k + 1)) || (j(world, i, j - 1, k)) || (j(world, i, j + 1, k)))
          {
            org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
            if ((block.getTypeId() == Block.FIRE.id) || 
              (!((BlockIgniteEvent)CraftEventFactory.callEvent(new BlockIgniteEvent(block, igniteCause, null))).isCancelled()))
            {




              world.setTypeId(i, j, k, Block.FIRE.id);
            }
          }
        } else if (Block.byId[j1].material.isSolid()) {
          return;
        }
      }
      
      if (l == 0) {
        i1 = i;
        int j1 = k;
        
        for (int k1 = 0; k1 < 3; k1++) {
          i = i1 + random.nextInt(3) - 1;
          k = j1 + random.nextInt(3) - 1;
          if ((world.isEmpty(i, j + 1, k)) && (j(world, i, j, k)))
          {
            org.bukkit.block.Block block = bworld.getBlockAt(i, j + 1, k);
            if ((block.getTypeId() == Block.FIRE.id) || 
              (!((BlockIgniteEvent)CraftEventFactory.callEvent(new BlockIgniteEvent(block, igniteCause, null))).isCancelled()))
            {

              world.setTypeId(i, j + 1, k, Block.FIRE.id); }
          }
        }
      }
    }
  }
  
  private boolean j(World world, int i, int j, int k) {
    return world.getMaterial(i, j, k).isBurnable();
  }
}
