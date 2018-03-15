package net.minecraft.server;

import java.util.Random;



public class WorldGenCavesHell
  extends WorldGenBase
{
  protected void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, 1.0F + this.c.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
  }
  
  protected void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
    double d1 = paramInt1 * 16 + 8;
    double d2 = paramInt2 * 16 + 8;
    
    float f1 = 0.0F;
    float f2 = 0.0F;
    

    Random localRandom = new Random(this.c.nextLong());
    
    if (paramInt4 <= 0) {
      int i = this.b * 16 - 16; // BTCS: added decl 'int '
      paramInt4 = i - localRandom.nextInt(i / 4);
    }
    int i = 0;
    
    if (paramInt3 == -1) {
      paramInt3 = paramInt4 / 2;
      i = 1;
    }
    

    int j = localRandom.nextInt(paramInt4 / 2) + paramInt4 / 4;
    int k = localRandom.nextInt(6) == 0 ? 1 : 0;
    for (; 
        paramInt3 < paramInt4; paramInt3++) {
      double d3 = 1.5D + MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F;
      double d4 = d3 * paramDouble4;
      
      float f3 = MathHelper.cos(paramFloat3);
      float f4 = MathHelper.sin(paramFloat3);
      paramDouble1 += MathHelper.cos(paramFloat2) * f3;
      paramDouble2 += f4;
      paramDouble3 += MathHelper.sin(paramFloat2) * f3;
      
      if (k != 0) {
        paramFloat3 *= 0.92F;
      } else {
        paramFloat3 *= 0.7F;
      }
      paramFloat3 += f2 * 0.1F;
      paramFloat2 += f1 * 0.1F;
      
      f2 *= 0.9F;
      f1 *= 0.75F;
      f2 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 2.0F;
      f1 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 4.0F;
      

      if ((i == 0) && (paramInt3 == j) && (paramFloat1 > 1.0F)) {
        a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, localRandom.nextFloat() * 0.5F + 0.5F, paramFloat2 - 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
        a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, localRandom.nextFloat() * 0.5F + 0.5F, paramFloat2 + 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
        return;
      }
      if ((i != 0) || (localRandom.nextInt(4) != 0))
      {

        double d5 = paramDouble1 - d1;
        double d6 = paramDouble3 - d2;
        double d7 = paramInt4 - paramInt3;
        double d8 = paramFloat1 + 2.0F + 16.0F;
        if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
          return;
        }
        

        if ((paramDouble1 >= d1 - 16.0D - d3 * 2.0D) && (paramDouble3 >= d2 - 16.0D - d3 * 2.0D) && (paramDouble1 <= d1 + 16.0D + d3 * 2.0D) && (paramDouble3 <= d2 + 16.0D + d3 * 2.0D))
        {
          int m = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
          int n = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
          
          int i1 = MathHelper.floor(paramDouble2 - d4) - 1;
          int i2 = MathHelper.floor(paramDouble2 + d4) + 1;
          
          int i3 = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
          int i4 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
          
          if (m < 0) m = 0;
          if (n > 16) { n = 16;
          }
          if (i1 < 1) i1 = 1;
          if (i2 > 120) { i2 = 120;
          }
          if (i3 < 0) i3 = 0;
          if (i4 > 16) { i4 = 16;
          }
          int i5 = 0;
          int i9; for (int i6 = m; (i5 == 0) && (i6 < n); i6++) {
            for (int i7 = i3; (i5 == 0) && (i7 < i4); i7++) {
              for (int i8 = i2 + 1; (i5 == 0) && (i8 >= i1 - 1); i8--) {
                i9 = (i6 * 16 + i7) * 128 + i8;
                if ((i8 >= 0) && (i8 < 128)) {
                  if ((paramArrayOfByte[i9] == Block.LAVA.id) || (paramArrayOfByte[i9] == Block.STATIONARY_LAVA.id)) {
                    i5 = 1;
                  }
                  if ((i8 != i1 - 1) && (i6 != m) && (i6 != n - 1) && (i7 != i3) && (i7 != i4 - 1))
                    i8 = i1;
                }
              }
            }
          }
          if (i5 == 0)
          {
            for (int i6 = m; i6 < n; i6++) { // BTCS: added decl 'int '
              double d9 = (i6 + paramInt1 * 16 + 0.5D - paramDouble1) / d3;
              for (i9 = i3; i9 < i4; i9++) {
                double d10 = (i9 + paramInt2 * 16 + 0.5D - paramDouble3) / d3;
                int i10 = (i6 * 16 + i9) * 128 + i2;
                for (int i11 = i2 - 1; i11 >= i1; i11--) {
                  double d11 = (i11 + 0.5D - paramDouble2) / d4;
                  if ((d11 > -0.7D) && (d9 * d9 + d11 * d11 + d10 * d10 < 1.0D)) {
                    int i12 = paramArrayOfByte[i10];
                    if ((i12 == Block.NETHERRACK.id) || (i12 == Block.DIRT.id) || (i12 == Block.GRASS.id)) {
                      paramArrayOfByte[i10] = 0;
                    }
                  }
                  i10--;
                }
              }
            }
            if (i != 0) {
              break;
            }
          }
        }
      }
    }
  }
  



  protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte)
  {
    int i = this.c.nextInt(this.c.nextInt(this.c.nextInt(10) + 1) + 1);
    if (this.c.nextInt(5) != 0) { i = 0;
    }
    for (int j = 0; j < i; j++) {
      double d1 = paramInt1 * 16 + this.c.nextInt(16);
      double d2 = this.c.nextInt(128);
      double d3 = paramInt2 * 16 + this.c.nextInt(16);
      
      int k = 1;
      if (this.c.nextInt(4) == 0) {
        a(paramInt3, paramInt4, paramArrayOfByte, d1, d2, d3);
        k += this.c.nextInt(4);
      }
      
      for (int m = 0; m < k; m++)
      {
        float f1 = this.c.nextFloat() * 3.1415927F * 2.0F;
        float f2 = (this.c.nextFloat() - 0.5F) * 2.0F / 8.0F;
        float f3 = this.c.nextFloat() * 2.0F + this.c.nextFloat();
        
        a(paramInt3, paramInt4, paramArrayOfByte, d1, d2, d3, f3 * 2.0F, f1, f2, 0, 0, 0.5D);
      }
    }
  }
}
