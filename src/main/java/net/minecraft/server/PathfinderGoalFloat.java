package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalFloat extends PathfinderGoal
{
  private EntityLiving a;
  
  public PathfinderGoalFloat(EntityLiving paramEntityLiving)
  {
    this.a = paramEntityLiving;
    a(4);
    paramEntityLiving.al().e(true);
  }
  
  public boolean a()
  {
    return (this.a.aU()) || (this.a.aV());
  }
  
  public void e() {
    if (this.a.an().nextFloat() < 0.8F) this.a.getControllerJump().a();
  }
}
