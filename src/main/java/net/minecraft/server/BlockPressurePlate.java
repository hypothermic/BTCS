package net.minecraft.server;

import java.util.List;
import java.util.Random;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.PluginManager;

public class BlockPressurePlate extends Block
{
  private EnumMobType a;
  
  protected BlockPressurePlate(int i, int j, EnumMobType enummobtype, Material material)
  {
    super(i, j, material);
    this.a = enummobtype;
    a(true);
    float f = 0.0625F;
    
    a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
  }
  
  public int d() {
    return 20;
  }
  
  public AxisAlignedBB e(World world, int i, int j, int k) {
    return null;
  }
  
  public boolean a() {
    return false;
  }
  
  public boolean b() {
    return false;
  }
  
  public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
    return true;
  }
  
  public boolean canPlace(World world, int i, int j, int k) {
    return (world.isBlockSolidOnSide(i, j - 1, k, 1)) || (world.getTypeId(i, j - 1, k) == Block.FENCE.id);
  }
  
  public void onPlace(World world, int i, int j, int k) {}
  
  public void doPhysics(World world, int i, int j, int k, int l) {
    boolean flag = false;
    
    if ((!world.isBlockSolidOnSide(i, j - 1, k, 1)) && (world.getTypeId(i, j - 1, k) != Block.FENCE.id)) {
      flag = true;
    }
    
    if (flag) {
      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setTypeId(i, j, k, 0);
    }
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    if ((!world.isStatic) && 
      (world.getData(i, j, k) != 0)) {
      g(world, i, j, k);
    }
  }
  
  public void a(World world, int i, int j, int k, Entity entity)
  {
    if ((!world.isStatic) && 
      (world.getData(i, j, k) != 1)) {
      g(world, i, j, k);
    }
  }
  
  private void g(World world, int i, int j, int k)
  {
    boolean flag = world.getData(i, j, k) == 1;
    boolean flag1 = false;
    float f = 0.125F;
    List list = null;
    
    if (this.a == EnumMobType.EVERYTHING) {
      list = world.getEntities((Entity)null, AxisAlignedBB.b(i + f, j, k + f, i + 1 - f, j + 0.25D, k + 1 - f));
    }
    
    if (this.a == EnumMobType.MOBS) {
      list = world.a(EntityLiving.class, AxisAlignedBB.b(i + f, j, k + f, i + 1 - f, j + 0.25D, k + 1 - f));
    }
    
    if (this.a == EnumMobType.PLAYERS) {
      list = world.a(EntityHuman.class, AxisAlignedBB.b(i + f, j, k + f, i + 1 - f, j + 0.25D, k + 1 - f));
    }
    
    if (list.size() > 0) {
      flag1 = true;
    }
    

    org.bukkit.World bworld = world.getWorld();
    PluginManager manager = world.getServer().getPluginManager();
    
    if (flag != flag1) {
      if (flag1) {
        for (Object object : list) {
          if (object != null) {
            Cancellable cancellable;
            if ((object instanceof EntityHuman)) {
              cancellable = org.bukkit.craftbukkit.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, org.bukkit.event.block.Action.PHYSICAL, i, j, k, -1, null);
            } else { if (!(object instanceof Entity)) continue;
              cancellable = new EntityInteractEvent(((Entity)object).getBukkitEntity(), bworld.getBlockAt(i, j, k));
              manager.callEvent((EntityInteractEvent)cancellable);
            }
            

            if (cancellable.isCancelled()) {
              return;
            }
          }
        }
      }
      
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bworld.getBlockAt(i, j, k), flag ? 1 : 0, flag1 ? 1 : 0);
      manager.callEvent(eventRedstone);
      
      flag1 = eventRedstone.getNewCurrent() > 0;
    }
    

    if ((flag1) && (!flag)) {
      world.setData(i, j, k, 1);
      world.applyPhysics(i, j, k, this.id);
      world.applyPhysics(i, j - 1, k, this.id);
      world.b(i, j, k, i, j, k);
      world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.3F, 0.6F);
    }
    
    if ((!flag1) && (flag)) {
      world.setData(i, j, k, 0);
      world.applyPhysics(i, j, k, this.id);
      world.applyPhysics(i, j - 1, k, this.id);
      world.b(i, j, k, i, j, k);
      world.makeSound(i + 0.5D, j + 0.1D, k + 0.5D, "random.click", 0.3F, 0.5F);
    }
    
    if (flag1) {
      world.c(i, j, k, this.id, d());
    }
  }
  
  public void remove(World world, int i, int j, int k) {
    int l = world.getData(i, j, k);
    
    if (l > 0) {
      world.applyPhysics(i, j, k, this.id);
      world.applyPhysics(i, j - 1, k, this.id);
    }
    
    super.remove(world, i, j, k);
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    boolean flag = iblockaccess.getData(i, j, k) == 1;
    float f = 0.0625F;
    
    if (flag) {
      a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
    } else {
      a(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
    }
  }
  
  public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    return iblockaccess.getData(i, j, k) > 0;
  }
  
  public boolean d(World world, int i, int j, int k, int l) {
    return world.getData(i, j, k) != 0;
  }
  
  public boolean isPowerSource() {
    return true;
  }
  
  public void f() {
    float f = 0.5F;
    float f1 = 0.125F;
    float f2 = 0.5F;
    
    a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
  }
  
  public int g() {
    return 1;
  }
}
