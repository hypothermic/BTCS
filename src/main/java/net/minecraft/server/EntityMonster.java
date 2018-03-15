package net.minecraft.server;

import org.bukkit.event.entity.EntityTargetEvent;

public abstract class EntityMonster extends EntityCreature implements IMonster
{
  protected int damage = 2;
  
  public EntityMonster(World world) {
    super(world);
    this.aA = 5;
  }
  
  public void e() {
    float f = b(1.0F);
    
    if (f > 0.5F) {
      this.aV += 2;
    }
    
    super.e();
  }
  
  public void F_() {
    super.F_();
    if ((!this.world.isStatic) && (this.world.difficulty == 0)) {
      die();
    }
  }
  
  protected Entity findTarget() {
    EntityHuman entityhuman = this.world.findNearbyVulnerablePlayer(this, 16.0D);
    
    return (entityhuman != null) && (h(entityhuman)) ? entityhuman : null;
  }
  
  public boolean damageEntity(DamageSource damagesource, int i) {
    if (super.damageEntity(damagesource, i)) {
      Entity entity = damagesource.getEntity();
      
      if ((this.passenger != entity) && (this.vehicle != entity)) {
        if (entity != this)
        {
          if ((entity != this.target) && (((this instanceof EntityBlaze)) || ((this instanceof EntityEnderman)) || ((this instanceof EntitySpider)) || ((this instanceof EntityGiantZombie)) || ((this instanceof EntitySilverfish)))) {
            EntityTargetEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetEvent(this, entity, org.bukkit.event.entity.EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
            
            if (!event.isCancelled()) {
              if (event.getTarget() == null) {
                this.target = null;
              } else {
                this.target = ((org.bukkit.craftbukkit.entity.CraftEntity)event.getTarget()).getHandle();
              }
            }
          } else {
            this.target = entity;
          }
        }
        

        return true;
      }
      return true;
    }
    
    return false;
  }
  
  public boolean a(Entity entity)
  {
    int i = this.damage;
    
    if (hasEffect(MobEffectList.INCREASE_DAMAGE)) {
      i += (3 << getEffect(MobEffectList.INCREASE_DAMAGE).getAmplifier());
    }
    
    if (hasEffect(MobEffectList.WEAKNESS)) {
      i -= (2 << getEffect(MobEffectList.WEAKNESS).getAmplifier());
    }
    
    return entity.damageEntity(DamageSource.mobAttack(this), i);
  }
  
  protected void a(Entity entity, float f) {
    if ((this.attackTicks <= 0) && (f < 2.0F) && (entity.boundingBox.e > this.boundingBox.b) && (entity.boundingBox.b < this.boundingBox.e)) {
      this.attackTicks = 20;
      a(entity);
    }
  }
  
  public float a(int i, int j, int k) {
    return 0.5F - this.world.p(i, j, k);
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
  }
  
  protected boolean C() {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.boundingBox.b);
    int k = MathHelper.floor(this.locZ);
    
    if (this.world.a(EnumSkyBlock.SKY, i, j, k, false) > this.random.nextInt(32)) { // BTCS: added 4th arg
      return false;
    }
    int l = this.world.getLightLevel(i, j, k);
    
    if (this.world.w()) {
      int i1 = this.world.f;
      
      this.world.f = 10;
      l = this.world.getLightLevel(i, j, k);
      this.world.f = i1;
    }
    
    return l <= this.random.nextInt(8);
  }
  
  public boolean canSpawn()
  {
    return (C()) && (super.canSpawn());
  }
}
