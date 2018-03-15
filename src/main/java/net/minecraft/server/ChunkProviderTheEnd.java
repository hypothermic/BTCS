package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class ChunkProviderTheEnd
  implements IChunkProvider
{
  private Random i;
  private NoiseGeneratorOctaves j;
  private NoiseGeneratorOctaves k;
  private NoiseGeneratorOctaves l;
  public NoiseGeneratorOctaves a;
  public NoiseGeneratorOctaves b;
  private World m;
  private double[] n;
  private BiomeBase[] o;
  double[] c;
  double[] d;
  double[] e;
  double[] f;
  double[] g;
  
  public ChunkProviderTheEnd(World paramWorld, long paramLong)
  {
    this.m = paramWorld;
    
    this.i = new Random(paramLong);
    this.j = new NoiseGeneratorOctaves(this.i, 16);
    this.k = new NoiseGeneratorOctaves(this.i, 16);
    this.l = new NoiseGeneratorOctaves(this.i, 8);
    
    this.a = new NoiseGeneratorOctaves(this.i, 10);
    this.b = new NoiseGeneratorOctaves(this.i, 16);
  }
  


  public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, BiomeBase[] paramArrayOfBiomeBase)
  {
    int i1 = 2;
    
    int i2 = i1 + 1;
    int i3 = 33;
    int i4 = i1 + 1;
    this.n = a(this.n, paramInt1 * i1, 0, paramInt2 * i1, i2, i3, i4);
    
    for (int i5 = 0; i5 < i1; i5++) {
      for (int i6 = 0; i6 < i1; i6++) {
        for (int i7 = 0; i7 < 32; i7++) {
          double d1 = 0.25D;
          double d2 = this.n[(((i5 + 0) * i4 + (i6 + 0)) * i3 + (i7 + 0))];
          double d3 = this.n[(((i5 + 0) * i4 + (i6 + 1)) * i3 + (i7 + 0))];
          double d4 = this.n[(((i5 + 1) * i4 + (i6 + 0)) * i3 + (i7 + 0))];
          double d5 = this.n[(((i5 + 1) * i4 + (i6 + 1)) * i3 + (i7 + 0))];
          
          double d6 = (this.n[(((i5 + 0) * i4 + (i6 + 0)) * i3 + (i7 + 1))] - d2) * d1;
          double d7 = (this.n[(((i5 + 0) * i4 + (i6 + 1)) * i3 + (i7 + 1))] - d3) * d1;
          double d8 = (this.n[(((i5 + 1) * i4 + (i6 + 0)) * i3 + (i7 + 1))] - d4) * d1;
          double d9 = (this.n[(((i5 + 1) * i4 + (i6 + 1)) * i3 + (i7 + 1))] - d5) * d1;
          
          for (int i8 = 0; i8 < 4; i8++) {
            double d10 = 0.125D;
            
            double d11 = d2;
            double d12 = d3;
            double d13 = (d4 - d2) * d10;
            double d14 = (d5 - d3) * d10;
            
            for (int i9 = 0; i9 < 8; i9++) {
              int i10 = i9 + i5 * 8 << 11 | 0 + i6 * 8 << 7 | i7 * 4 + i8;
              int i11 = 128;
              double d15 = 0.125D;
              
              double d16 = d11;
              double d17 = (d12 - d11) * d15;
              for (int i12 = 0; i12 < 8; i12++) {
                int i13 = 0;
                if (d16 > 0.0D) {
                  i13 = Block.WHITESTONE.id;
                }
                

                paramArrayOfByte[i10] = ((byte)i13);
                i10 += i11;
                d16 += d17;
              }
              d11 += d13;
              d12 += d14;
            }
            
            d2 += d6;
            d3 += d7;
            d4 += d8;
            d5 += d9;
          }
        }
      }
    }
  }
  
  public void b(int paramInt1, int paramInt2, byte[] paramArrayOfByte, BiomeBase[] paramArrayOfBiomeBase) {
    for (int i1 = 0; i1 < 16; i1++) {
      for (int i2 = 0; i2 < 16; i2++) {
        int i3 = 1;
        int i4 = -1;
        
        int i5 = (byte)Block.WHITESTONE.id;
        int i6 = (byte)Block.WHITESTONE.id;
        
        for (int i7 = 127; i7 >= 0; i7--) {
          int i8 = (i2 * 16 + i1) * 128 + i7;
          
          int i9 = paramArrayOfByte[i8];
          
          if (i9 == 0) {
            i4 = -1;
          } else if (i9 == Block.STONE.id) {
            if (i4 == -1) {
              if (i3 <= 0) {
                i5 = 0;
                i6 = (byte)Block.WHITESTONE.id;
              }
              







              i4 = i3;
              if (i7 >= 0) paramArrayOfByte[i8] = (byte) i5; else // BTCS: added cast (byte)
                paramArrayOfByte[i8] = (byte) i6; // BTCS: added cast (byte)
            } else if (i4 > 0) {
              i4--;
              paramArrayOfByte[i8] = (byte) i6; // BTCS: added cast (byte)
            }
          }
        }
      }
    }
  }
  

  public Chunk getChunkAt(int paramInt1, int paramInt2)
  {
    return getOrCreateChunk(paramInt1, paramInt2);
  }
  
  public Chunk getOrCreateChunk(int paramInt1, int paramInt2) {
    this.i.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L);
    
    byte[] arrayOfByte1 = new byte[32768];
    this.o = this.m.getWorldChunkManager().getBiomeBlock(this.o, paramInt1 * 16, paramInt2 * 16, 16, 16);
    
    a(paramInt1, paramInt2, arrayOfByte1, this.o);
    b(paramInt1, paramInt2, arrayOfByte1, this.o);
    
    Chunk localChunk = new Chunk(this.m, arrayOfByte1, paramInt1, paramInt2);
    byte[] arrayOfByte2 = localChunk.l();
    
    for (int i1 = 0; i1 < arrayOfByte2.length; i1++) {
      arrayOfByte2[i1] = ((byte)this.o[i1].id);
    }
    
    localChunk.initLighting();
    
    return localChunk;
  }
  


  private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (paramArrayOfDouble == null) {
      paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];
    }
    
    double d1 = 684.412D;
    double d2 = 684.412D;
    



    this.f = this.a.a(this.f, paramInt1, paramInt3, paramInt4, paramInt6, 1.121D, 1.121D, 0.5D);
    this.g = this.b.a(this.g, paramInt1, paramInt3, paramInt4, paramInt6, 200.0D, 200.0D, 0.5D);
    
    d1 *= 2.0D;
    

    this.c = this.l.a(this.c, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 160.0D, d1 / 80.0D);
    this.d = this.j.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
    this.e = this.k.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
    
    int i1 = 0;
    int i2 = 0;
    
    for (int i3 = 0; i3 < paramInt4; i3++) {
      for (int i4 = 0; i4 < paramInt6; i4++) {
        double d3 = (this.f[i2] + 256.0D) / 512.0D;
        if (d3 > 1.0D) { d3 = 1.0D;
        }
        
        double d4 = this.g[i2] / 8000.0D;
        if (d4 < 0.0D) d4 = -d4 * 0.3D;
        d4 = d4 * 3.0D - 2.0D;
        
        float f1 = (i3 + paramInt1 - 0) / 1.0F;
        float f2 = (i4 + paramInt3 - 0) / 1.0F;
        float f3 = 100.0F - MathHelper.c(f1 * f1 + f2 * f2) * 8.0F;
        if (f3 > 80.0F) f3 = 80.0F;
        if (f3 < -100.0F) f3 = -100.0F;
        if (d4 > 1.0D) d4 = 1.0D;
        d4 /= 8.0D;
        d4 = 0.0D;
        
        if (d3 < 0.0D) d3 = 0.0D;
        d3 += 0.5D;
        d4 = d4 * paramInt5 / 16.0D;
        
        i2++;
        
        double d5 = paramInt5 / 2.0D;
        

        for (int i5 = 0; i5 < paramInt5; i5++) {
          double d6 = 0.0D;
          

          double d7 = (i5 - d5) * 8.0D / d3;
          
          if (d7 < 0.0D) { d7 *= -1.0D;
          }
          double d8 = this.d[i1] / 512.0D;
          double d9 = this.e[i1] / 512.0D;
          
          double d10 = (this.c[i1] / 10.0D + 1.0D) / 2.0D;
          if (d10 < 0.0D) { d6 = d8;
          } else if (d10 > 1.0D) d6 = d9; else
            d6 = d8 + (d9 - d8) * d10;
          d6 -= 8.0D;
          



          d6 += f3;
          
          int i6 = 2;
          double d11; if (i5 > paramInt5 / 2 - i6) {
            d11 = (i5 - (paramInt5 / 2 - i6)) / 64.0F;
            if (d11 < 0.0D) d11 = 0.0D;
            if (d11 > 1.0D) d11 = 1.0D;
            d6 = d6 * (1.0D - d11) + -3000.0D * d11;
          }
          i6 = 8;
          if (i5 < i6) {
            d11 = (i6 - i5) / (i6 - 1.0F);
            d6 = d6 * (1.0D - d11) + -30.0D * d11;
          }
          

          paramArrayOfDouble[i1] = d6;
          i1++;
        }
      }
    }
    return paramArrayOfDouble;
  }
  
  public boolean isChunkLoaded(int paramInt1, int paramInt2) {
    return true;
  }
  
  int[][] h = new int[32][32];
  













































  public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
  {
    BlockSand.instaFall = true;
    int i1 = paramInt1 * 16;
    int i2 = paramInt2 * 16;
    
    BiomeBase localBiomeBase = this.m.getBiome(i1 + 16, i2 + 16);
    localBiomeBase.a(this.m, this.m.random, i1, i2);
    
    BlockSand.instaFall = false;
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
    BiomeBase localBiomeBase = this.m.getBiome(paramInt1, paramInt3);
    if (localBiomeBase == null) {
      return null;
    }
    return localBiomeBase.getMobs(paramEnumCreatureType);
  }
  
  public ChunkPosition findNearestMapFeature(World paramWorld, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    return null;
  }
}
