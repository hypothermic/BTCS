package net.minecraft.server;


public class GenLayerRiverMix
  extends GenLayer
{
  private GenLayer b;
  
  private GenLayer c;
  
  public GenLayerRiverMix(long paramLong, GenLayer paramGenLayer1, GenLayer paramGenLayer2)
  {
    super(paramLong);
    this.b = paramGenLayer1;
    this.c = paramGenLayer2;
  }
  
  public void a(long paramLong) {
    this.b.a(paramLong);
    this.c.a(paramLong);
    super.a(paramLong);
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt1 = this.b.a(paramInt1, paramInt2, paramInt3, paramInt4);
    int[] arrayOfInt2 = this.c.a(paramInt1, paramInt2, paramInt3, paramInt4);
    
    int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
    for (int i = 0; i < paramInt3 * paramInt4; i++) {
      if (arrayOfInt1[i] == BiomeBase.OCEAN.id) {
        arrayOfInt3[i] = arrayOfInt1[i];
      }
      else if (arrayOfInt2[i] >= 0) {
        if (arrayOfInt1[i] == BiomeBase.ICE_PLAINS.id) { arrayOfInt3[i] = BiomeBase.FROZEN_RIVER.id;
        } else if ((arrayOfInt1[i] == BiomeBase.MUSHROOM_ISLAND.id) || (arrayOfInt1[i] == BiomeBase.MUSHROOM_SHORE.id)) arrayOfInt3[i] = BiomeBase.MUSHROOM_SHORE.id; else
          arrayOfInt3[i] = arrayOfInt2[i];
      } else {
        arrayOfInt3[i] = arrayOfInt1[i];
      }
    }
    

    return arrayOfInt3;
  }
}
