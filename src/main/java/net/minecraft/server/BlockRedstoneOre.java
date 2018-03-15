package net.minecraft.server;

import java.util.Random;
import org.bukkit.event.entity.EntityInteractEvent;

public class BlockRedstoneOre extends Block
{
  private boolean a;
  
  public BlockRedstoneOre(int i, int j, boolean flag)
  {
    super(i, j, Material.STONE);
    if (flag) {
      a(true);
    }
    
    this.a = flag;
  }
  
  public int d() {
    return 30;
  }
  
  public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {
    g(world, i, j, k);
    super.attack(world, i, j, k, entityhuman);
  }
  
  public void b(World world, int i, int j, int k, Entity entity)
  {
    if ((entity instanceof EntityHuman)) {
      org.bukkit.event.player.PlayerInteractEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
      if (!event.isCancelled()) {
        g(world, i, j, k);
        super.b(world, i, j, k, entity);
      }
    } else {
      EntityInteractEvent event = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
      world.getServer().getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
        g(world, i, j, k);
        super.b(world, i, j, k, entity);
      }
    }
  }
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman)
  {
    g(world, i, j, k);
    return super.interact(world, i, j, k, entityhuman);
  }
  
  private void g(World world, int i, int j, int k) {
    h(world, i, j, k);
    if (this.id == Block.REDSTONE_ORE.id) {
      world.setTypeId(i, j, k, Block.GLOWING_REDSTONE_ORE.id);
    }
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if (this.id == Block.GLOWING_REDSTONE_ORE.id) {
      world.setTypeId(i, j, k, Block.REDSTONE_ORE.id);
    }
  }
  
  public int getDropType(int i, Random random, int j) {
    return Item.REDSTONE.id;
  }
  
  public int getDropCount(int i, Random random) {
    return a(random) + random.nextInt(i + 1);
  }
  
  public int a(Random random) {
    return 4 + random.nextInt(2);
  }
  
  private void h(World world, int i, int j, int k) {
    Random random = world.random;
    double d0 = 0.0625D;
    
    for (int l = 0; l < 6; l++) {
      double d1 = i + random.nextFloat();
      double d2 = j + random.nextFloat();
      double d3 = k + random.nextFloat();
      
      if ((l == 0) && (!world.r(i, j + 1, k))) {
        d2 = j + 1 + d0;
      }
      
      if ((l == 1) && (!world.r(i, j - 1, k))) {
        d2 = j + 0 - d0;
      }
      
      if ((l == 2) && (!world.r(i, j, k + 1))) {
        d3 = k + 1 + d0;
      }
      
      if ((l == 3) && (!world.r(i, j, k - 1))) {
        d3 = k + 0 - d0;
      }
      
      if ((l == 4) && (!world.r(i + 1, j, k))) {
        d1 = i + 1 + d0;
      }
      
      if ((l == 5) && (!world.r(i - 1, j, k))) {
        d1 = i + 0 - d0;
      }
      
      if ((d1 < i) || (d1 > i + 1) || (d2 < 0.0D) || (d2 > j + 1) || (d3 < k) || (d3 > k + 1)) {
        world.a("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
      }
    }
  }
  
  protected ItemStack a_(int i) {
    return new ItemStack(Block.REDSTONE_ORE);
  }
}
