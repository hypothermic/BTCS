package net.minecraft.server;

import java.util.List;

public class PathfinderGoalFollowParent
  extends PathfinderGoal
{
  EntityAnimal a;
  EntityAnimal b;
  float c;
  private int d;
  
  public PathfinderGoalFollowParent(EntityAnimal paramEntityAnimal, float paramFloat)
  {
    this.a = paramEntityAnimal;
    this.c = paramFloat;
  }
  
  public boolean a() {
    if (this.a.getAge() >= 0) { return false;
    }
    List localList = this.a.world.a(this.a.getClass(), this.a.boundingBox.grow(8.0D, 4.0D, 8.0D));
    
    Object localObject = null;
    double d1 = Double.MAX_VALUE;
    for (Entity localEntity : (Entity[]) localList.toArray()) { // BTCS: added cast and .toArray()
      EntityAnimal localEntityAnimal = (EntityAnimal)localEntity;
      if (localEntityAnimal.getAge() >= 0) {
        double d2 = this.a.j(localEntityAnimal);
        if (d2 <= d1) {
          d1 = d2;
          localObject = localEntityAnimal;
        }
      } }
    if (localObject == null) return false;
    if (d1 < 9.0D) return false;
    this.b = ((EntityAnimal)localObject);
    return true;
  }
  
  public boolean b() {
    if (!this.b.isAlive()) return false;
    double d1 = this.a.j(this.b);
    if ((d1 < 9.0D) || (d1 > 256.0D)) return false;
    return true;
  }
  
  public void c() {
    this.d = 0;
  }
  
  public void d() {
    this.b = null;
  }
  
  public void e() {
    if (--this.d > 0) return;
    this.d = 10;
    this.a.al().a(this.b, this.c);
  }
}
