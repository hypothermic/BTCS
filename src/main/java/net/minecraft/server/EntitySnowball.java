package net.minecraft.server;





public class EntitySnowball
  extends EntityProjectile
{
  public EntitySnowball(World paramWorld)
  {
    super(paramWorld);
  }
  
  public EntitySnowball(World paramWorld, EntityLiving paramEntityLiving) {
    super(paramWorld, paramEntityLiving);
  }
  
  public EntitySnowball(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
    super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
  }
  
  protected void a(MovingObjectPosition paramMovingObjectPosition)
  {
    if (paramMovingObjectPosition.entity != null) {
      i = 0;
      if ((paramMovingObjectPosition.entity instanceof EntityBlaze)) {
        i = 3;
      }
      if (!paramMovingObjectPosition.entity.damageEntity(DamageSource.projectile(this, this.shooter), i)) {}
    }
    

    for (int i = 0; i < 8; i++)
      this.world.a("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
    if (!this.world.isStatic) {
      die();
    }
  }
}
