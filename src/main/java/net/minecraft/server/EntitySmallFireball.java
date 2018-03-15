package net.minecraft.server;

import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.plugin.PluginManager;

public class EntitySmallFireball extends EntityFireball
{
  public EntitySmallFireball(World world)
  {
    super(world);
    b(0.3125F, 0.3125F);
  }
  
  public EntitySmallFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
    super(world, entityliving, d0, d1, d2);
    b(0.3125F, 0.3125F);
  }
  
  public EntitySmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
    super(world, d0, d1, d2, d3, d4, d5);
    b(0.3125F, 0.3125F);
  }
  
  protected void a(MovingObjectPosition movingobjectposition) {
    if (!this.world.isStatic) {
      if (movingobjectposition.entity != null) {
        if ((!movingobjectposition.entity.isFireproof()) && (movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 5)))
        {
          EntityCombustByEntityEvent event = new EntityCombustByEntityEvent((org.bukkit.entity.Projectile)getBukkitEntity(), movingobjectposition.entity.getBukkitEntity(), 5);
          movingobjectposition.entity.world.getServer().getPluginManager().callEvent(event);
          
          if (!event.isCancelled()) {
            movingobjectposition.entity.setOnFire(event.getDuration());
          }
        }
      }
      else {
        int i = movingobjectposition.b;
        int j = movingobjectposition.c;
        int k = movingobjectposition.d;
        
        switch (movingobjectposition.face) {
        case 0: 
          j--;
          break;
        
        case 1: 
          j++;
          break;
        
        case 2: 
          k--;
          break;
        
        case 3: 
          k++;
          break;
        
        case 4: 
          i--;
          break;
        
        case 5: 
          i++;
        }
        
        if (this.world.isEmpty(i, j, k))
        {
          org.bukkit.block.Block block = this.world.getWorld().getBlockAt(i, j, k);
          BlockIgniteEvent event = new BlockIgniteEvent(block, org.bukkit.event.block.BlockIgniteEvent.IgniteCause.FIREBALL, null);
          this.world.getServer().getPluginManager().callEvent(event);
          
          if (!event.isCancelled()) {
            this.world.setTypeId(i, j, k, Block.FIRE.id);
          }
        }
      }
      

      die();
    }
  }
  
  public boolean o_() {
    return false;
  }
  
  public boolean damageEntity(DamageSource damagesource, int i) {
    return false;
  }
}
