package net.minecraft.server;

import org.bukkit.craftbukkit.event.CraftEventFactory;

public class EntityPig extends EntityAnimal {
  public EntityPig(World world) { super(world);
    this.texture = "/mob/pig.png";
    b(0.9F, 0.9F);
    al().a(true);
    float f = 0.25F;
    
    this.goalSelector.a(0, new PathfinderGoalFloat(this));
    this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
    this.goalSelector.a(2, new PathfinderGoalBreed(this, f));
    this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25F, Item.WHEAT.id, false));
    this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.28F));
    this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, f));
    this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
  }
  
  public boolean c_() {
    return true;
  }
  
  public int getMaxHealth() {
    return 10;
  }
  
  protected void b() {
    super.b();
    this.datawatcher.a(16, Byte.valueOf((byte)0));
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setBoolean("Saddle", hasSaddle());
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    setSaddle(nbttagcompound.getBoolean("Saddle"));
  }
  
  protected String i() {
    return "mob.pig";
  }
  
  protected String j() {
    return "mob.pig";
  }
  
  protected String k() {
    return "mob.pigdeath";
  }
  
  public boolean b(EntityHuman entityhuman) {
    if (super.b(entityhuman))
      return true;
    if ((hasSaddle()) && (!this.world.isStatic) && ((this.passenger == null) || (this.passenger == entityhuman))) {
      entityhuman.mount(this);
      return true;
    }
    return false;
  }
  
  protected int getLootId()
  {
    return isBurning() ? Item.GRILLED_PORK.id : Item.PORK.id;
  }
  
  public boolean hasSaddle() {
    return (this.datawatcher.getByte(16) & 0x1) != 0;
  }
  
  public void setSaddle(boolean flag) {
    if (flag) {
      this.datawatcher.watch(16, Byte.valueOf((byte)1));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)0));
    }
  }
  
  public void a(EntityWeatherLighting entityweatherlighting) {
    if (!this.world.isStatic) {
      EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
      

      if (CraftEventFactory.callPigZapEvent(this, entityweatherlighting, entitypigzombie).isCancelled()) {
        return;
      }
      

      entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      
      this.world.addEntity(entitypigzombie, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.LIGHTNING);
      die();
    }
  }
  
  protected void a(float f) {
    super.a(f);
    if ((f > 5.0F) && ((this.passenger instanceof EntityHuman))) {
      ((EntityHuman)this.passenger).a(AchievementList.u);
    }
  }
  
  public EntityAnimal createChild(EntityAnimal entityanimal) {
    return new EntityPig(this.world);
  }
}
