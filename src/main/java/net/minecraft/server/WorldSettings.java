package net.minecraft.server;


public final class WorldSettings
{
  private final long a;
  
  private final int b;
  
  private final boolean c;
  
  private final boolean d;
  private final WorldType e;
  
  public WorldSettings(long paramLong, int paramInt, boolean paramBoolean1, boolean paramBoolean2, WorldType paramWorldType)
  {
    this.a = paramLong;
    this.b = paramInt;
    this.c = paramBoolean1;
    this.d = paramBoolean2;
    this.e = paramWorldType;
  }
  
  public long a() {
    return this.a;
  }
  
  public int b() {
    return this.b;
  }
  
  public boolean c() {
    return this.d;
  }
  
  public boolean d() {
    return this.c;
  }
  
  public WorldType e() {
    return this.e;
  }
  
  public static int a(int paramInt) {
    switch (paramInt) {
    case 0: 
    case 1: 
      return paramInt;
    }
    return 0;
  }
}
