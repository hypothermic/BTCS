package net.minecraft.server;

public class GenLayerMushroomShore extends GenLayer
{
  public GenLayerMushroomShore(long paramLong, GenLayer paramGenLayer)
  {
    super(paramLong);
    this.a = paramGenLayer;
  }
  
  public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
    
    int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
    for (int i = 0; i < paramInt4; i++) {
      for (int j = 0; j < paramInt3; j++) {
        a(j + paramInt1, i + paramInt2);
        int k = arrayOfInt1[(j + 1 + (i + 1) * (paramInt3 + 2))];
        int m; int n; int i1; int i2; if (k == BiomeBase.MUSHROOM_ISLAND.id) {
          m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
          n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
          i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
          i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
          if ((m == BiomeBase.OCEAN.id) || (n == BiomeBase.OCEAN.id) || (i1 == BiomeBase.OCEAN.id) || (i2 == BiomeBase.OCEAN.id)) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MUSHROOM_SHORE.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        } else if ((k != BiomeBase.OCEAN.id) && (k != BiomeBase.RIVER.id) && (k != BiomeBase.SWAMPLAND.id) && (k != BiomeBase.EXTREME_HILLS.id)) {
          m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
          n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
          i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
          i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
          if ((m == BiomeBase.OCEAN.id) || (n == BiomeBase.OCEAN.id) || (i1 == BiomeBase.OCEAN.id) || (i2 == BiomeBase.OCEAN.id)) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.BEACH.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        } else if (k == BiomeBase.EXTREME_HILLS.id) {
          m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
          n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
          i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
          i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
          if ((m != BiomeBase.EXTREME_HILLS.id) || (n != BiomeBase.EXTREME_HILLS.id) || (i1 != BiomeBase.EXTREME_HILLS.id) || (i2 != BiomeBase.EXTREME_HILLS.id)) {
            arrayOfInt2[(j + i * paramInt3)] = BiomeBase.SMALL_MOUNTAINS.id;
          } else {
            arrayOfInt2[(j + i * paramInt3)] = k;
          }
        } else {
          arrayOfInt2[(j + i * paramInt3)] = k;
        }
      }
    }
    

    return arrayOfInt2;
  }
}
