package net.minecraft.server;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;

public class EntitySnowman extends EntityGolem
{
  public EntitySnowman(World world)
  {
    super(world);
    this.texture = "/mob/snowman.png";
    b(0.4F, 1.8F);
    al().a(true);
    this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 0.25F, 2, 20));
    this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 0.2F));
    this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
    this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityMonster.class, 16.0F, 0, true));
  }
  
  public boolean c_() {
    return true;
  }
  
  public int getMaxHealth() {
    return 4;
  }
  
  public void e() {
    super.e();
    if (aT())
    {
      EntityDamageEvent event = new EntityDamageEvent(getBukkitEntity(), org.bukkit.event.entity.EntityDamageEvent.DamageCause.DROWNING, 1);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (!event.isCancelled()) {
        event.getEntity().setLastDamageCause(event);
        damageEntity(DamageSource.DROWN, event.getDamage());
      }
    }
    

    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.locZ);
    
    if (this.world.getBiome(i, j).i() > 1.0F)
    {
      EntityDamageEvent event = new EntityDamageEvent(getBukkitEntity(), org.bukkit.event.entity.EntityDamageEvent.DamageCause.MELTING, 1);
      this.world.getServer().getPluginManager().callEvent(event);
      
      if (!event.isCancelled()) {
        event.getEntity().setLastDamageCause(event);
        damageEntity(DamageSource.BURN, event.getDamage());
      }
    }
    

    for (i = 0; i < 4; i++) {
      j = MathHelper.floor(this.locX + (i % 2 * 2 - 1) * 0.25F);
      int k = MathHelper.floor(this.locY);
      int l = MathHelper.floor(this.locZ + (i / 2 % 2 * 2 - 1) * 0.25F);
      
      if ((this.world.getTypeId(j, k, l) == 0) && (this.world.getBiome(j, l).i() < 0.8F) && (Block.SNOW.canPlace(this.world, j, k, l)))
      {
        BlockState blockState = this.world.getWorld().getBlockAt(j, k, l).getState();
        blockState.setTypeId(Block.SNOW.id);
        
        EntityBlockFormEvent event = new EntityBlockFormEvent(getBukkitEntity(), blockState.getBlock(), blockState);
        this.world.getServer().getPluginManager().callEvent(event);
        
        if (!event.isCancelled()) {
          blockState.update(true);
        }
      }
    }
  }
  
  public void b(NBTTagCompound nbttagcompound)
  {
    super.b(nbttagcompound);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
  }
  
  protected int getLootId() {
    return Item.SNOW_BALL.id;
  }
  
  protected void dropDeathLoot(boolean flag, int i)
  {
    java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList();
    int j = this.random.nextInt(16);
    
    if (j > 0) {
      loot.add(new org.bukkit.inventory.ItemStack(Item.SNOW_BALL.id, j));
    }
    
    org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
  }
}
