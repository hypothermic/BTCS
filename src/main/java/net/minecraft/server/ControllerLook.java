package net.minecraft.server;


public class ControllerLook
{
  private EntityLiving a;
  
  private float b;
  private float c;
  private boolean d = false;
  
  private double e;
  
  public ControllerLook(EntityLiving paramEntityLiving) { this.a = paramEntityLiving; }
  
  private double f;
  private double g;
  public void a(Entity paramEntity, float paramFloat1, float paramFloat2) { this.e = paramEntity.locX;
    if ((paramEntity instanceof EntityLiving)) this.f = (paramEntity.locY + ((EntityLiving)paramEntity).getHeadHeight()); else
      this.f = ((paramEntity.boundingBox.b + paramEntity.boundingBox.e) / 2.0D);
    this.g = paramEntity.locZ;
    this.b = paramFloat1;
    this.c = paramFloat2;
    this.d = true;
  }
  
  public void a(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
    this.e = paramDouble1;
    this.f = paramDouble2;
    this.g = paramDouble3;
    this.b = paramFloat1;
    this.c = paramFloat2;
    this.d = true;
  }
  
  public void a() {
    this.a.pitch = 0.0F;
    
    if (this.d) {
      this.d = false;
      
      double d1 = this.e - this.a.locX;
      double d2 = this.f - (this.a.locY + this.a.getHeadHeight());
      double d3 = this.g - this.a.locZ;
      double d4 = MathHelper.sqrt(d1 * d1 + d3 * d3);
      
      float f1 = (float)(Math.atan2(d3, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
      float f2 = (float)-(Math.atan2(d2, d4) * 180.0D / 3.1415927410125732D);
      this.a.pitch = a(this.a.pitch, f2, this.c);
      this.a.X = a(this.a.X, f1, this.b);
    } else {
      this.a.X = a(this.a.X, this.a.V, 10.0F);
    }
    
    float f3 = this.a.X - this.a.V;
    while (f3 < -180.0F)
      f3 += 360.0F;
    while (f3 >= 180.0F) {
      f3 -= 360.0F;
    }
    if (!this.a.al().e())
    {
      if (f3 < -75.0F) this.a.X = (this.a.V - 75.0F);
      if (f3 > 75.0F) this.a.X = (this.a.V + 75.0F);
    }
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
