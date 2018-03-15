package net.minecraft.server;

import org.bukkit.event.entity.EntityTargetEvent; // BTCS
import org.bukkit.event.entity.EntityTargetEvent.TargetReason; // BTCS

public class PathfinderGoalMeleeAttack extends PathfinderGoal
{
  World a;
  EntityLiving b;
  EntityLiving c;
  int d;
  float e;
  boolean f;
  PathEntity g;
  Class h;
  private int i;
  
  public PathfinderGoalMeleeAttack(EntityLiving entityliving, Class oclass, float f, boolean flag) {
    this(entityliving, f, flag);
    this.h = oclass;
  }
  
  public PathfinderGoalMeleeAttack(EntityLiving entityliving, float f, boolean flag) {
    this.d = 0;
    this.b = entityliving;
    this.a = entityliving.world;
    this.e = f;
    this.f = flag;
    a(3);
  }
  
  public boolean a() {
    EntityLiving entityliving = this.b.at();
    
    if (entityliving == null)
      return false;
    if ((this.h != null) && (!this.h.isAssignableFrom(entityliving.getClass()))) {
      return false;
    }
    this.c = entityliving;
    this.g = this.b.al().a(this.c);
    return this.g != null;
  }
  
  public boolean b()
  {
    EntityLiving entityliving = this.b.at();
    
    return !this.f ? false : !this.b.al().e() ? true : !this.c.isAlive() ? false : entityliving == null ? false : this.b.e(MathHelper.floor(this.c.locX), MathHelper.floor(this.c.locY), MathHelper.floor(this.c.locZ));
  }
  
  public void c() {
    this.b.al().a(this.g, this.e);
    this.i = 0;
  }
  
  public void d()
  {
    EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
    org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetEvent(this.b, null, reason);
    
    this.c = null;
    this.b.al().f();
  }
  
  public void e() {
    this.b.getControllerLook().a(this.c, 30.0F, 30.0F);
    if (((this.f) || (this.b.am().canSee(this.c))) && (--this.i <= 0)) {
      this.i = (4 + this.b.an().nextInt(7));
      this.b.al().a(this.c, this.e);
    }
    
    this.d = Math.max(this.d - 1, 0);
    double d0 = this.b.width * 2.0F * this.b.width * 2.0F;
    
    if ((this.b.e(this.c.locX, this.c.boundingBox.b, this.c.locZ) <= d0) && 
      (this.d <= 0)) {
      this.d = 20;
      this.b.a(this.c);
    }
  }
}
