package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PathfinderGoalMoveThroughVillage
  extends PathfinderGoal
{
  private EntityCreature a;
  private float b;
  private PathEntity c;
  private VillageDoor d;
  private boolean e;
  private List f = new ArrayList();
  
  public PathfinderGoalMoveThroughVillage(EntityCreature paramEntityCreature, float paramFloat, boolean paramBoolean) {
    this.a = paramEntityCreature;
    this.b = paramFloat;
    this.e = paramBoolean;
    a(1);
  }
  
  public boolean a()
  {
    f();
    
    if ((this.e) && (this.a.world.e())) { return false;
    }
    Village localVillage = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 0);
    if (localVillage == null) { return false;
    }
    this.d = a(localVillage);
    if (this.d == null) { return false;
    }
    boolean bool = this.a.al().b();
    this.a.al().b(false);
    this.c = this.a.al().a(this.d.locX, this.d.locY, this.d.locZ);
    this.a.al().b(bool);
    if (this.c != null) { return true;
    }
    Vec3D localVec3D = RandomPositionGenerator.a(this.a, 10, 7, Vec3D.create(this.d.locX, this.d.locY, this.d.locZ));
    if (localVec3D == null) return false;
    this.a.al().b(false);
    this.c = this.a.al().a(localVec3D.a, localVec3D.b, localVec3D.c);
    this.a.al().b(bool);
    return this.c != null;
  }
  
  public boolean b()
  {
    if (this.a.al().e()) return false;
    float f1 = this.a.width + 4.0F;
    return this.a.e((double) this.d.locX, this.d.locY, this.d.locZ) > f1 * f1;
  }
  
  public void c()
  {
    this.a.al().a(this.c, this.b);
  }
  
  public void d() {
    if ((this.a.al().e()) || (this.a.e((double) this.d.locX, this.d.locY, this.d.locZ) < 16.0D)) this.f.add(this.d);
  }
  
  private VillageDoor a(Village paramVillage) {
    Object localObject = null;
    int i = Integer.MAX_VALUE;
    List localList = paramVillage.getDoors();
    for (VillageDoor localVillageDoor : (VillageDoor[]) localList.toArray()) {  // BTCS: added cast and .toArray()
      int j = localVillageDoor.a(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
      if (j < i)
        if (!a(localVillageDoor)) {
          localObject = localVillageDoor;
          i = j;
        }
    }
    return (VillageDoor)localObject;
  }
  
  // BTCS start
  /*private boolean a(VillageDoor paramVillageDoor) {
    for (Iterator localIterator = this.f.iterator(); localIterator.hasNext(); return true) {
      VillageDoor localVillageDoor = (VillageDoor)localIterator.next();
      if ((paramVillageDoor.locX != localVillageDoor.locX) || (paramVillageDoor.locY != localVillageDoor.locY) || (paramVillageDoor.locZ != localVillageDoor.locZ)) {} }
    return false;
  }*/
  private boolean a(VillageDoor villagedoor) {
      Iterator iterator = this.f.iterator();

      VillageDoor villagedoor1;

      do {
          if (!iterator.hasNext()) {
              return false;
          }

          villagedoor1 = (VillageDoor) iterator.next();
      } while (villagedoor.locX != villagedoor1.locX || villagedoor.locY != villagedoor1.locY || villagedoor.locZ != villagedoor1.locZ);

      return true;
  }
  // BTCS end
  
  private void f() {
    if (this.f.size() > 15) this.f.remove(0);
  }
}
