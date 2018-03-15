package net.minecraft.server;

import org.bukkit.craftbukkit.event.CraftEventFactory;

public class PathfinderGoalBreakDoor extends PathfinderGoalDoorInteract {
  private int i;
  
  public PathfinderGoalBreakDoor(EntityLiving entityliving) { super(entityliving); }
  
  public boolean a()
  {
    return super.a();
  }
  
  public void c() {
    super.c();
    this.i = 240;
  }
  
  public boolean b() {
    double d0 = this.a.e((double)this.b, this.c, this.d); // BTCS: added cast (double)
    
    return (this.i >= 0) && (!this.e.d(this.a.world, this.b, this.c, this.d)) && (d0 < 4.0D);
  }
  
  public void e() {
    super.e();
    if (this.a.an().nextInt(20) == 0) {
      this.a.world.triggerEffect(1010, this.b, this.c, this.d, 0);
    }
    
    if ((--this.i == 0) && (this.a.world.difficulty == 3))
    {
      if (CraftEventFactory.callEntityBreakDoorEvent(this.a, this.b, this.c, this.d).isCancelled()) {
        c();
        return;
      }
      
      this.a.world.setTypeId(this.b, this.c, this.d, 0);
      this.a.world.triggerEffect(1012, this.b, this.c, this.d, 0);
      this.a.world.triggerEffect(2001, this.b, this.c, this.d, this.e.id);
    }
  }
}
