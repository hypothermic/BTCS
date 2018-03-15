package net.minecraft.server;



class WorldGenNetherPieceWeight
{
  public Class a;
  

  public final int b;
  

  public int c;
  

  public int d;
  

  public boolean e;
  


  public WorldGenNetherPieceWeight(Class paramClass, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.a = paramClass;
    this.b = paramInt1;
    this.d = paramInt2;
    this.e = paramBoolean;
  }
  
  public WorldGenNetherPieceWeight(Class paramClass, int paramInt1, int paramInt2) {
    this(paramClass, paramInt1, paramInt2, false);
  }
  
  public boolean a(int paramInt) {
    return (this.d == 0) || (this.c < this.d);
  }
  
  public boolean a() {
    return (this.d == 0) || (this.c < this.d);
  }
}
