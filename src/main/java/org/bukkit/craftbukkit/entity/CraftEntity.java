package org.bukkit.craftbukkit.entity;

import java.util.List;
import net.minecraft.server.EntityComplexPart;
import net.minecraft.server.EntityEnderDragon;
import net.minecraft.server.EntityFishingHook;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.EntityWolf;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.metadata.EntityMetadataStore;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent; // BTCS
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause; // BTCS
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

public class CraftEntity implements org.bukkit.entity.Entity {
  protected final CraftServer server;
  protected net.minecraft.server.Entity entity;
  private EntityDamageEvent lastDamageEvent;
  
  public CraftEntity(CraftServer server, net.minecraft.server.Entity entity)
  {
    this.server = server;
    this.entity = entity;
  }
  


  public static CraftEntity getEntity(CraftServer server, net.minecraft.server.Entity entity)
  {
    if ((entity instanceof net.minecraft.server.EntityLiving))
    {
      if ((entity instanceof net.minecraft.server.EntityHuman)) {
        if ((entity instanceof net.minecraft.server.EntityPlayer)) return new CraftPlayer(server, (net.minecraft.server.EntityPlayer)entity);
        return new CraftHumanEntity(server, (net.minecraft.server.EntityHuman)entity);
      }
      if ((entity instanceof net.minecraft.server.EntityCreature))
      {
        if ((entity instanceof net.minecraft.server.EntityAnimal)) {
          if ((entity instanceof net.minecraft.server.EntityChicken)) return new CraftChicken(server, (net.minecraft.server.EntityChicken)entity);
          if ((entity instanceof net.minecraft.server.EntityCow)) {
            if ((entity instanceof net.minecraft.server.EntityMushroomCow)) return new CraftMushroomCow(server, (net.minecraft.server.EntityMushroomCow)entity);
            return new CraftCow(server, (net.minecraft.server.EntityCow)entity);
          }
          if ((entity instanceof net.minecraft.server.EntityPig)) return new CraftPig(server, (net.minecraft.server.EntityPig)entity);
          if ((entity instanceof net.minecraft.server.EntityTameableAnimal)) {
            if ((entity instanceof EntityWolf)) return new CraftWolf(server, (EntityWolf)entity);
            if ((entity instanceof net.minecraft.server.EntityOcelot)) return new CraftOcelot(server, (net.minecraft.server.EntityOcelot)entity);
          }
          else if ((entity instanceof net.minecraft.server.EntitySheep)) { return new CraftSheep(server, (net.minecraft.server.EntitySheep)entity); }
          return new CraftAnimals(server, (net.minecraft.server.EntityAnimal)entity);
        }
        
        if ((entity instanceof net.minecraft.server.EntityMonster)) {
          if ((entity instanceof net.minecraft.server.EntityZombie)) {
            if ((entity instanceof net.minecraft.server.EntityPigZombie)) return new CraftPigZombie(server, (net.minecraft.server.EntityPigZombie)entity);
            return new CraftZombie(server, (net.minecraft.server.EntityZombie)entity);
          }
          if ((entity instanceof net.minecraft.server.EntityCreeper)) return new CraftCreeper(server, (net.minecraft.server.EntityCreeper)entity);
          if ((entity instanceof net.minecraft.server.EntityEnderman)) return new CraftEnderman(server, (net.minecraft.server.EntityEnderman)entity);
          if ((entity instanceof net.minecraft.server.EntitySilverfish)) return new CraftSilverfish(server, (net.minecraft.server.EntitySilverfish)entity);
          if ((entity instanceof net.minecraft.server.EntityGiantZombie)) return new CraftGiant(server, (net.minecraft.server.EntityGiantZombie)entity);
          if ((entity instanceof net.minecraft.server.EntitySkeleton)) return new CraftSkeleton(server, (net.minecraft.server.EntitySkeleton)entity);
          if ((entity instanceof net.minecraft.server.EntityBlaze)) return new CraftBlaze(server, (net.minecraft.server.EntityBlaze)entity);
          if ((entity instanceof net.minecraft.server.EntitySpider)) {
            if ((entity instanceof net.minecraft.server.EntityCaveSpider)) return new CraftCaveSpider(server, (net.minecraft.server.EntityCaveSpider)entity);
            return new CraftSpider(server, (net.minecraft.server.EntitySpider)entity);
          }
          
          return new CraftMonster(server, (net.minecraft.server.EntityMonster)entity);
        }
        
        if ((entity instanceof net.minecraft.server.EntityWaterAnimal)) {
          if ((entity instanceof net.minecraft.server.EntitySquid)) return new CraftSquid(server, (net.minecraft.server.EntitySquid)entity);
          return new CraftWaterMob(server, (net.minecraft.server.EntityWaterAnimal)entity);
        }
        if ((entity instanceof net.minecraft.server.EntityGolem)) {
          if ((entity instanceof net.minecraft.server.EntitySnowman)) return new CraftSnowman(server, (net.minecraft.server.EntitySnowman)entity);
          if ((entity instanceof net.minecraft.server.EntityIronGolem)) return new CraftIronGolem(server, (net.minecraft.server.EntityIronGolem)entity);
        }
        else if ((entity instanceof net.minecraft.server.EntityVillager)) { return new CraftVillager(server, (net.minecraft.server.EntityVillager)entity); }
        return new CraftCreature(server, (net.minecraft.server.EntityCreature)entity);
      }
      
      if ((entity instanceof net.minecraft.server.EntitySlime)) {
        if ((entity instanceof net.minecraft.server.EntityMagmaCube)) return new CraftMagmaCube(server, (net.minecraft.server.EntityMagmaCube)entity);
        return new CraftSlime(server, (net.minecraft.server.EntitySlime)entity);
      }
      
      if ((entity instanceof net.minecraft.server.EntityFlying)) {
        if ((entity instanceof net.minecraft.server.EntityGhast)) return new CraftGhast(server, (net.minecraft.server.EntityGhast)entity);
        return new CraftFlying(server, (net.minecraft.server.EntityFlying)entity);
      }
      if (((entity instanceof net.minecraft.server.EntityComplex)) && 
        ((entity instanceof EntityEnderDragon))) { return new CraftEnderDragon(server, (EntityEnderDragon)entity);
      }
      return new CraftLivingEntity(server, (net.minecraft.server.EntityLiving)entity);
    }
    if ((entity instanceof EntityComplexPart)) {
      EntityComplexPart part = (EntityComplexPart)entity;
      if ((part.owner instanceof EntityEnderDragon)) return new CraftEnderDragonPart(server, (EntityComplexPart)entity);
      return new CraftComplexPart(server, (EntityComplexPart)entity);
    }
    if ((entity instanceof net.minecraft.server.EntityExperienceOrb)) return new CraftExperienceOrb(server, (net.minecraft.server.EntityExperienceOrb)entity);
    if ((entity instanceof net.minecraft.server.EntityArrow)) return new CraftArrow(server, (net.minecraft.server.EntityArrow)entity);
    if ((entity instanceof net.minecraft.server.EntityBoat)) return new CraftBoat(server, (net.minecraft.server.EntityBoat)entity);
    if ((entity instanceof net.minecraft.server.EntityProjectile)) {
      if ((entity instanceof net.minecraft.server.EntityEgg)) return new CraftEgg(server, (net.minecraft.server.EntityEgg)entity);
      if ((entity instanceof EntitySnowball)) return new CraftSnowball(server, (EntitySnowball)entity);
      if ((entity instanceof net.minecraft.server.EntityPotion)) return new CraftThrownPotion(server, (net.minecraft.server.EntityPotion)entity);
      if ((entity instanceof net.minecraft.server.EntityEnderPearl)) return new CraftEnderPearl(server, (net.minecraft.server.EntityEnderPearl)entity);
      if ((entity instanceof net.minecraft.server.EntityThrownExpBottle)) return new CraftThrownExpBottle(server, (net.minecraft.server.EntityThrownExpBottle)entity);
    } else {
      if ((entity instanceof net.minecraft.server.EntityFallingBlock)) return new CraftFallingSand(server, (net.minecraft.server.EntityFallingBlock)entity);
      if ((entity instanceof net.minecraft.server.EntityFireball)) {
        if ((entity instanceof net.minecraft.server.EntitySmallFireball)) return new CraftSmallFireball(server, (net.minecraft.server.EntitySmallFireball)entity);
        return new CraftFireball(server, (net.minecraft.server.EntityFireball)entity);
      }
      if ((entity instanceof net.minecraft.server.EntityEnderSignal)) return new CraftEnderSignal(server, (net.minecraft.server.EntityEnderSignal)entity);
      if ((entity instanceof net.minecraft.server.EntityEnderCrystal)) return new CraftEnderCrystal(server, (net.minecraft.server.EntityEnderCrystal)entity);
      if ((entity instanceof EntityFishingHook)) return new CraftFish(server, (EntityFishingHook)entity);
      if ((entity instanceof net.minecraft.server.EntityItem)) return new CraftItem(server, (net.minecraft.server.EntityItem)entity);
      if ((entity instanceof net.minecraft.server.EntityWeather)) {
        if ((entity instanceof net.minecraft.server.EntityWeatherLighting)) return new CraftLightningStrike(server, (net.minecraft.server.EntityWeatherLighting)entity);
        return new CraftWeather(server, (net.minecraft.server.EntityWeather)entity);
      }
      if ((entity instanceof EntityMinecart)) {
        EntityMinecart mc = (EntityMinecart)entity;
        if (mc.type == CraftMinecart.Type.StorageMinecart.getId()) return new CraftStorageMinecart(server, mc);
        if (mc.type == CraftMinecart.Type.PoweredMinecart.getId()) return new CraftPoweredMinecart(server, mc);
        return new CraftMinecart(server, mc);
      }
      if ((entity instanceof net.minecraft.server.EntityPainting)) return new CraftPainting(server, (net.minecraft.server.EntityPainting)entity);
      if ((entity instanceof net.minecraft.server.EntityTNTPrimed)) { return new CraftTNTPrimed(server, (net.minecraft.server.EntityTNTPrimed)entity);
      }
    }
    return new CraftEntity(server, entity);
  }
  
  public Location getLocation() {
    return new Location(getWorld(), this.entity.locX, this.entity.locY, this.entity.locZ, this.entity.yaw, this.entity.pitch);
  }
  
  public Vector getVelocity() {
    return new Vector(this.entity.motX, this.entity.motY, this.entity.motZ);
  }
  
  public void setVelocity(Vector vel) {
    this.entity.motX = vel.getX();
    this.entity.motY = vel.getY();
    this.entity.motZ = vel.getZ();
    this.entity.velocityChanged = true;
  }
  
  public org.bukkit.World getWorld() {
    return ((net.minecraft.server.WorldServer)this.entity.world).getWorld();
  }
  
  public boolean teleport(Location location) {
    return teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
  }
  
  public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
	    this.entity.world = ((org.bukkit.craftbukkit.CraftWorld)location.getWorld()).getHandle();
	    this.entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    
	    return true;
	  }
	  
	  public boolean teleport(Entity destination) {
	    return teleport(destination.getLocation());
	  }
	  
	  public boolean teleport(Entity destination, PlayerTeleportEvent.TeleportCause cause) {
	    return teleport(destination.getLocation(), cause);
	  }
  
  public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z)
  {
    List<net.minecraft.server.Entity> notchEntityList = this.entity.world.getEntities(this.entity, this.entity.boundingBox.grow(x, y, z));
    List<org.bukkit.entity.Entity> bukkitEntityList = new java.util.ArrayList(notchEntityList.size());
    
    for (net.minecraft.server.Entity e : notchEntityList) {
      bukkitEntityList.add(e.getBukkitEntity());
    }
    return bukkitEntityList;
  }
  
  public int getEntityId() {
    return this.entity.id;
  }
  
  public int getFireTicks() {
    return this.entity.fireTicks;
  }
  
  public int getMaxFireTicks() {
    return this.entity.maxFireTicks;
  }
  
  public void setFireTicks(int ticks) {
    this.entity.fireTicks = ticks;
  }
  
  public void remove() {
    this.entity.dead = true;
  }
  
  public boolean isDead() {
    return !this.entity.isAlive();
  }
  
  public boolean isValid() {
    return (this.entity.isAlive()) && (this.entity.valid);
  }
  
  public org.bukkit.Server getServer() {
    return this.server;
  }
  
  public Vector getMomentum() {
    return getVelocity();
  }
  
  public void setMomentum(Vector value) {
    setVelocity(value);
  }
  
  public org.bukkit.entity.Entity getPassenger() {
    return isEmpty() ? null : (CraftEntity)getHandle().passenger.getBukkitEntity();
  }
  
  public boolean setPassenger(org.bukkit.entity.Entity passenger) {
    if ((passenger instanceof CraftEntity)) {
      ((CraftEntity)passenger).getHandle().setPassengerOf(getHandle());
      return true;
    }
    return false;
  }
  
  public boolean isEmpty()
  {
    return getHandle().passenger == null;
  }
  
  public boolean eject() {
    if (getHandle().passenger == null) {
      return false;
    }
    
    getHandle().passenger.setPassengerOf(null);
    return true;
  }
  
  public float getFallDistance() {
    return getHandle().fallDistance;
  }
  
  public void setFallDistance(float distance) {
    getHandle().fallDistance = distance;
  }
  
  public void setLastDamageCause(EntityDamageEvent event) {
    this.lastDamageEvent = event;
  }
  
  public EntityDamageEvent getLastDamageCause() {
    return this.lastDamageEvent;
  }
  
  public java.util.UUID getUniqueId() {
    return getHandle().uniqueId;
  }
  
  public int getTicksLived() {
    return getHandle().ticksLived;
  }
  
  public void setTicksLived(int value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Age must be at least 1 tick");
    }
    getHandle().ticksLived = value;
  }
  
  public net.minecraft.server.Entity getHandle() {
    return this.entity;
  }
  
  public void playEffect(EntityEffect type) {
    getHandle().world.broadcastEntityEffect(getHandle(), type.getData());
  }
  
  public void setHandle(net.minecraft.server.Entity entity) {
    this.entity = entity;
  }
  
  public String toString()
  {
    return "CraftEntity{id=" + getEntityId() + '}';
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CraftEntity other = (CraftEntity)obj;
    return getEntityId() == other.getEntityId();
  }
  
  public int hashCode()
  {
    int hash = 7;
    hash = 29 * hash + getEntityId();
    return hash;
  }
  
  public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
    this.server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
  }
  
  public List<MetadataValue> getMetadata(String metadataKey) {
    return this.server.getEntityMetadata().getMetadata(this, metadataKey);
  }
  
  public boolean hasMetadata(String metadataKey) {
    return this.server.getEntityMetadata().hasMetadata(this, metadataKey);
  }
  
  public void removeMetadata(String metadataKey, org.bukkit.plugin.Plugin owningPlugin) {
    this.server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
  }
  
  public boolean isInsideVehicle() {
    return getHandle().vehicle != null;
  }
  
  public boolean leaveVehicle() {
    if (getHandle().vehicle == null) {
      return false;
    }
    
    getHandle().setPassengerOf(null);
    return true;
  }
  
  public org.bukkit.entity.Entity getVehicle() {
    if (getHandle().vehicle == null) {
      return null;
    }
    
    return getHandle().vehicle.getBukkitEntity();
  }
  
  public org.bukkit.entity.EntityType getType()
  {
    return org.bukkit.entity.EntityType.UNKNOWN;
  }
}
