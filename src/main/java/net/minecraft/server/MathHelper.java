package net.minecraft.server;

import java.util.Random;

public class MathHelper
{
  private static float[] a = new float[65536];
  static { for (int i = 0; i < 65536; i++) {
      a[i] = ((float)Math.sin(i * 3.141592653589793D * 2.0D / 65536.0D));
    }
  }
  
  public static final float sin(float paramFloat) {
    return a[((int)(paramFloat * 10430.378F) & 0xFFFF)];
  }
  
  public static final float cos(float paramFloat) {
    return a[((int)(paramFloat * 10430.378F + 16384.0F) & 0xFFFF)];
  }
  
  public static final float c(float paramFloat) {
    return (float)Math.sqrt(paramFloat);
  }
  
  public static final float sqrt(double paramDouble) {
    return (float)Math.sqrt(paramDouble);
  }
  
  public static int d(float paramFloat) {
    int i = (int)paramFloat;
    return paramFloat < i ? i - 1 : i;
  }
  



  public static int floor(double paramDouble)
  {
    int i = (int)paramDouble;
    return paramDouble < i ? i - 1 : i;
  }
  
  public static long c(double paramDouble) {
    long l = (long) paramDouble; // BTCS: added cast (long)
    return paramDouble < l ? l - 1L : l;
  }
  



  public static float abs(float paramFloat)
  {
    return paramFloat >= 0.0F ? paramFloat : -paramFloat;
  }
  
  public static int a(int paramInt) {
    return paramInt >= 0 ? paramInt : -paramInt;
  }
  




  public static int a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < paramInt2) {
      return paramInt2;
    }
    if (paramInt1 > paramInt3) {
      return paramInt3;
    }
    return paramInt1;
  }
  





























  public static double a(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 < 0.0D) paramDouble1 = -paramDouble1;
    if (paramDouble2 < 0.0D) paramDouble2 = -paramDouble2;
    return paramDouble1 > paramDouble2 ? paramDouble1 : paramDouble2;
  }
  

















  public static int a(Random paramRandom, int paramInt1, int paramInt2)
  {
    if (paramInt1 >= paramInt2) {
      return paramInt1;
    }
    return paramRandom.nextInt(paramInt2 - paramInt1 + 1) + paramInt1;
  }
}
