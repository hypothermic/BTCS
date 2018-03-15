package net.minecraft.server;


public class PathfinderGoalInteract
  extends PathfinderGoalLookAtPlayer
{
  public PathfinderGoalInteract(EntityLiving paramEntityLiving, Class paramClass, float paramFloat)
  {
    super(paramEntityLiving, paramClass, paramFloat);
    a(3);
  }
  
  public PathfinderGoalInteract(EntityLiving paramEntityLiving, Class paramClass, float paramFloat1, float paramFloat2) {
    super(paramEntityLiving, paramClass, paramFloat1, paramFloat2);
    a(3);
  }
}
