package net.minecraft.server;

import java.util.Random;




public class RandomPositionGenerator
{
  private static Vec3D a = Vec3D.a(0.0D, 0.0D, 0.0D);
  
  public static Vec3D a(EntityCreature paramEntityCreature, int paramInt1, int paramInt2) {
    return c(paramEntityCreature, paramInt1, paramInt2, null);
  }
  
  public static Vec3D a(EntityCreature paramEntityCreature, int paramInt1, int paramInt2, Vec3D paramVec3D) {
    a.a = (paramVec3D.a - paramEntityCreature.locX);
    a.b = (paramVec3D.b - paramEntityCreature.locY);
    a.c = (paramVec3D.c - paramEntityCreature.locZ);
    return c(paramEntityCreature, paramInt1, paramInt2, a);
  }
  
  public static Vec3D b(EntityCreature paramEntityCreature, int paramInt1, int paramInt2, Vec3D paramVec3D) {
    a.a = (paramEntityCreature.locX - paramVec3D.a);
    a.b = (paramEntityCreature.locY - paramVec3D.b);
    a.c = (paramEntityCreature.locZ - paramVec3D.c);
    return c(paramEntityCreature, paramInt1, paramInt2, a);
  }
  
  private static Vec3D c(EntityCreature paramEntityCreature, int paramInt1, int paramInt2, Vec3D paramVec3D) {
    Random localRandom = paramEntityCreature.an();
    int i = 0;
    int j = 0;int k = 0;int m = 0;
    float f1 = -99999.0F;
    
    int n;
    if (paramEntityCreature.ay()) {
      double d = paramEntityCreature.av().b(MathHelper.floor(paramEntityCreature.locX), MathHelper.floor(paramEntityCreature.locY), MathHelper.floor(paramEntityCreature.locZ)) + 4.0D;
      n = d < paramEntityCreature.aw() + paramInt1 ? 1 : 0;
    } else { n = 0;
    }
    for (int i1 = 0; i1 < 10; i1++) {
      int i2 = localRandom.nextInt(2 * paramInt1) - paramInt1;
      int i3 = localRandom.nextInt(2 * paramInt2) - paramInt2;
      int i4 = localRandom.nextInt(2 * paramInt1) - paramInt1;
      
      if ((paramVec3D == null) || (i2 * paramVec3D.a + i4 * paramVec3D.c >= 0.0D))
      {
        i2 += MathHelper.floor(paramEntityCreature.locX);
        i3 += MathHelper.floor(paramEntityCreature.locY);
        i4 += MathHelper.floor(paramEntityCreature.locZ);
        
        if ((n == 0) || (paramEntityCreature.e(i2, i3, i4))) {
          float f2 = paramEntityCreature.a(i2, i3, i4);
          if (f2 > f1) {
            f1 = f2;
            j = i2;
            k = i3;
            m = i4;
            i = 1;
          }
        } } }
    if (i != 0) {
      return Vec3D.create(j, k, m);
    }
    
    return null;
  }
}
