package net.minecraft.server;



public class ControllerMove
{
  private EntityLiving a;
  

  private double b;
  
  private double c;
  
  private double d;
  
  private float e;
  
  private boolean f = false;
  
  public ControllerMove(EntityLiving paramEntityLiving) {
    this.a = paramEntityLiving;
    this.b = paramEntityLiving.locX;
    this.c = paramEntityLiving.locY;
    this.d = paramEntityLiving.locZ;
  }
  
  public boolean a() {
    return this.f;
  }
  
  public float b() {
    return this.e;
  }
  
  public void a(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat) {
    this.b = paramDouble1;
    this.c = paramDouble2;
    this.d = paramDouble3;
    this.e = paramFloat;
    this.f = true;
  }
  
  public void c() {
    this.a.e(0.0F);
    if (!this.f) return;
    this.f = false;
    
    int i = MathHelper.floor(this.a.boundingBox.b + 0.5D);
    
    double d1 = this.b - this.a.locX;
    double d2 = this.d - this.a.locZ;
    double d3 = this.c - i;
    double d4 = d1 * d1 + d3 * d3 + d2 * d2;
    if (d4 < 2.500000277905201E-7D) { return;
    }
    float f1 = (float)(Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
    
    this.a.yaw = a(this.a.yaw, f1, 30.0F);
    this.a.d(this.e);
    
    if ((d3 > 0.0D) && (d1 * d1 + d2 * d2 < 1.0D)) this.a.getControllerJump().a();
  }
  
  private float a(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f1 = paramFloat2 - paramFloat1;
    while (f1 < -180.0F)
      f1 += 360.0F;
    while (f1 >= 180.0F)
      f1 -= 360.0F;
    if (f1 > paramFloat3) {
      f1 = paramFloat3;
    }
    if (f1 < -paramFloat3) {
      f1 = -paramFloat3;
    }
    return paramFloat1 + f1;
  }
}
