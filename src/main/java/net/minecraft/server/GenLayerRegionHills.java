package net.minecraft.server;

public class GenLayerRegionHills extends GenLayer
{
  public GenLayerRegionHills(long paramLong, GenLayer paramGenLayer)
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
        if (a(3) == 0) {
          int m = k;
          if (k == BiomeBase.DESERT.id) {
            m = BiomeBase.DESERT_HILLS.id;
          } else if (k == BiomeBase.FOREST.id) {
            m = BiomeBase.FOREST_HILLS.id;
          } else if (k == BiomeBase.TAIGA.id) {
            m = BiomeBase.TAIGA_HILLS.id;
          } else if (k == BiomeBase.PLAINS.id) {
            m = BiomeBase.FOREST.id;
          } else if (k == BiomeBase.ICE_PLAINS.id) {
            m = BiomeBase.ICE_MOUNTAINS.id;
          } else if (k == BiomeBase.JUNGLE.id) {
            m = BiomeBase.JUNGLE_HILLS.id;
          }
          if (m != k) {
            int n = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
            int i1 = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
            int i2 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
            int i3 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
            if ((n == k) && (i1 == k) && (i2 == k) && (i3 == k)) {
              arrayOfInt2[(j + i * paramInt3)] = m;
            } else {
              arrayOfInt2[(j + i * paramInt3)] = k;
            }
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
