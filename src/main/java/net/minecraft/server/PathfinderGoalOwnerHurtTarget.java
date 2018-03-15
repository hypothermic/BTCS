package net.minecraft.server;

public class PathfinderGoalOwnerHurtTarget extends PathfinderGoalTarget
{
  EntityTameableAnimal a;
  EntityLiving b;
  
  public PathfinderGoalOwnerHurtTarget(EntityTameableAnimal paramEntityTameableAnimal)
  {
    super(paramEntityTameableAnimal, 32.0F, false);
    this.a = paramEntityTameableAnimal;
    a(1);
  }
  
  public boolean a() {
    if (!this.a.isTamed()) return false;
    EntityLiving localEntityLiving = this.a.getOwner();
    if (localEntityLiving == null) return false;
    this.b = localEntityLiving.ap();
    return a(this.b, false);
  }
  
  public void c() {
    this.c.b(this.b);
    super.c();
  }
}
