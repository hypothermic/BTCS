package net.minecraft.server;


public abstract class EntityFlying
  extends EntityLiving
{
  public EntityFlying(World paramWorld)
  {
    super(paramWorld);
  }
  
  protected void a(float paramFloat) {}
  
  public void a(float paramFloat1, float paramFloat2)
  {
    if (aU()) {
      a(paramFloat1, paramFloat2, 0.02F);
      move(this.motX, this.motY, this.motZ);
      
      this.motX *= 0.800000011920929D;
      this.motY *= 0.800000011920929D;
      this.motZ *= 0.800000011920929D;
    } else if (aV()) {
      a(paramFloat1, paramFloat2, 0.02F);
      move(this.motX, this.motY, this.motZ);
      this.motX *= 0.5D;
      this.motY *= 0.5D;
      this.motZ *= 0.5D;
    } else {
      float f1 = 0.91F;
      if (this.onGround) {
        f1 = 0.54600006F;
        int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
        if (i > 0) {
          f1 = Block.byId[i].frictionFactor * 0.91F;
        }
      }
      
      float f2 = 0.16277136F / (f1 * f1 * f1);
      a(paramFloat1, paramFloat2, this.onGround ? 0.1F * f2 : 0.02F);
      
      f1 = 0.91F;
      if (this.onGround) {
        f1 = 0.54600006F;
        int j = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
        if (j > 0) {
          f1 = Block.byId[j].frictionFactor * 0.91F;
        }
      }
      
      move(this.motX, this.motY, this.motZ);
      
      this.motX *= f1;
      this.motY *= f1;
      this.motZ *= f1;
    }
    this.aD = this.aE;
    double d1 = this.locX - this.lastX;
    double d2 = this.locZ - this.lastZ;
    float f3 = MathHelper.sqrt(d1 * d1 + d2 * d2) * 4.0F;
    if (f3 > 1.0F) f3 = 1.0F;
    this.aE += (f3 - this.aE) * 0.4F;
    this.aF += this.aE;
  }
  
  public boolean t() {
    return false;
  }
}
