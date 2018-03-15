package net.minecraft.server;

import java.util.Comparator;

public class DistanceComparator
  implements Comparator
{
  private Entity b;
  
  public DistanceComparator(PathfinderGoalNearestAttackableTarget paramPathfinderGoalNearestAttackableTarget, Entity paramEntity)
  {
    this.b = paramEntity;
  }
  
  public int a(Entity paramEntity1, Entity paramEntity2) {
    double d1 = this.b.j(paramEntity1);
    double d2 = this.b.j(paramEntity2);
    if (d1 < d2) return -1;
    if (d1 > d2) return 1;
    return 0;
  }
  
  // BTCS start
  public int compare(Object arg0, Object arg1) {
	// TODO Auto-generated method stub
	return 0;
  }
  // BTCS end
}
