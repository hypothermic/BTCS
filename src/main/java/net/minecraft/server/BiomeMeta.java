package net.minecraft.server;










































public class BiomeMeta
  extends WeightedRandomChoice
{
  public Class a;
  








































  public int b;
  







































  public int c;
  








































  public BiomeMeta(Class paramClass, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInt1);
    this.a = paramClass;
    this.b = paramInt2;
    this.c = paramInt3;
  }
}