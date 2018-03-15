package net.minecraft.server;

import java.util.List;
import java.util.Random;




public class PathfinderGoalTakeFlower
  extends PathfinderGoal
{
  private EntityVillager a;
  private EntityIronGolem b;
  private int c;
  private boolean d = false;
  
  public PathfinderGoalTakeFlower(EntityVillager paramEntityVillager) {
    this.a = paramEntityVillager;
    a(3);
  }
  
  public boolean a()
  {
    if (this.a.getAge() >= 0) return false;
    if (!this.a.world.e()) { return false;
    }
    List localList = this.a.world.a(EntityIronGolem.class, this.a.boundingBox.grow(6.0D, 2.0D, 6.0D));
    if (localList.size() == 0) { return false;
    }
    for (Entity localEntity : (Entity[]) localList.toArray()) { // BTCS: added cast and .toArray()
      EntityIronGolem localEntityIronGolem = (EntityIronGolem)localEntity;
      if (localEntityIronGolem.m_() > 0) {
        this.b = localEntityIronGolem;
        break;
      }
    }
    return this.b != null;
  }
  
  public boolean b() {
    return this.b.m_() > 0;
  }
  
  public void c() {
    this.c = this.a.an().nextInt(320);
    this.d = false;
    this.b.al().f();
  }
  
  public void d() {
    this.b = null;
    this.a.al().f();
  }
  
  public void e() {
    this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
    if (this.b.m_() == this.c) {
      this.a.al().a(this.b, 0.15F);
      this.d = true;
    }
    
    if ((this.d) && 
      (this.a.j(this.b) < 4.0D)) {
      this.b.a(false);
      this.a.al().f();
    }
  }
}
