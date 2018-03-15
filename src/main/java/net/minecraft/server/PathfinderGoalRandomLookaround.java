package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalRandomLookaround extends PathfinderGoal
{
  private EntityLiving a;
  private double b;
  private double c;
  private int d = 0;
  
  public PathfinderGoalRandomLookaround(EntityLiving paramEntityLiving) {
    this.a = paramEntityLiving;
    a(3);
  }
  
  public boolean a()
  {
    return this.a.an().nextFloat() < 0.02F;
  }
  
  public boolean b() {
    return this.d >= 0;
  }
  
  public void c() {
    double d1 = 6.283185307179586D * this.a.an().nextDouble();
    this.b = Math.cos(d1);
    this.c = Math.sin(d1);
    this.d = (20 + this.a.an().nextInt(20));
  }
  
  public void e() {
    this.d -= 1;
    this.a.getControllerLook().a(this.a.locX + this.b, this.a.locY + this.a.getHeadHeight(), this.a.locZ + this.c, 10.0F, this.a.D());
  }
}
