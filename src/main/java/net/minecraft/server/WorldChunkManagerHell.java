package net.minecraft.server;

import java.util.List;

public class WorldChunkManagerHell extends WorldChunkManager
{
  private BiomeBase a;
  private float b;
  private float c;
  
  public WorldChunkManagerHell(BiomeBase paramBiomeBase, float paramFloat1, float paramFloat2) {
    this.a = paramBiomeBase;
    this.b = paramFloat1;
    this.c = paramFloat2;
  }
  



  public BiomeBase getBiome(int paramInt1, int paramInt2)
  {
    return this.a;
  }
  
















  public BiomeBase[] getBiomes(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramArrayOfBiomeBase == null) || (paramArrayOfBiomeBase.length < paramInt3 * paramInt4)) {
      paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
    }
    
    java.util.Arrays.fill(paramArrayOfBiomeBase, 0, paramInt3 * paramInt4, this.a);
    
    return paramArrayOfBiomeBase;
  }
  
  public float[] getTemperatures(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length < paramInt3 * paramInt4)) {
      paramArrayOfFloat = new float[paramInt3 * paramInt4];
    }
    
    java.util.Arrays.fill(paramArrayOfFloat, 0, paramInt3 * paramInt4, this.b);
    return paramArrayOfFloat;
  }
  














  public float[] getWetness(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length < paramInt3 * paramInt4)) {
      paramArrayOfFloat = new float[paramInt3 * paramInt4];
    }
    java.util.Arrays.fill(paramArrayOfFloat, 0, paramInt3 * paramInt4, this.c);
    
    return paramArrayOfFloat;
  }
  



















  public BiomeBase[] getBiomeBlock(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramArrayOfBiomeBase == null) || (paramArrayOfBiomeBase.length < paramInt3 * paramInt4)) {
      paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
    }
    
    java.util.Arrays.fill(paramArrayOfBiomeBase, 0, paramInt3 * paramInt4, this.a);
    
    return paramArrayOfBiomeBase;
  }
  
  public BiomeBase[] a(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    return getBiomeBlock(paramArrayOfBiomeBase, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  










  public ChunkPosition a(int paramInt1, int paramInt2, int paramInt3, List paramList, java.util.Random paramRandom)
  {
    if (paramList.contains(this.a)) {
      return new ChunkPosition(paramInt1 - paramInt3 + paramRandom.nextInt(paramInt3 * 2 + 1), 0, paramInt2 - paramInt3 + paramRandom.nextInt(paramInt3 * 2 + 1));
    }
    
    return null;
  }
  





  public boolean a(int paramInt1, int paramInt2, int paramInt3, List paramList)
  {
    return paramList.contains(this.a);
  }
}
