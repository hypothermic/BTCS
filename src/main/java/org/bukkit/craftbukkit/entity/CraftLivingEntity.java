package org.bukkit.craftbukkit.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityComplex;
import net.minecraft.server.EntityEgg;
import net.minecraft.server.EntityEnderPearl;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntitySenses;
import net.minecraft.server.EntitySmallFireball;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.MobEffect;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet42RemoveMobEffect;
import net.minecraft.server.World;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CraftLivingEntity extends CraftEntity implements LivingEntity
{
  public CraftLivingEntity(CraftServer server, EntityLiving entity)
  {
    super(server, entity);
  }
  
  public int getHealth() {
    return getHandle().getHealth();
  }
  
  public void setHealth(int health) {
    if ((health < 0) || (health > getMaxHealth())) {
      throw new IllegalArgumentException("Health must be between 0 and " + getMaxHealth());
    }
    
    if (((this.entity instanceof EntityPlayer)) && (health == 0)) {
      ((EntityPlayer)this.entity).die(DamageSource.GENERIC);
    }
    
    getHandle().setHealth(health);
  }
  
  public int getMaxHealth() {
    return getHandle().getMaxHealth();
  }
  
  @Deprecated
  public Egg throwEgg() {
    return (Egg)launchProjectile(Egg.class);
  }
  
  @Deprecated
  public Snowball throwSnowball() {
    return (Snowball)launchProjectile(Snowball.class);
  }
  
  public double getEyeHeight() {
    return 1.0D;
  }
  
  public double getEyeHeight(boolean ignoreSneaking) {
    return getEyeHeight();
  }
  
  private List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance, int maxLength) {
    if (maxDistance > 120) {
      maxDistance = 120;
    }
    ArrayList<Block> blocks = new ArrayList();
    Iterator<Block> itr = new org.bukkit.util.BlockIterator(this, maxDistance);
    while (itr.hasNext()) {
      Block block = (Block)itr.next();
      blocks.add(block);
      if ((maxLength != 0) && (blocks.size() > maxLength)) {
        blocks.remove(0);
      }
      int id = block.getTypeId();
      if (transparent == null ? 
        id != 0 : 
        


        !transparent.contains(Byte.valueOf((byte)id))) {
        break;
      }
    }
    
    return blocks;
  }
  
  public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
    return getLineOfSight(transparent, maxDistance, 0);
  }
  
  public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
    List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);
    return (Block)blocks.get(0);
  }
  
  public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
    return getLineOfSight(transparent, maxDistance, 2);
  }
  
  @Deprecated
  public Arrow shootArrow() {
    return (Arrow)launchProjectile(Arrow.class);
  }
  
  public int getRemainingAir() {
    return getHandle().getAirTicks();
  }
  
  public void setRemainingAir(int ticks) {
    getHandle().setAirTicks(ticks);
  }
  
  public int getMaximumAir() {
    return getHandle().maxAirTicks;
  }
  
  public void setMaximumAir(int ticks) {
    getHandle().maxAirTicks = ticks;
  }
  
  public void damage(int amount) {
    damage(amount, null);
  }
  
  public void damage(int amount, org.bukkit.entity.Entity source) {
    DamageSource reason = DamageSource.GENERIC;
    
    if ((source instanceof HumanEntity)) {
      reason = DamageSource.playerAttack(((CraftHumanEntity)source).getHandle());
    } else if ((source instanceof LivingEntity)) {
      reason = DamageSource.mobAttack(((CraftLivingEntity)source).getHandle());
    }
    
    if ((this.entity instanceof EntityComplex)) {
      ((EntityComplex)this.entity).dealDamage(reason, amount);
    } else {
      this.entity.damageEntity(reason, amount);
    }
  }
  
  public Location getEyeLocation() {
    Location loc = getLocation();
    loc.setY(loc.getY() + getEyeHeight());
    return loc;
  }
  
  public int getMaximumNoDamageTicks() {
    return getHandle().maxNoDamageTicks;
  }
  
  public void setMaximumNoDamageTicks(int ticks) {
    getHandle().maxNoDamageTicks = ticks;
  }
  
  public int getLastDamage() {
    return getHandle().lastDamage;
  }
  
  public void setLastDamage(int damage) {
    getHandle().lastDamage = damage;
  }
  
  public int getNoDamageTicks() {
    return getHandle().noDamageTicks;
  }
  
  public void setNoDamageTicks(int ticks) {
    getHandle().noDamageTicks = ticks;
  }
  
  public EntityLiving getHandle()
  {
    return (EntityLiving)this.entity;
  }
  
  public void setHandle(EntityLiving entity) {
    super.setHandle(entity);
  }
  
  public String toString()
  {
    return "CraftLivingEntity{id=" + getEntityId() + '}';
  }
  
  public Player getKiller() {
    return getHandle().killer == null ? null : (Player)getHandle().killer.getBukkitEntity();
  }
  
  public boolean addPotionEffect(PotionEffect effect) {
    return addPotionEffect(effect, false);
  }
  
  public boolean addPotionEffect(PotionEffect effect, boolean force) {
    if (hasPotionEffect(effect.getType())) {
      if (!force) {
        return false;
      }
      removePotionEffect(effect.getType());
    }
    getHandle().addEffect(new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier()));
    return true;
  }
  
  public boolean addPotionEffects(Collection<PotionEffect> effects) {
    boolean success = true;
    for (PotionEffect effect : effects) {
      success &= addPotionEffect(effect);
    }
    return success;
  }
  
  public boolean hasPotionEffect(PotionEffectType type) {
    return getHandle().hasEffect(net.minecraft.server.MobEffectList.byId[type.getId()]);
  }
  
  public void removePotionEffect(PotionEffectType type) {
    getHandle().effects.remove(Integer.valueOf(type.getId()));
    getHandle().e = true;
    if ((getHandle() instanceof EntityPlayer)) {
      if (((EntityPlayer)getHandle()).netServerHandler == null) return;
      ((EntityPlayer)getHandle()).netServerHandler.sendPacket(new Packet42RemoveMobEffect(getHandle().id, new MobEffect(type.getId(), 0, 0)));
    }
  }
  
  public Collection<PotionEffect> getActivePotionEffects() {
    List<PotionEffect> effects = new ArrayList();
    for (Object raw : getHandle().effects.values())
      if ((raw instanceof MobEffect))
      {
        MobEffect handle = (MobEffect)raw;
        effects.add(new PotionEffect(PotionEffectType.getById(handle.getEffectId()), handle.getDuration(), handle.getAmplifier()));
      }
    return effects;
  }
  
  public <T extends Projectile> T launchProjectile(Class<? extends T> projectile)
  {
    World world = ((CraftWorld)getWorld()).getHandle();
    net.minecraft.server.Entity launch = null;
    
    if (Snowball.class.isAssignableFrom(projectile)) {
      launch = new EntitySnowball(world, getHandle());
    } else if (Egg.class.isAssignableFrom(projectile)) {
      launch = new EntityEgg(world, getHandle());
    } else if (EnderPearl.class.isAssignableFrom(projectile)) {
      launch = new EntityEnderPearl(world, getHandle());
    } else if (Arrow.class.isAssignableFrom(projectile)) {
      launch = new EntityArrow(world, getHandle(), 1.0F);
    } else if (Fireball.class.isAssignableFrom(projectile)) {
      if (SmallFireball.class.isAssignableFrom(projectile)) {
        launch = new EntitySmallFireball(world);
      } else {
        launch = new EntityFireball(world);
      }
      
      Location location = getEyeLocation();
      Vector direction = location.getDirection().multiply(10);
      
      launch.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
      ((EntityFireball)launch).setDirection(direction.getX(), direction.getY(), direction.getZ());
    }
    
    Validate.notNull(launch, "Projectile not supported");
    
    world.addEntity(launch);
    return (T) launch.getBukkitEntity(); // BTCS: changed cast from (Projectile) to (T)
  }
  
  public EntityType getType() {
    return EntityType.UNKNOWN;
  }
  
  public boolean hasLineOfSight(org.bukkit.entity.Entity other) {
    return getHandle().am().canSee(((CraftEntity)other).getHandle());
  }
}
