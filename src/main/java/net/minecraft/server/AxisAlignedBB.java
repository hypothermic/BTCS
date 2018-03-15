package net.minecraft.server;

import java.util.List;

public class AxisAlignedBB
{
  private static List g = new java.util.ArrayList();
  private static int h = 0;
  public double a;
  
  public static AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) { return new AxisAlignedBB(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6); }
  

  public double b;
  
  public double c;
  
  public static void a()
  {
    h = 0;
  }
  
  public static AxisAlignedBB b(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    if (h >= g.size()) {
      g.add(a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
    }
    return ((AxisAlignedBB)g.get(h++)).c(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
  }
  



  private AxisAlignedBB(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
  {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.c = paramDouble3;
    this.d = paramDouble4;
    this.e = paramDouble5;
    this.f = paramDouble6;
  }
  
  public AxisAlignedBB c(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.c = paramDouble3;
    this.d = paramDouble4;
    this.e = paramDouble5;
    this.f = paramDouble6;
    return this;
  }
  
  public AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3) {
    double d1 = this.a;
    double d2 = this.b;
    double d3 = this.c;
    double d4 = this.d;
    double d5 = this.e;
    double d6 = this.f;
    
    if (paramDouble1 < 0.0D) d1 += paramDouble1;
    if (paramDouble1 > 0.0D) { d4 += paramDouble1;
    }
    if (paramDouble2 < 0.0D) d2 += paramDouble2;
    if (paramDouble2 > 0.0D) { d5 += paramDouble2;
    }
    if (paramDouble3 < 0.0D) d3 += paramDouble3;
    if (paramDouble3 > 0.0D) { d6 += paramDouble3;
    }
    return b(d1, d2, d3, d4, d5, d6);
  }
  
  public AxisAlignedBB grow(double paramDouble1, double paramDouble2, double paramDouble3) {
    double d1 = this.a - paramDouble1;
    double d2 = this.b - paramDouble2;
    double d3 = this.c - paramDouble3;
    double d4 = this.d + paramDouble1;
    double d5 = this.e + paramDouble2;
    double d6 = this.f + paramDouble3;
    
    return b(d1, d2, d3, d4, d5, d6);
  }
  
  public AxisAlignedBB c(double paramDouble1, double paramDouble2, double paramDouble3) {
    return b(this.a + paramDouble1, this.b + paramDouble2, this.c + paramDouble3, this.d + paramDouble1, this.e + paramDouble2, this.f + paramDouble3);
  }
  
  public double a(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
    if ((paramAxisAlignedBB.e <= this.b) || (paramAxisAlignedBB.b >= this.e)) return paramDouble;
    if ((paramAxisAlignedBB.f <= this.c) || (paramAxisAlignedBB.c >= this.f)) return paramDouble;
    double d1;
    if ((paramDouble > 0.0D) && (paramAxisAlignedBB.d <= this.a)) {
      d1 = this.a - paramAxisAlignedBB.d;
      if (d1 < paramDouble) paramDouble = d1;
    }
    if ((paramDouble < 0.0D) && (paramAxisAlignedBB.a >= this.d)) {
      d1 = this.d - paramAxisAlignedBB.a;
      if (d1 > paramDouble) { paramDouble = d1;
      }
    }
    return paramDouble;
  }
  
  public double b(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
    if ((paramAxisAlignedBB.d <= this.a) || (paramAxisAlignedBB.a >= this.d)) return paramDouble;
    if ((paramAxisAlignedBB.f <= this.c) || (paramAxisAlignedBB.c >= this.f)) return paramDouble;
    double d1;
    if ((paramDouble > 0.0D) && (paramAxisAlignedBB.e <= this.b)) {
      d1 = this.b - paramAxisAlignedBB.e;
      if (d1 < paramDouble) paramDouble = d1;
    }
    if ((paramDouble < 0.0D) && (paramAxisAlignedBB.b >= this.e)) {
      d1 = this.e - paramAxisAlignedBB.b;
      if (d1 > paramDouble) { paramDouble = d1;
      }
    }
    return paramDouble;
  }
  
  public double c(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
    if ((paramAxisAlignedBB.d <= this.a) || (paramAxisAlignedBB.a >= this.d)) return paramDouble;
    if ((paramAxisAlignedBB.e <= this.b) || (paramAxisAlignedBB.b >= this.e)) return paramDouble;
    double d1;
    if ((paramDouble > 0.0D) && (paramAxisAlignedBB.f <= this.c)) {
      d1 = this.c - paramAxisAlignedBB.f;
      if (d1 < paramDouble) paramDouble = d1;
    }
    if ((paramDouble < 0.0D) && (paramAxisAlignedBB.c >= this.f)) {
      d1 = this.f - paramAxisAlignedBB.c;
      if (d1 > paramDouble) { paramDouble = d1;
      }
    }
    return paramDouble;
  }
  
  public boolean a(AxisAlignedBB paramAxisAlignedBB) {
    if ((paramAxisAlignedBB.d <= this.a) || (paramAxisAlignedBB.a >= this.d)) return false;
    if ((paramAxisAlignedBB.e <= this.b) || (paramAxisAlignedBB.b >= this.e)) return false;
    if ((paramAxisAlignedBB.f <= this.c) || (paramAxisAlignedBB.c >= this.f)) return false;
    return true;
  }
  

  public double d;
  
  public double e;
  
  public double f;
  public AxisAlignedBB d(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.a += paramDouble1;
    this.b += paramDouble2;
    this.c += paramDouble3;
    this.d += paramDouble1;
    this.e += paramDouble2;
    this.f += paramDouble3;
    return this;
  }
  






  public boolean a(Vec3D paramVec3D)
  {
    if ((paramVec3D.a <= this.a) || (paramVec3D.a >= this.d)) return false;
    if ((paramVec3D.b <= this.b) || (paramVec3D.b >= this.e)) return false;
    if ((paramVec3D.c <= this.c) || (paramVec3D.c >= this.f)) return false;
    return true;
  }
  






  public AxisAlignedBB shrink(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d1 = this.a + paramDouble1;
    double d2 = this.b + paramDouble2;
    double d3 = this.c + paramDouble3;
    double d4 = this.d - paramDouble1;
    double d5 = this.e - paramDouble2;
    double d6 = this.f - paramDouble3;
    
    return b(d1, d2, d3, d4, d5, d6);
  }
  
  public AxisAlignedBB clone() {
    return b(this.a, this.b, this.c, this.d, this.e, this.f);
  }
  
  public MovingObjectPosition a(Vec3D paramVec3D1, Vec3D paramVec3D2) {
    Vec3D localVec3D1 = paramVec3D1.a(paramVec3D2, this.a);
    Vec3D localVec3D2 = paramVec3D1.a(paramVec3D2, this.d);
    
    Vec3D localVec3D3 = paramVec3D1.b(paramVec3D2, this.b);
    Vec3D localVec3D4 = paramVec3D1.b(paramVec3D2, this.e);
    
    Vec3D localVec3D5 = paramVec3D1.c(paramVec3D2, this.c);
    Vec3D localVec3D6 = paramVec3D1.c(paramVec3D2, this.f);
    
    if (!b(localVec3D1)) localVec3D1 = null;
    if (!b(localVec3D2)) localVec3D2 = null;
    if (!c(localVec3D3)) localVec3D3 = null;
    if (!c(localVec3D4)) localVec3D4 = null;
    if (!d(localVec3D5)) localVec3D5 = null;
    if (!d(localVec3D6)) { localVec3D6 = null;
    }
    Vec3D localVec3D7 = null;
    
    if ((localVec3D1 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D1) < paramVec3D1.distanceSquared(localVec3D7)))) localVec3D7 = localVec3D1;
    if ((localVec3D2 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D2) < paramVec3D1.distanceSquared(localVec3D7)))) localVec3D7 = localVec3D2;
    if ((localVec3D3 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D3) < paramVec3D1.distanceSquared(localVec3D7)))) localVec3D7 = localVec3D3;
    if ((localVec3D4 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D4) < paramVec3D1.distanceSquared(localVec3D7)))) localVec3D7 = localVec3D4;
    if ((localVec3D5 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D5) < paramVec3D1.distanceSquared(localVec3D7)))) localVec3D7 = localVec3D5;
    if ((localVec3D6 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D6) < paramVec3D1.distanceSquared(localVec3D7)))) { localVec3D7 = localVec3D6;
    }
    if (localVec3D7 == null) { return null;
    }
    int i = -1;
    
    if (localVec3D7 == localVec3D1) i = 4;
    if (localVec3D7 == localVec3D2) i = 5;
    if (localVec3D7 == localVec3D3) i = 0;
    if (localVec3D7 == localVec3D4) i = 1;
    if (localVec3D7 == localVec3D5) i = 2;
    if (localVec3D7 == localVec3D6) { i = 3;
    }
    return new MovingObjectPosition(0, 0, 0, i, localVec3D7);
  }
  
  private boolean b(Vec3D paramVec3D) {
    if (paramVec3D == null) return false;
    return (paramVec3D.b >= this.b) && (paramVec3D.b <= this.e) && (paramVec3D.c >= this.c) && (paramVec3D.c <= this.f);
  }
  
  private boolean c(Vec3D paramVec3D) {
    if (paramVec3D == null) return false;
    return (paramVec3D.a >= this.a) && (paramVec3D.a <= this.d) && (paramVec3D.c >= this.c) && (paramVec3D.c <= this.f);
  }
  
  private boolean d(Vec3D paramVec3D) {
    if (paramVec3D == null) return false;
    return (paramVec3D.a >= this.a) && (paramVec3D.a <= this.d) && (paramVec3D.b >= this.b) && (paramVec3D.b <= this.e);
  }
  
  public void b(AxisAlignedBB paramAxisAlignedBB) {
    this.a = paramAxisAlignedBB.a;
    this.b = paramAxisAlignedBB.b;
    this.c = paramAxisAlignedBB.c;
    this.d = paramAxisAlignedBB.d;
    this.e = paramAxisAlignedBB.e;
    this.f = paramAxisAlignedBB.f;
  }
  
  public String toString() {
    return "box[" + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + "]";
  }
}
