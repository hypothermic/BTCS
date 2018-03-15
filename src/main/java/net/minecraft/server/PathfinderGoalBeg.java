package net.minecraft.server;

import java.util.Random;



public class PathfinderGoalBeg
  extends PathfinderGoal
{
  private EntityWolf a;
  private EntityHuman b;
  private World c;
  private float d;
  private int e;
  
  public PathfinderGoalBeg(EntityWolf paramEntityWolf, float paramFloat)
  {
    this.a = paramEntityWolf;
    this.c = paramEntityWolf.world;
    this.d = paramFloat;
    a(2);
  }
  
  public boolean a()
  {
    this.b = this.c.findNearbyPlayer(this.a, this.d);
    if (this.b == null) return false;
    return a(this.b);
  }
  
  public boolean b() {
    if (!this.b.isAlive()) return false;
    if (this.a.j(this.b) > this.d * this.d) return false;
    return (this.e > 0) && (a(this.b));
  }
  
  public void c() {
    this.a.e(true);
    this.e = (40 + this.a.an().nextInt(40));
  }
  
  public void d() {
    this.a.e(false);
    this.b = null;
  }
  
  public void e() {
    this.a.getControllerLook().a(this.b.locX, this.b.locY + this.b.getHeadHeight(), this.b.locZ, 10.0F, this.a.D());
    this.e -= 1;
  }
  
  private boolean a(EntityHuman paramEntityHuman) {
    ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
    if (localItemStack == null) return false;
    if ((!this.a.isTamed()) && (localItemStack.id == Item.BONE.id)) return true;
    return this.a.a(localItemStack);
  }
}
