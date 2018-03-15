package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.util.BlockStateListPopulator;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockPumpkin extends BlockDirectional
{
  private boolean a;
  
  protected BlockPumpkin(int i, int j, boolean flag)
  {
    super(i, Material.PUMPKIN);
    this.textureId = j;
    a(true);
    this.a = flag;
  }
  
  public int a(int i, int j) {
    if (i == 1)
      return this.textureId;
    if (i == 0) {
      return this.textureId;
    }
    int k = this.textureId + 1 + 16;
    
    if (this.a) {
      k++;
    }
    
    return (j == 1) && (i == 4) ? k : (j == 0) && (i == 3) ? k : (j == 3) && (i == 5) ? k : (j == 2) && (i == 2) ? k : this.textureId + 16;
  }
  
  public int a(int i)
  {
    return i == 3 ? this.textureId + 1 + 16 : i == 0 ? this.textureId : i == 1 ? this.textureId : this.textureId + 16;
  }
  
  public void onPlace(World world, int i, int j, int k) {
    super.onPlace(world, i, j, k);
    if (world.suppressPhysics) return;
    if ((world.getTypeId(i, j - 1, k) == Block.SNOW_BLOCK.id) && (world.getTypeId(i, j - 2, k) == Block.SNOW_BLOCK.id)) {
      if (!world.isStatic)
      {
        BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
        
        blockList.setTypeId(i, j, k, 0);
        blockList.setTypeId(i, j - 1, k, 0);
        blockList.setTypeId(i, j - 2, k, 0);
        
        EntitySnowman entitysnowman = new EntitySnowman(world);
        
        entitysnowman.setPositionRotation(i + 0.5D, j - 1.95D, k + 0.5D, 0.0F, 0.0F);
        if (world.addEntity(entitysnowman, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) { // BTCS edit
          blockList.updateList();
        }
      }
      

      for (int l = 0; l < 120; l++) {
        world.a("snowshovel", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 2.5D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
      }
    } else if ((world.getTypeId(i, j - 1, k) == Block.IRON_BLOCK.id) && (world.getTypeId(i, j - 2, k) == Block.IRON_BLOCK.id)) {
      boolean flag = (world.getTypeId(i - 1, j - 1, k) == Block.IRON_BLOCK.id) && (world.getTypeId(i + 1, j - 1, k) == Block.IRON_BLOCK.id);
      boolean flag1 = (world.getTypeId(i, j - 1, k - 1) == Block.IRON_BLOCK.id) && (world.getTypeId(i, j - 1, k + 1) == Block.IRON_BLOCK.id);
      
      if ((flag) || (flag1))
      {
        BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
        
        blockList.setTypeId(i, j, k, 0);
        blockList.setTypeId(i, j - 1, k, 0);
        blockList.setTypeId(i, j - 2, k, 0);
        if (flag) {
          blockList.setTypeId(i - 1, j - 1, k, 0);
          blockList.setTypeId(i + 1, j - 1, k, 0);
        } else {
          blockList.setTypeId(i, j - 1, k - 1, 0);
          blockList.setTypeId(i, j - 1, k + 1, 0);
        }
        
        EntityIronGolem entityirongolem = new EntityIronGolem(world);
        
        entityirongolem.b(true);
        entityirongolem.setPositionRotation(i + 0.5D, j - 1.95D, k + 0.5D, 0.0F, 0.0F);
        if (world.addEntity(entityirongolem, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)) { // BTCS edit
          for (int i1 = 0; i1 < 120; i1++) {
            world.a("snowballpoof", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 3.9D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
          }
          
          blockList.updateList();
        }
      }
    }
  }
  
  public boolean canPlace(World world, int i, int j, int k)
  {
    int l = world.getTypeId(i, j, k);
    
    return ((l == 0) || (Block.byId[l].material.isReplacable())) && (world.e(i, j - 1, k));
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
    int l = MathHelper.floor(entityliving.yaw * 4.0F / 360.0F + 2.5D) & 0x3;
    
    world.setData(i, j, k, l);
  }
  
  public void doPhysics(World world, int i, int j, int k, int l)
  {
    if ((Block.byId[l] != null) && (Block.byId[l].isPowerSource())) {
      org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
      int power = block.getBlockPower();
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
      world.getServer().getPluginManager().callEvent(eventRedstone);
    }
  }
}
