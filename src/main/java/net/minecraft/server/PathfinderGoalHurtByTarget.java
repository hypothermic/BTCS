package net.minecraft.server;

import java.util.List;


public class PathfinderGoalHurtByTarget
  extends PathfinderGoalTarget
{
  boolean a;
  
  public PathfinderGoalHurtByTarget(EntityLiving paramEntityLiving, boolean paramBoolean)
  {
    super(paramEntityLiving, 16.0F, false);
    this.a = paramBoolean;
    a(1);
  }
  
  public boolean a() {
    return a(this.c.ao(), true);
  }
  
  public void c() {
    this.c.b(this.c.ao());
    
    if (this.a) {
      List localList = this.c.world.a(this.c.getClass(), AxisAlignedBB.b(this.c.locX, this.c.locY, this.c.locZ, this.c.locX + 1.0D, this.c.locY + 1.0D, this.c.locZ + 1.0D).grow(this.d, 4.0D, this.d));
      for (Entity localEntity : (Entity[])localList.toArray()) { // BTCS: added cast and .toArray()
        EntityLiving localEntityLiving = (EntityLiving)localEntity;
        if ((this.c != localEntityLiving) && 
          (localEntityLiving.at() == null)) {
          localEntityLiving.b(this.c.ao());
        }
      }
    }
    super.c();
  }
}
