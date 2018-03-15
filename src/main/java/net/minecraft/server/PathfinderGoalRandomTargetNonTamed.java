package net.minecraft.server;

public class PathfinderGoalRandomTargetNonTamed
  extends PathfinderGoalNearestAttackableTarget
{
  private EntityTameableAnimal g;
  
  public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal paramEntityTameableAnimal, Class paramClass, float paramFloat, int paramInt, boolean paramBoolean)
  {
    super(paramEntityTameableAnimal, paramClass, paramFloat, paramInt, paramBoolean);
    this.g = paramEntityTameableAnimal;
  }
  
  public boolean a()
  {
    if (this.g.isTamed()) return false;
    return super.a();
  }
}
