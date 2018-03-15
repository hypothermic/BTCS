package net.minecraft.server;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PathfinderGoalNearestAttackableTarget
  extends PathfinderGoalTarget
{
  EntityLiving a;
  Class b;
  int f;
  private DistanceComparator g;
  
  public PathfinderGoalNearestAttackableTarget(EntityLiving paramEntityLiving, Class paramClass, float paramFloat, int paramInt, boolean paramBoolean)
  {
    this(paramEntityLiving, paramClass, paramFloat, paramInt, paramBoolean, false);
  }
  
  public PathfinderGoalNearestAttackableTarget(EntityLiving paramEntityLiving, Class paramClass, float paramFloat, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    super(paramEntityLiving, paramFloat, paramBoolean1, paramBoolean2);
    this.b = paramClass;
    this.d = paramFloat;
    this.f = paramInt;
    this.g = new DistanceComparator(this, paramEntityLiving);
    a(1);
  }
  
  public boolean a() {
    if ((this.f > 0) && (this.c.an().nextInt(this.f) != 0)) return false;
    Object localObject; if (this.b == EntityHuman.class) {
      localObject = this.c.world.findNearbyVulnerablePlayer(this.c, this.d);
      if (a((EntityLiving)localObject, false)) {
        this.a = ((EntityLiving)localObject);
        return true;
      }
    } else {
      localObject = this.c.world.a(this.b, this.c.boundingBox.grow(this.d, 4.0D, this.d));
      Collections.sort((List)localObject, this.g);
      for (Entity localEntity : (Entity[]) localObject) { // BTCS: edited cast from (List) to (Entity[])
        EntityLiving localEntityLiving = (EntityLiving)localEntity;
        if (a(localEntityLiving, false)) {
          this.a = localEntityLiving;
          return true;
        }
      }
    }
    return false;
  }
  
  public void c() {
    this.c.b(this.a);
    super.c();
  }
}
