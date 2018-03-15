package net.minecraft.server;

import java.util.Random;








public class EntityEnderSignal
  extends Entity
{
  public int a = 0;
  private double b;
  private double c;
  
  public EntityEnderSignal(World paramWorld)
  {
    super(paramWorld);
    b(0.25F, 0.25F);
  }
  




  protected void b() {}
  




  public EntityEnderSignal(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    super(paramWorld);
    this.e = 0;
    
    b(0.25F, 0.25F);
    
    setPosition(paramDouble1, paramDouble2, paramDouble3);
    this.height = 0.0F;
  }
  

  public void a(double paramDouble1, int paramInt, double paramDouble2)
  {
    double d1 = paramDouble1 - this.locX;double d2 = paramDouble2 - this.locZ;
    float f1 = MathHelper.sqrt(d1 * d1 + d2 * d2);
    
    if (f1 > 12.0F) {
      this.b = (this.locX + d1 / f1 * 12.0D);
      this.d = (this.locZ + d2 / f1 * 12.0D);
      this.c = (this.locY + 8.0D);
    } else {
      this.b = paramDouble1;
      this.c = paramInt;
      this.d = paramDouble2;
    }
    
    this.e = 0;
    this.f = (this.random.nextInt(5) > 0);
  }
  


  private double d;
  

  private int e;
  

  private boolean f;
  
  public void F_()
  {
    this.bL = this.locX;
    this.bM = this.locY;
    this.bN = this.locZ;
    super.F_();
    
    this.locX += this.motX;
    this.locY += this.motY;
    this.locZ += this.motZ;
    
    float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
    this.pitch = ((float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D));
    
    while (this.pitch - this.lastPitch < -180.0F)
      this.lastPitch -= 360.0F;
    while (this.pitch - this.lastPitch >= 180.0F) {
      this.lastPitch += 360.0F;
    }
    while (this.yaw - this.lastYaw < -180.0F)
      this.lastYaw -= 360.0F;
    while (this.yaw - this.lastYaw >= 180.0F) {
      this.lastYaw += 360.0F;
    }
    this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
    this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
    
    if (!this.world.isStatic) {
      double d1 = this.b - this.locX;double d2 = this.d - this.locZ;
      float f2 = (float)Math.sqrt(d1 * d1 + d2 * d2);
      float f3 = (float)Math.atan2(d2, d1);
      double d3 = f1 + (f2 - f1) * 0.0025D;
      if (f2 < 1.0F) {
        d3 *= 0.8D;
        this.motY *= 0.8D;
      }
      this.motX = (Math.cos(f3) * d3);
      this.motZ = (Math.sin(f3) * d3);
      
      if (this.locY < this.c) {
        this.motY += (1.0D - this.motY) * 0.014999999664723873D;
      } else {
        this.motY += (-1.0D - this.motY) * 0.014999999664723873D;
      }
    }
    

    float f4 = 0.25F;
    if (aU()) {
      for (int i = 0; i < 4; i++) {
        this.world.a("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
      }
      
    } else {
      this.world.a("portal", this.locX - this.motX * f4 + this.random.nextDouble() * 0.6D - 0.3D, this.locY - this.motY * f4 - 0.5D, this.locZ - this.motZ * f4 + this.random.nextDouble() * 0.6D - 0.3D, this.motX, this.motY, this.motZ);
    }
    
    if (!this.world.isStatic) {
      setPosition(this.locX, this.locY, this.locZ);
      

      this.e += 1;
      if ((this.e > 80) && (!this.world.isStatic)) {
        die();
        if (this.f) {
          this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.EYE_OF_ENDER)));
        } else {
          this.world.triggerEffect(2003, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), 0);
        }
      }
    }
  }
  


  public void b(NBTTagCompound paramNBTTagCompound) {}
  


  public void a(NBTTagCompound paramNBTTagCompound) {}
  


  public void a_(EntityHuman paramEntityHuman) {}
  

  public float b(float paramFloat)
  {
    return 1.0F;
  }
  





  public boolean k_()
  {
    return false;
  }
}
