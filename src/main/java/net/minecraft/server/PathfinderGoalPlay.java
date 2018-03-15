package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;




public class PathfinderGoalPlay
  extends PathfinderGoal
{
  private EntityVillager a;
  private EntityLiving b;
  private float c;
  private int d;
  
  public PathfinderGoalPlay(EntityVillager paramEntityVillager, float paramFloat)
  {
    this.a = paramEntityVillager;
    this.c = paramFloat;
    a(1);
  }
  
  public boolean a() {
    if (this.a.getAge() >= 0) return false;
    if (this.a.an().nextInt(400) != 0) { return false;
    }
    List localList = this.a.world.a(EntityVillager.class, this.a.boundingBox.grow(6.0D, 3.0D, 6.0D));
    double d1 = Double.MAX_VALUE;
    for (Object localObject = localList.iterator(); ((Iterator)localObject).hasNext();) { Entity localEntity = (Entity)((Iterator)localObject).next();
      if (localEntity != this.a) {
        EntityVillager localEntityVillager = (EntityVillager)localEntity;
        if ((!localEntityVillager.C()) && 
          (localEntityVillager.getAge() < 0)) {
          double d2 = localEntityVillager.j(this.a);
          if (d2 <= d1) {
            d1 = d2;
            this.b = localEntityVillager;
          }
        } } }
    if (this.b == null) {
      Vec3D localObject = RandomPositionGenerator.a(this.a, 16, 3); // BTCS: added decl 'Vec3D'
      if (localObject == null) return false;
    }
    return true;
  }
  
  public boolean b() {
    return this.d > 0;
  }
  
  public void c() {
    if (this.b != null) this.a.b(true);
    this.d = 1000;
  }
  
  public void d() {
    this.a.b(false);
    this.b = null;
  }
  
  public void e() {
    this.d -= 1;
    if (this.b != null) {
      if (this.a.j(this.b) > 4.0D) this.a.al().a(this.b, this.c);
    }
    else if (this.a.al().e()) {
      Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 3);
      if (localVec3D == null) return;
      this.a.al().a(localVec3D.a, localVec3D.b, localVec3D.c, this.c);
    }
  }
}
