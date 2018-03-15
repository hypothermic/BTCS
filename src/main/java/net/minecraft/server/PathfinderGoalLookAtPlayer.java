package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalLookAtPlayer
  extends PathfinderGoal
{
  private EntityLiving a;
  private Entity b;
  private float c;
  private int d;
  private float e;
  private Class f;
  
  public PathfinderGoalLookAtPlayer(EntityLiving paramEntityLiving, Class paramClass, float paramFloat)
  {
    this.a = paramEntityLiving;
    this.f = paramClass;
    this.c = paramFloat;
    this.e = 0.02F;
    a(2);
  }
  
  public PathfinderGoalLookAtPlayer(EntityLiving paramEntityLiving, Class paramClass, float paramFloat1, float paramFloat2) {
    this.a = paramEntityLiving;
    this.f = paramClass;
    this.c = paramFloat1;
    this.e = paramFloat2;
    a(2);
  }
  
  public boolean a()
  {
    if (this.a.an().nextFloat() >= this.e) return false;
    if (this.f == EntityHuman.class) this.b = this.a.world.findNearbyPlayer(this.a, this.c); else
      this.b = this.a.world.a(this.f, this.a.boundingBox.grow(this.c, 3.0D, this.c), this.a);
    return this.b != null;
  }
  
  public boolean b() {
    if (!this.b.isAlive()) return false;
    if (this.a.j(this.b) > this.c * this.c) return false;
    return this.d > 0;
  }
  
  public void c() {
    this.d = (40 + this.a.an().nextInt(40));
  }
  
  public void d() {
    this.b = null;
  }
  
  public void e() {
    this.a.getControllerLook().a(this.b.locX, this.b.locY + this.b.getHeadHeight(), this.b.locZ, 10.0F, this.a.D());
    this.d -= 1;
  }
}
