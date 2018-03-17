package net.minecraft.server;





public abstract class WM_PhysHelper
{
  public static WM_Explosion createAdvancedExplosion(World paramWorld, Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    WM_Explosion localWM_Explosion = new WM_Explosion(paramWorld, paramEntity, paramDouble1, paramDouble2, paramDouble3, paramFloat);
    localWM_Explosion.a = paramBoolean1;
    localWM_Explosion.a();
    localWM_Explosion.doExplosionB(paramBoolean3, paramBoolean4, paramBoolean2);
    return localWM_Explosion;
  }
  
  public static WM_Explosion createAdvancedExplosion(World paramWorld, Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    WM_Explosion localWM_Explosion = new WM_Explosion(paramWorld, paramEntity, paramDouble1, paramDouble2, paramDouble3, paramFloat);
    localWM_Explosion.a = paramBoolean1;
    localWM_Explosion.a();
    localWM_Explosion.doExplosionB(paramBoolean3, paramBoolean3, paramBoolean2);
    return localWM_Explosion;
  }
  
  public static void knockBack(EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2, float paramFloat)
  {
    paramEntityLiving1.motX = 0.0D;
    paramEntityLiving1.motY = 0.0D;
    paramEntityLiving1.motZ = 0.0D;
    float f1 = 2.5F;
    
    if (((paramEntityLiving2 instanceof EntityHuman)) && (paramEntityLiving2.isSprinting()))
    {
      paramEntityLiving1.b_(-Math.sin(Math.toRadians(paramEntityLiving2.yaw)) * paramFloat * f1, 0.1F * paramFloat * f1, Math.cos(Math.toRadians(paramEntityLiving2.yaw)) * paramFloat * f1);
    }
    
    double d1 = paramEntityLiving2.locX - paramEntityLiving1.locX;
    
    double d2;
    for (d2 = paramEntityLiving2.locZ - paramEntityLiving1.locZ; d1 * d1 + d2 * d2 < 1.0E-4D; d2 = (Math.random() - Math.random()) * 0.01D)
    {
      d1 = (Math.random() - Math.random()) * 0.01D;
    }
    
    paramEntityLiving1.au = ((float)(Math.atan2(d2, d1) * 180.0D / 3.141592653589793D) - paramEntityLiving1.yaw);
    float f2 = MathHelper.sqrt(d1 * d1 + d2 * d2);
    float f3 = paramFloat;
    paramEntityLiving1.motX -= d1 / f2 * f3;
    paramEntityLiving1.motY += 0.4D;
    paramEntityLiving1.motZ -= d2 / f2 * f3;
    
    if (paramEntityLiving1.motY > 0.4D)
    {
      paramEntityLiving1.motY = 0.4D;
    }
  }
}
