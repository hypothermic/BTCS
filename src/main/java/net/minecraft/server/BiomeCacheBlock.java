package net.minecraft.server;

public class BiomeCacheBlock
{
  public float[] a = new float['Ā'];
  public float[] b = new float['Ā'];
  public BiomeBase[] c = new BiomeBase['Ā'];
  public int d;
  public int e;
  public long f;
  
  public BiomeCacheBlock(BiomeCache paramBiomeCache, int paramInt1, int paramInt2) {
    this.d = paramInt1;
    this.e = paramInt2;
    // BTCS start
    /*BiomeCache.a(paramBiomeCache).getTemperatures(this.a, paramInt1 << 4, paramInt2 << 4, 16, 16);
    BiomeCache.a(paramBiomeCache).getWetness(this.b, paramInt1 << 4, paramInt2 << 4, 16, 16);
    BiomeCache.a(paramBiomeCache).a(this.c, paramInt1 << 4, paramInt2 << 4, 16, 16, false);*/
    BiomeCache.getChunkManager(paramBiomeCache).getTemperatures(this.a, paramInt1 << 4, paramInt2 << 4, 16, 16);
    BiomeCache.getChunkManager(paramBiomeCache).getWetness(this.b, paramInt1 << 4, paramInt2 << 4, 16, 16);
    BiomeCache.getChunkManager(paramBiomeCache).a(this.c, paramInt1 << 4, paramInt2 << 4, 16, 16, false);
    // BTCS end
  }
  
  public BiomeBase a(int paramInt1, int paramInt2) {
    return this.c[(paramInt1 & 0xF | (paramInt2 & 0xF) << 4)];
  }
}
