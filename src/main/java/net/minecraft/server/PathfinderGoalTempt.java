package net.minecraft.server;

public class PathfinderGoalTempt extends PathfinderGoal
{
  private EntityCreature a;
  private float b;
  private double c;
  private double d;
  private double e;
  private double f;
  private double g;
  private EntityHuman h;
  private int i = 0;
  private boolean j;
  private int k;
  private boolean l;
  private boolean m;
  
  public PathfinderGoalTempt(EntityCreature paramEntityCreature, float paramFloat, int paramInt, boolean paramBoolean) {
    this.a = paramEntityCreature;
    this.b = paramFloat;
    this.k = paramInt;
    this.l = paramBoolean;
    a(3);
  }
  
  public boolean a()
  {
    if (this.i > 0) {
      this.i -= 1;
      return false;
    }
    this.h = this.a.world.findNearbyPlayer(this.a, 10.0D);
    if (this.h == null) return false;
    ItemStack localItemStack = this.h.U();
    if (localItemStack == null) return false;
    if (localItemStack.id != this.k) return false;
    return true;
  }
  
  public boolean b()
  {
    if (this.l) {
      if (this.a.j(this.h) < 36.0D) {
        if (this.h.e(this.c, this.d, this.e) > 0.010000000000000002D) return false;
        if ((Math.abs(this.h.pitch - this.f) > 5.0D) || (Math.abs(this.h.yaw - this.g) > 5.0D)) return false;
      } else {
        this.c = this.h.locX;
        this.d = this.h.locY;
        this.e = this.h.locZ;
      }
      this.f = this.h.pitch;
      this.g = this.h.yaw;
    }
    return a();
  }
  
  public void c() {
    this.c = this.h.locX;
    this.d = this.h.locY;
    this.e = this.h.locZ;
    this.j = true;
    this.m = this.a.al().a();
    this.a.al().a(false);
  }
  
  public void d() {
    this.h = null;
    this.a.al().f();
    this.i = 100;
    this.j = false;
    this.a.al().a(this.m);
  }
  
  public void e() {
    this.a.getControllerLook().a(this.h, 30.0F, this.a.D());
    if (this.a.j(this.h) < 6.25D) this.a.al().f(); else
      this.a.al().a(this.h, this.b);
  }
  
  public boolean f() {
    return this.j;
  }
}
