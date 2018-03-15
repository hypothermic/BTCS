package net.minecraft.server;

import java.util.List;

public class PathfinderGoalAvoidPlayer
  extends PathfinderGoal
{
  private EntityCreature a;
  private float b;
  private float c;
  private Entity d;
  private float e;
  private PathEntity f;
  private Navigation g;
  private Class h;
  
  public PathfinderGoalAvoidPlayer(EntityCreature paramEntityCreature, Class paramClass, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.a = paramEntityCreature;
    this.h = paramClass;
    this.e = paramFloat1;
    this.b = paramFloat2;
    this.c = paramFloat3;
    this.g = paramEntityCreature.al();
    a(1);
  }
  
  public boolean a()
  {
    if (this.h == EntityHuman.class) {
      if (((this.a instanceof EntityTameableAnimal)) && (((EntityTameableAnimal)this.a).isTamed())) return false;
      this.d = this.a.world.findNearbyPlayer(this.a, this.e);
      if (this.d == null) return false;
    } else {
      List localObject = this.a.world.a(this.h, this.a.boundingBox.grow(this.e, 3.0D, this.e)); // BTCS: added decl 'List'
      if (((List)localObject).size() == 0) return false;
      this.d = ((Entity)((List)localObject).get(0));
    }
    
    if (!this.a.am().canSee(this.d)) { return false;
    }
    Object localObject = RandomPositionGenerator.b(this.a, 16, 7, Vec3D.create(this.d.locX, this.d.locY, this.d.locZ));
    if (localObject == null) return false;
    if (this.d.e(((Vec3D)localObject).a, ((Vec3D)localObject).b, ((Vec3D)localObject).c) < this.d.j(this.a)) return false;
    this.f = this.g.a(((Vec3D)localObject).a, ((Vec3D)localObject).b, ((Vec3D)localObject).c);
    if (this.f == null) return false;
    if (!this.f.a((Vec3D)localObject)) return false;
    return true;
  }
  
  public boolean b()
  {
    return !this.g.e();
  }
  
  public void c()
  {
    this.g.a(this.f, this.b);
  }
  
  public void d() {
    this.d = null;
  }
  
  public void e() {
    if (this.a.j(this.d) < 49.0D) this.a.al().a(this.c); else {
      this.a.al().a(this.b);
    }
  }
}
