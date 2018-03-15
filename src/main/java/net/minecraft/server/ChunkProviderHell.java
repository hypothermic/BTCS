package net.minecraft.server;

import java.util.List;
import java.util.Random;













public class ChunkProviderHell
  implements IChunkProvider
{
  private Random i;
  private NoiseGeneratorOctaves j;
  private NoiseGeneratorOctaves k;
  private NoiseGeneratorOctaves l;
  private NoiseGeneratorOctaves m;
  private NoiseGeneratorOctaves n;
  public NoiseGeneratorOctaves a;
  public NoiseGeneratorOctaves b;
  private World o;
  private double[] p;
  
  public ChunkProviderHell(World paramWorld, long paramLong)
  {
    this.o = paramWorld;
    
    this.i = new Random(paramLong);
    this.j = new NoiseGeneratorOctaves(this.i, 16);
    this.k = new NoiseGeneratorOctaves(this.i, 16);
    this.l = new NoiseGeneratorOctaves(this.i, 8);
    this.m = new NoiseGeneratorOctaves(this.i, 4);
    this.n = new NoiseGeneratorOctaves(this.i, 4);
    
    this.a = new NoiseGeneratorOctaves(this.i, 10);
    this.b = new NoiseGeneratorOctaves(this.i, 16);
  }
  








  public WorldGenNether c = new WorldGenNether();
  
  public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte) {
    int i1 = 4;
    int i2 = 32;
    
    int i3 = i1 + 1;
    int i4 = 17;
    int i5 = i1 + 1;
    this.p = a(this.p, paramInt1 * i1, 0, paramInt2 * i1, i3, i4, i5);
    
    for (int i6 = 0; i6 < i1; i6++) {
      for (int i7 = 0; i7 < i1; i7++) {
        for (int i8 = 0; i8 < 16; i8++) {
          double d1 = 0.125D;
          double d2 = this.p[(((i6 + 0) * i5 + (i7 + 0)) * i4 + (i8 + 0))];
          double d3 = this.p[(((i6 + 0) * i5 + (i7 + 1)) * i4 + (i8 + 0))];
          double d4 = this.p[(((i6 + 1) * i5 + (i7 + 0)) * i4 + (i8 + 0))];
          double d5 = this.p[(((i6 + 1) * i5 + (i7 + 1)) * i4 + (i8 + 0))];
          
          double d6 = (this.p[(((i6 + 0) * i5 + (i7 + 0)) * i4 + (i8 + 1))] - d2) * d1;
          double d7 = (this.p[(((i6 + 0) * i5 + (i7 + 1)) * i4 + (i8 + 1))] - d3) * d1;
          double d8 = (this.p[(((i6 + 1) * i5 + (i7 + 0)) * i4 + (i8 + 1))] - d4) * d1;
          double d9 = (this.p[(((i6 + 1) * i5 + (i7 + 1)) * i4 + (i8 + 1))] - d5) * d1;
          
          for (int i9 = 0; i9 < 8; i9++) {
            double d10 = 0.25D;
            
            double d11 = d2;
            double d12 = d3;
            double d13 = (d4 - d2) * d10;
            double d14 = (d5 - d3) * d10;
            
            for (int i10 = 0; i10 < 4; i10++) {
              int i11 = i10 + i6 * 4 << 11 | 0 + i7 * 4 << 7 | i8 * 8 + i9;
              int i12 = 128;
              double d15 = 0.25D;
              
              double d16 = d11;
              double d17 = (d12 - d11) * d15;
              for (int i13 = 0; i13 < 4; i13++) {
                int i14 = 0;
                if (i8 * 8 + i9 < i2) {
                  i14 = Block.STATIONARY_LAVA.id;
                }
                if (d16 > 0.0D) {
                  i14 = Block.NETHERRACK.id;
                }
                

                paramArrayOfByte[i11] = ((byte)i14);
                i11 += i12;
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
  
  private double[] q = new double['Ā'];
  private double[] r = new double['Ā'];
  private double[] s = new double['Ā'];
  
  public void b(int paramInt1, int paramInt2, byte[] paramArrayOfByte) {
    int i1 = 64;
    
    double d1 = 0.03125D;
    this.q = this.m.a(this.q, paramInt1 * 16, paramInt2 * 16, 0, 16, 16, 1, d1, d1, 1.0D);
    this.r = this.m.a(this.r, paramInt1 * 16, 109, paramInt2 * 16, 16, 1, 16, d1, 1.0D, d1);
    this.s = this.n.a(this.s, paramInt1 * 16, paramInt2 * 16, 0, 16, 16, 1, d1 * 2.0D, d1 * 2.0D, d1 * 2.0D);
    
    for (int i2 = 0; i2 < 16; i2++) {
      for (int i3 = 0; i3 < 16; i3++) {
        int i4 = this.q[(i2 + i3 * 16)] + this.i.nextDouble() * 0.2D > 0.0D ? 1 : 0;
        int i5 = this.r[(i2 + i3 * 16)] + this.i.nextDouble() * 0.2D > 0.0D ? 1 : 0;
        int i6 = (int)(this.s[(i2 + i3 * 16)] / 3.0D + 3.0D + this.i.nextDouble() * 0.25D);
        
        int i7 = -1;
        
        int i8 = (byte)Block.NETHERRACK.id;
        int i9 = (byte)Block.NETHERRACK.id;
        
        for (int i10 = 127; i10 >= 0; i10--) {
          int i11 = (i3 * 16 + i2) * 128 + i10;
          
          if (i10 >= 127 - this.i.nextInt(5)) {
            paramArrayOfByte[i11] = ((byte)Block.BEDROCK.id);
          } else if (i10 <= 0 + this.i.nextInt(5)) {
            paramArrayOfByte[i11] = ((byte)Block.BEDROCK.id);
          } else {
            int i12 = paramArrayOfByte[i11];
            
            if (i12 == 0) {
              i7 = -1;
            } else if (i12 == Block.NETHERRACK.id) {
              if (i7 == -1) {
                if (i6 <= 0) {
                  i8 = 0;
                  i9 = (byte)Block.NETHERRACK.id;
                } else if ((i10 >= i1 - 4) && (i10 <= i1 + 1)) {
                  i8 = (byte)Block.NETHERRACK.id;
                  i9 = (byte)Block.NETHERRACK.id;
                  if (i5 != 0) i8 = (byte)Block.GRAVEL.id;
                  if (i5 != 0) i9 = (byte)Block.NETHERRACK.id;
                  if (i4 != 0) i8 = (byte)Block.SOUL_SAND.id;
                  if (i4 != 0) { i9 = (byte)Block.SOUL_SAND.id;
                  }
                }
                if ((i10 < i1) && (i8 == 0)) { i8 = (byte)Block.STATIONARY_LAVA.id;
                }
                i7 = i6;
                if (i10 >= i1 - 1) paramArrayOfByte[i11] = (byte) i8; else // BTCS: added cast (byte)
                  paramArrayOfByte[i11] = (byte) i9; // BTCS: added cast (byte)
              } else if (i7 > 0) {
                i7--;
                paramArrayOfByte[i11] = (byte) i9; // BTCS: added cast (byte)
              }
            }
          }
        }
      }
    }
  }
  
  private WorldGenBase t = new WorldGenCavesHell();
  double[] d;
  
  public Chunk getChunkAt(int paramInt1, int paramInt2) { return getOrCreateChunk(paramInt1, paramInt2); }
  
  public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
  {
    this.i.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L);
    
    byte[] arrayOfByte1 = new byte[32768];
    
    a(paramInt1, paramInt2, arrayOfByte1);
    b(paramInt1, paramInt2, arrayOfByte1);
    
    this.t.a(this, this.o, paramInt1, paramInt2, arrayOfByte1);
    this.c.a(this, this.o, paramInt1, paramInt2, arrayOfByte1);
    
    Chunk localChunk = new Chunk(this.o, arrayOfByte1, paramInt1, paramInt2);
    BiomeBase[] arrayOfBiomeBase = this.o.getWorldChunkManager().getBiomeBlock(null, paramInt1 * 16, paramInt2 * 16, 16, 16);
    byte[] arrayOfByte2 = localChunk.l();
    
    for (int i1 = 0; i1 < arrayOfByte2.length; i1++) {
      arrayOfByte2[i1] = ((byte)arrayOfBiomeBase[i1].id);
    }
    
    localChunk.m();
    



    return localChunk;
  }
  

  double[] e;
  private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (paramArrayOfDouble == null) {
      paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];
    }
    
    double d1 = 684.412D;
    double d2 = 2053.236D;
    

    this.g = this.a.a(this.g, paramInt1, paramInt2, paramInt3, paramInt4, 1, paramInt6, 1.0D, 0.0D, 1.0D);
    this.h = this.b.a(this.h, paramInt1, paramInt2, paramInt3, paramInt4, 1, paramInt6, 100.0D, 0.0D, 100.0D);
    
    this.d = this.l.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 60.0D, d1 / 80.0D);
    this.e = this.j.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
    this.f = this.k.a(this.f, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
    
    int i1 = 0;
    int i2 = 0;
    double[] arrayOfDouble = new double[paramInt5];
    for (int i3 = 0; i3 < paramInt5; i3++) {
      arrayOfDouble[i3] = (Math.cos(i3 * 3.141592653589793D * 6.0D / paramInt5) * 2.0D);
      
      double d3 = i3;
      if (i3 > paramInt5 / 2) {
        d3 = paramInt5 - 1 - i3;
      }
      if (d3 < 4.0D) {
        d3 = 4.0D - d3;
        arrayOfDouble[i3] -= d3 * d3 * d3 * 10.0D;
      }
    }
    
    int i3; // BTCS
    for (i3 = 0; i3 < paramInt4; i3++)
    {
      for (int i4 = 0; i4 < paramInt6; i4++)
      {
        double d4 = (this.g[i2] + 256.0D) / 512.0D;
        if (d4 > 1.0D) { d4 = 1.0D;
        }
        double d5 = 0.0D;
        
        double d6 = this.h[i2] / 8000.0D;
        if (d6 < 0.0D) { d6 = -d6;
        }
        
        d6 = d6 * 3.0D - 3.0D;
        

        if (d6 < 0.0D) {
          d6 /= 2.0D;
          if (d6 < -1.0D) d6 = -1.0D;
          d6 /= 1.4D;
          d6 /= 2.0D;
          d4 = 0.0D;
        } else {
          if (d6 > 1.0D) d6 = 1.0D;
          d6 /= 6.0D;
        }
        d4 += 0.5D;
        d6 = d6 * paramInt5 / 16.0D;
        i2++;
        
        for (int i5 = 0; i5 < paramInt5; i5++) {
          double d7 = 0.0D;
          
          double d8 = arrayOfDouble[i5];
          
          double d9 = this.e[i1] / 512.0D;
          double d10 = this.f[i1] / 512.0D;
          
          double d11 = (this.d[i1] / 10.0D + 1.0D) / 2.0D;
          if (d11 < 0.0D) { d7 = d9;
          } else if (d11 > 1.0D) d7 = d10; else
            d7 = d9 + (d10 - d9) * d11;
          d7 -= d8;
          double d12;
          if (i5 > paramInt5 - 4) {
            d12 = (i5 - (paramInt5 - 4)) / 3.0F;
            d7 = d7 * (1.0D - d12) + -10.0D * d12;
          }
          
          if (i5 < d5) {
            d12 = (d5 - i5) / 4.0D;
            if (d12 < 0.0D) d12 = 0.0D;
            if (d12 > 1.0D) d12 = 1.0D;
            d7 = d7 * (1.0D - d12) + -10.0D * d12;
          }
          
          paramArrayOfDouble[i1] = d7;
          i1++;
        }
      }
    }
    return paramArrayOfDouble;
  }
  
  public boolean isChunkLoaded(int paramInt1, int paramInt2) {
    return true;
  }
  
  public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
  {
    BlockSand.instaFall = true;
    int i1 = paramInt1 * 16;
    int i2 = paramInt2 * 16;
    
    this.c.a(this.o, this.i, paramInt1, paramInt2);
    int i5;
    int i6;
    for (int i3 = 0; i3 < 8; i3++) {
      int i4 = i1 + this.i.nextInt(16) + 8;
      i5 = this.i.nextInt(120) + 4;
      i6 = i2 + this.i.nextInt(16) + 8;
      new WorldGenHellLava(Block.LAVA.id).a(this.o, this.i, i4, i5, i6);
    }
    
    int i3 = this.i.nextInt(this.i.nextInt(10) + 1) + 1;
    int i7;
    for (int i4 = 0; i4 < i3; i4++) {
      i5 = i1 + this.i.nextInt(16) + 8;
      i6 = this.i.nextInt(120) + 4;
      i7 = i2 + this.i.nextInt(16) + 8;
      new WorldGenFire().a(this.o, this.i, i5, i6, i7);
    }
    
    i3 = this.i.nextInt(this.i.nextInt(10) + 1);
    int i4;
    for (i4 = 0; i4 < i3; i4++) {
      i5 = i1 + this.i.nextInt(16) + 8;
      i6 = this.i.nextInt(120) + 4;
      i7 = i2 + this.i.nextInt(16) + 8;
      new WorldGenLightStone1().a(this.o, this.i, i5, i6, i7);
    }
    
    for (i4 = 0; i4 < 10; i4++) {
      i5 = i1 + this.i.nextInt(16) + 8;
      i6 = this.i.nextInt(128);
      i7 = i2 + this.i.nextInt(16) + 8;
      new WorldGenLightStone2().a(this.o, this.i, i5, i6, i7);
    }
    
    if (this.i.nextInt(1) == 0) {
      i4 = i1 + this.i.nextInt(16) + 8;
      i5 = this.i.nextInt(128);
      i6 = i2 + this.i.nextInt(16) + 8;
      new WorldGenFlowers(Block.BROWN_MUSHROOM.id).a(this.o, this.i, i4, i5, i6);
    }
    
    if (this.i.nextInt(1) == 0) {
      i4 = i1 + this.i.nextInt(16) + 8;
      i5 = this.i.nextInt(128);
      i6 = i2 + this.i.nextInt(16) + 8;
      new WorldGenFlowers(Block.RED_MUSHROOM.id).a(this.o, this.i, i4, i5, i6);
    }
    
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
  

  double[] f;
  
  double[] g;
  double[] h;
  public List getMobsFor(EnumCreatureType paramEnumCreatureType, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramEnumCreatureType == EnumCreatureType.MONSTER) && (this.c.a(paramInt1, paramInt2, paramInt3))) {
      return this.c.b();
    }
    
    BiomeBase localBiomeBase = this.o.getBiome(paramInt1, paramInt3);
    if (localBiomeBase == null) {
      return null;
    }
    return localBiomeBase.getMobs(paramEnumCreatureType);
  }
  
  public ChunkPosition findNearestMapFeature(World paramWorld, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    return null;
  }
}
