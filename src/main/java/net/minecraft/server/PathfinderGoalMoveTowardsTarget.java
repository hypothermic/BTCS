package net.minecraft.server;

public class PathfinderGoalMoveTowardsTarget
  extends PathfinderGoal
{
  private EntityCreature a;
  private EntityLiving b;
  private double c;
  private double d;
  private double e;
  private float f;
  private float g;
  
  public PathfinderGoalMoveTowardsTarget(EntityCreature paramEntityCreature, float paramFloat1, float paramFloat2)
  {
    this.a = paramEntityCreature;
    this.f = paramFloat1;
    this.g = paramFloat2;
    a(1);
  }
  
  public boolean a() {
    this.b = this.a.at();
    if (this.b == null) return false;
    if (this.b.j(this.a) > this.g * this.g) return false;
    Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 7, Vec3D.create(this.b.locX, this.b.locY, this.b.locZ));
    if (localVec3D == null) return false;
    this.c = localVec3D.a;
    this.d = localVec3D.b;
    this.e = localVec3D.c;
    return true;
  }
  
  public boolean b() {
    return (!this.a.al().e()) && (this.b.isAlive()) && (this.b.j(this.a) < this.g * this.g);
  }
  
  public void d() {
    this.b = null;
  }
  
  public void c() {
    this.a.al().a(this.c, this.d, this.e, this.f);
  }
}
