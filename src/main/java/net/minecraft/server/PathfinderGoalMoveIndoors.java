package net.minecraft.server;

import java.util.Random;







public class PathfinderGoalMoveIndoors
  extends PathfinderGoal
{
  private int d = -1; private int c = -1;
  private VillageDoor b;
  
  public PathfinderGoalMoveIndoors(EntityCreature paramEntityCreature) { this.a = paramEntityCreature;
    a(1);
  }
  
  private EntityCreature a;
  public boolean a() { if (((this.a.world.e()) && (!this.a.world.x())) || (this.a.world.worldProvider.e)) return false;
    if (this.a.an().nextInt(50) != 0) return false;
    if ((this.c != -1) && (this.a.e(this.c, this.a.locY, this.d) < 4.0D)) return false;
    Village localVillage = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 14);
    if (localVillage == null) return false;
    this.b = localVillage.c(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
    return this.b != null;
  }
  
  public boolean b() {
    return !this.a.al().e();
  }
  
  public void c() {
    this.c = -1;
    if (this.a.e((double) this.b.getIndoorsX(), this.b.locY, this.b.getIndoorsZ()) > 256.0D) { // BTCS: added cast (double)
      Vec3D localVec3D = RandomPositionGenerator.a(this.a, 14, 3, Vec3D.create(this.b.getIndoorsX() + 0.5D, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5D));
      if (localVec3D != null) this.a.al().a(localVec3D.a, localVec3D.b, localVec3D.c, 0.3F);
    } else { this.a.al().a(this.b.getIndoorsX() + 0.5D, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5D, 0.3F);
    }
  }
  
  public void d() { this.c = this.b.getIndoorsX();
    this.d = this.b.getIndoorsZ();
    this.b = null;
  }
}
