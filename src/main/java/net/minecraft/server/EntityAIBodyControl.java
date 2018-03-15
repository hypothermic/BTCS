package net.minecraft.server;



public class EntityAIBodyControl
{
  private EntityLiving entity;
  
  private int b = 0;
  private float c = 0.0F;
  
  public EntityAIBodyControl(EntityLiving paramEntityLiving) {
    this.entity = paramEntityLiving;
  }
  
  public void a() {
    double d1 = this.entity.locX - this.entity.lastX;
    double d2 = this.entity.locZ - this.entity.lastZ;
    
    if (d1 * d1 + d2 * d2 > 2.500000277905201E-7D)
    {
      this.entity.V = this.entity.yaw;
      this.entity.X = a(this.entity.V, this.entity.X, 75.0F);
      this.c = this.entity.X;
      this.b = 0;
      return;
    }
    

    float f = 75.0F;
    if (Math.abs(this.entity.X - this.c) > 15.0F) {
      this.b = 0;
      this.c = this.entity.X;
    } else {
      this.b += 1;
      
      if (this.b > 10) { f = Math.max(1.0F - (this.b - 10) / 10.0F, 0.0F) * 75.0F;
      }
    }
    this.entity.V = a(this.entity.X, this.entity.V, f);
  }
  
  private float a(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = paramFloat1 - paramFloat2;
    while (f < -180.0F)
      f += 360.0F;
    while (f >= 180.0F)
      f -= 360.0F;
    if (f < -paramFloat3) f = -paramFloat3;
    if (f >= paramFloat3) f = paramFloat3;
    return paramFloat1 - f;
  }
}
