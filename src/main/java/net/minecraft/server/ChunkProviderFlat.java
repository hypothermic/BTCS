package net.minecraft.server;

import java.util.List;
import java.util.Random;












public class ChunkProviderFlat
  implements IChunkProvider
{
  private World a;
  private Random b;
  private final boolean c;
  private WorldGenVillage d = new WorldGenVillage(1);
  
  public ChunkProviderFlat(World paramWorld, long paramLong, boolean paramBoolean) {
    this.a = paramWorld;
    this.c = paramBoolean;
    this.b = new Random(paramLong);
  }
  
  private void a(byte[] paramArrayOfByte) {
    int i = paramArrayOfByte.length / 256;
    
    for (int j = 0; j < 16; j++) {
      for (int k = 0; k < 16; k++) {
        for (int m = 0; m < i; m++) {
          int n = 0;
          if (m == 0) {
            n = Block.BEDROCK.id;
          } else if (m <= 2) {
            n = Block.DIRT.id;
          } else if (m == 3) {
            n = Block.GRASS.id;
          }
          paramArrayOfByte[(j << 11 | k << 7 | m)] = ((byte)n);
        }
      }
    }
  }
  
  public Chunk getChunkAt(int paramInt1, int paramInt2) {
    return getOrCreateChunk(paramInt1, paramInt2);
  }
  
  public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte1 = new byte[32768];
    a(arrayOfByte1);
    
    Chunk localChunk = new Chunk(this.a, arrayOfByte1, paramInt1, paramInt2);
    


    if (this.c) {
      this.d.a(this, this.a, paramInt1, paramInt2, arrayOfByte1);
    }
    
    BiomeBase[] arrayOfBiomeBase = this.a.getWorldChunkManager().getBiomeBlock(null, paramInt1 * 16, paramInt2 * 16, 16, 16);
    byte[] arrayOfByte2 = localChunk.l();
    
    for (int i = 0; i < arrayOfByte2.length; i++) {
      arrayOfByte2[i] = ((byte)arrayOfBiomeBase[i].id);
    }
    
    localChunk.initLighting();
    
    return localChunk;
  }
  
  public boolean isChunkLoaded(int paramInt1, int paramInt2)
  {
    return true;
  }
  
  public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
  {
    this.b.setSeed(this.a.getSeed());
    long l1 = this.b.nextLong() / 2L * 2L + 1L;
    long l2 = this.b.nextLong() / 2L * 2L + 1L;
    this.b.setSeed(paramInt1 * l1 + paramInt2 * l2 ^ this.a.getSeed());
    
    if (this.c) {
      this.d.a(this.a, this.b, paramInt1, paramInt2);
    }
  }
  
  public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate) {
    return true;
  }
  
  public boolean unloadChunks() {
    return false;
  }
  
  public boolean canSave() {
    return true;
  }
  



  public List getMobsFor(EnumCreatureType paramEnumCreatureType, int paramInt1, int paramInt2, int paramInt3)
  {
    BiomeBase localBiomeBase = this.a.getBiome(paramInt1, paramInt3);
    if (localBiomeBase == null) {
      return null;
    }
    return localBiomeBase.getMobs(paramEnumCreatureType);
  }
  
  public ChunkPosition findNearestMapFeature(World paramWorld, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    return null;
  }
}
