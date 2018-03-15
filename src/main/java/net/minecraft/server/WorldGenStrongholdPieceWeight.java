package net.minecraft.server;





class WorldGenStrongholdPieceWeight
{
  public Class a;
  



  public final int b;
  



  public int c;
  



  public int d;
  



  public WorldGenStrongholdPieceWeight(Class paramClass, int paramInt1, int paramInt2)
  {
    this.a = paramClass;
    this.b = paramInt1;
    this.d = paramInt2;
  }
  
  public boolean a(int paramInt) {
    return (this.d == 0) || (this.c < this.d);
  }
  
  public boolean a() {
    return (this.d == 0) || (this.c < this.d);
  }
}
