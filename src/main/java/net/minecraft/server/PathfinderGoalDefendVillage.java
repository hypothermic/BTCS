package net.minecraft.server;


public class PathfinderGoalDefendVillage
  extends PathfinderGoalTarget
{
  EntityIronGolem a;
  EntityLiving b;
  
  public PathfinderGoalDefendVillage(EntityIronGolem paramEntityIronGolem)
  {
    super(paramEntityIronGolem, 16.0F, false, true);
    this.a = paramEntityIronGolem;
    a(1);
  }
  
  public boolean a() {
    Village localVillage = this.a.l_();
    if (localVillage == null) return false;
    this.b = localVillage.b(this.a);
    return a(this.b, false);
  }
  
  public void c() {
    this.a.b(this.b);
    super.c();
  }
}
