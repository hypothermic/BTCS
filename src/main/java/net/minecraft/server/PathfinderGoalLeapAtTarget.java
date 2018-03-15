package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalLeapAtTarget extends PathfinderGoal
{
  EntityLiving a;
  EntityLiving b;
  float c;
  
  public PathfinderGoalLeapAtTarget(EntityLiving paramEntityLiving, float paramFloat)
  {
    this.a = paramEntityLiving;
    this.c = paramFloat;
    a(5);
  }
  
  public boolean a()
  {
    this.b = this.a.at();
    if (this.b == null) return false;
    double d = this.a.j(this.b);
    if ((d < 4.0D) || (d > 16.0D)) return false;
    if (!this.a.onGround) return false;
    if (this.a.an().nextInt(5) != 0) return false;
    return true;
  }
  
  public boolean b() {
    return !this.a.onGround;
  }
  
  public void c()
  {
    double d1 = this.b.locX - this.a.locX;
    double d2 = this.b.locZ - this.a.locZ;
    float f = MathHelper.sqrt(d1 * d1 + d2 * d2);
    this.a.motX += d1 / f * 0.5D * 0.800000011920929D + this.a.motX * 0.20000000298023224D;
    this.a.motZ += d2 / f * 0.5D * 0.800000011920929D + this.a.motZ * 0.20000000298023224D;
    this.a.motY = this.c;
  }
}
