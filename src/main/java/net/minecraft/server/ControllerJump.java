package net.minecraft.server;


public class ControllerJump
{
  private EntityLiving a;
  private boolean b = false;
  
  public ControllerJump(EntityLiving paramEntityLiving) {
    this.a = paramEntityLiving;
  }
  
  public void a() {
    this.b = true;
  }
  
  public void b() {
    this.a.f(this.b);
    this.b = false;
  }
}
