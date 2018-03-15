package net.minecraft.server;

public class VillageDoor { public final int locX;
  public final int locY; public final int locZ; public final int d;
  public VillageDoor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { this.locX = paramInt1;
    this.locY = paramInt2;
    this.locZ = paramInt3;
    this.d = paramInt4;
    this.e = paramInt5;
    this.addedTime = paramInt6;
  }
  

  public final int e;
  public int addedTime;
  public boolean g = false;
  


  private int h = 0;
  



  public int a(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 - this.locX;
    int j = paramInt2 - this.locY;
    int k = paramInt3 - this.locZ;
    return i * i + j * j + k * k;
  }
  
  public int b(int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt1 - this.locX - this.d;
    int j = paramInt2 - this.locY;
    int k = paramInt3 - this.locZ - this.e;
    return i * i + j * j + k * k;
  }
  
  public int getIndoorsX() {
    return this.locX + this.d;
  }
  
  public int getIndoorsY() {
    return this.locY;
  }
  
  public int getIndoorsZ() {
    return this.locZ + this.e;
  }
  
  public boolean a(int paramInt1, int paramInt2) {
    int i = paramInt1 - this.locX;
    int j = paramInt2 - this.locZ;
    return i * this.d + j * this.e >= 0;
  }
  
  public void d() {
    this.h = 0;
  }
  
  public void e() {
    this.h += 1;
  }
  
  public int f() {
    return this.h;
  }
}
