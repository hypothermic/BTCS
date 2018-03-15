package net.minecraft.server;






















































public class WatchableObject
{
  private final int a;
  



















































  private final int b;
  



















































  private Object c;
  



















































  private boolean d;
  




















































  public WatchableObject(int paramInt1, int paramInt2, Object paramObject)
  {
    this.b = paramInt2;
    this.c = paramObject;
    this.a = paramInt1;
    this.d = true;
  }
  
  public int a() {
    return this.b;
  }
  
  public void a(Object paramObject) {
    this.c = paramObject;
  }
  
  public Object b() {
    return this.c;
  }
  
  public int c() {
    return this.a;
  }
  
  public boolean d() {
    return this.d;
  }
  
  public void a(boolean paramBoolean) {
    this.d = paramBoolean;
  }
}
