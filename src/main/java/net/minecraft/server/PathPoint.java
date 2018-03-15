package net.minecraft.server;

public class PathPoint
{
  public final int a;
  public final int b;
  public final int c;
  private final int j;
  int d = -1;
  float e;
  float f;
  float g; PathPoint h; public boolean i = false;
  
  public PathPoint(int paramInt1, int paramInt2, int paramInt3) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    
    this.j = a(paramInt1, paramInt2, paramInt3);
  }
  
  public static int a(int paramInt1, int paramInt2, int paramInt3) {
    return paramInt2 & 0xFF | (paramInt1 & 0x7FFF) << 8 | (paramInt3 & 0x7FFF) << 24 | (paramInt1 < 0 ? Integer.MIN_VALUE : 0) | (paramInt3 < 0 ? 32768 : 0);
  }
  
  public float a(PathPoint paramPathPoint) {
    float f1 = paramPathPoint.a - this.a;
    float f2 = paramPathPoint.b - this.b;
    float f3 = paramPathPoint.c - this.c;
    return MathHelper.c(f1 * f1 + f2 * f2 + f3 * f3);
  }
  
  public boolean equals(Object paramObject) {
    if ((paramObject instanceof PathPoint)) {
      PathPoint localPathPoint = (PathPoint)paramObject;
      return (this.j == localPathPoint.j) && (this.a == localPathPoint.a) && (this.b == localPathPoint.b) && (this.c == localPathPoint.c);
    }
    return false;
  }
  
  public int hashCode() {
    return this.j;
  }
  
  public boolean a() {
    return this.d >= 0;
  }
  
  public String toString() {
    return this.a + ", " + this.b + ", " + this.c;
  }
}
