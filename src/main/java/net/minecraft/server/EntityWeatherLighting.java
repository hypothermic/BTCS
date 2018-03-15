package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.block.BlockIgniteEvent;

public class EntityWeatherLighting extends EntityWeather
{
  private int lifeTicks;
  public long a = 0L;
  
  private int c;
  
  private CraftWorld cworld;
  public boolean isEffect = false;
  
  public EntityWeatherLighting(World world, double d0, double d1, double d2) {
    this(world, d0, d1, d2, false);
  }
  

  public EntityWeatherLighting(World world, double d0, double d1, double d2, boolean isEffect)
  {
    super(world);
    

    this.isEffect = isEffect;
    this.cworld = world.getWorld();
    

    setPositionRotation(d0, d1, d2, 0.0F, 0.0F);
    this.lifeTicks = 2;
    this.a = this.random.nextLong();
    this.c = (this.random.nextInt(3) + 1);
    

    if ((!isEffect) && (world.difficulty >= 2) && (world.areChunksLoaded(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2), 10))) {
      int i = MathHelper.floor(d0);
      int j = MathHelper.floor(d1);
      int k = MathHelper.floor(d2);
      
      if ((world.getTypeId(i, j, k) == 0) && (Block.FIRE.canPlace(world, i, j, k)))
      {
        BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(i, j, k), org.bukkit.event.block.BlockIgniteEvent.IgniteCause.LIGHTNING, null);
        world.getServer().getPluginManager().callEvent(event);
        
        if (!event.isCancelled()) {
          world.setTypeId(i, j, k, Block.FIRE.id);
        }
      }
      

      for (i = 0; i < 4; i++) {
        j = MathHelper.floor(d0) + this.random.nextInt(3) - 1;
        k = MathHelper.floor(d1) + this.random.nextInt(3) - 1;
        int l = MathHelper.floor(d2) + this.random.nextInt(3) - 1;
        
        if ((world.getTypeId(j, k, l) == 0) && (Block.FIRE.canPlace(world, j, k, l)))
        {
          BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(j, k, l), org.bukkit.event.block.BlockIgniteEvent.IgniteCause.LIGHTNING, null);
          world.getServer().getPluginManager().callEvent(event);
          
          if (!event.isCancelled()) {
            world.setTypeId(j, k, l, Block.FIRE.id);
          }
        }
      }
    }
  }
  
  public void F_()
  {
    super.F_();
    if (this.lifeTicks == 2) {
      this.world.makeSound(this.locX, this.locY, this.locZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.random.nextFloat() * 0.2F);
      this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 2.0F, 0.5F + this.random.nextFloat() * 0.2F);
    }
    
    this.lifeTicks -= 1;
    if (this.lifeTicks < 0) {
      if (this.c == 0) {
        die();
      } else if (this.lifeTicks < -this.random.nextInt(10)) {
        this.c -= 1;
        this.lifeTicks = 1;
        this.a = this.random.nextLong();
        
        if ((!this.isEffect) && (this.world.areChunksLoaded(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 10))) {
          int i = MathHelper.floor(this.locX);
          int j = MathHelper.floor(this.locY);
          int k = MathHelper.floor(this.locZ);
          
          if ((this.world.getTypeId(i, j, k) == 0) && (Block.FIRE.canPlace(this.world, i, j, k)))
          {
            BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(i, j, k), org.bukkit.event.block.BlockIgniteEvent.IgniteCause.LIGHTNING, null);
            this.world.getServer().getPluginManager().callEvent(event);
            
            if (!event.isCancelled()) {
              this.world.setTypeId(i, j, k, Block.FIRE.id);
            }
          }
        }
      }
    }
    

    if ((this.lifeTicks >= 0) && (!this.isEffect)) {
      double d0 = 3.0D;
      java.util.List list = this.world.getEntities(this, AxisAlignedBB.b(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + 6.0D + d0, this.locZ + d0));
      
      for (int l = 0; l < list.size(); l++) {
        Entity entity = (Entity)list.get(l);
        
        entity.a(this);
      }
      
      this.world.n = 2;
    }
  }
  
  protected void b() {}
  
  protected void a(NBTTagCompound nbttagcompound) {}
  
  protected void b(NBTTagCompound nbttagcompound) {}
}
