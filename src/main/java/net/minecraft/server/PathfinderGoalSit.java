package net.minecraft.server;


public class PathfinderGoalSit
  extends PathfinderGoal
{
  private EntityTameableAnimal a;
  
  private boolean b = false;
  
  public PathfinderGoalSit(EntityTameableAnimal paramEntityTameableAnimal) {
    this.a = paramEntityTameableAnimal;
    a(5);
  }
  
  public boolean a()
  {
    if (!this.a.isTamed()) return false;
    if (this.a.aU()) return false;
    if (!this.a.onGround) { return false;
    }
    EntityLiving localEntityLiving = this.a.getOwner();
    if (localEntityLiving == null) { return true;
    }
    if ((this.a.j(localEntityLiving) < 144.0D) && (localEntityLiving.ao() != null)) { return false;
    }
    return this.b;
  }
  
  public void c() {
    this.a.al().f();
    this.a.setSitting(true);
  }
  
  public void d() {
    this.a.setSitting(false);
  }
  
  public void a(boolean paramBoolean) {
    this.b = paramBoolean;
  }
}
