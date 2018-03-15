package net.minecraft.server;


public class PathEntity
{
  private final PathPoint[] a;
  private int b;
  private int c;
  
  public PathEntity(PathPoint[] paramArrayOfPathPoint)
  {
    this.a = paramArrayOfPathPoint;
    this.c = paramArrayOfPathPoint.length;
  }
  
  public void a() {
    this.b += 1;
  }
  
  public boolean b() {
    return this.b >= this.c;
  }
  
  public PathPoint c() {
    if (this.c > 0) {
      return this.a[(this.c - 1)];
    }
    return null;
  }
  
  public PathPoint a(int paramInt) {
    return this.a[paramInt];
  }
  
  public int d() {
    return this.c;
  }
  
  public void b(int paramInt) {
    this.c = paramInt;
  }
  
  public int e() {
    return this.b;
  }
  
  public void c(int paramInt) {
    this.b = paramInt;
  }
  
  public Vec3D a(Entity paramEntity, int paramInt) {
    double d1 = this.a[paramInt].a + (int)(paramEntity.width + 1.0F) * 0.5D;
    double d2 = this.a[paramInt].b;
    double d3 = this.a[paramInt].c + (int)(paramEntity.width + 1.0F) * 0.5D;
    return Vec3D.create(d1, d2, d3);
  }
  
  public Vec3D a(Entity paramEntity) {
    return a(paramEntity, this.b);
  }
  
  public boolean a(PathEntity paramPathEntity) {
    if (paramPathEntity == null) return false;
    if (paramPathEntity.a.length != this.a.length) return false;
    for (int i = 0; i < this.a.length; i++)
      if ((this.a[i].a != paramPathEntity.a[i].a) || (this.a[i].b != paramPathEntity.a[i].b) || (this.a[i].c != paramPathEntity.a[i].c)) return false;
    return true;
  }
  





  public boolean a(Vec3D paramVec3D)
  {
    PathPoint localPathPoint = c();
    if (localPathPoint == null) return false;
    return (localPathPoint.a == (int)paramVec3D.a) && (localPathPoint.c == (int)paramVec3D.c);
  }
}
