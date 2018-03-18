package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider {
  private Random k;
  private NoiseGeneratorOctaves l;
  private NoiseGeneratorOctaves m;
  private NoiseGeneratorOctaves n;
  private NoiseGeneratorOctaves o;
  public NoiseGeneratorOctaves a;
  public NoiseGeneratorOctaves b;
  public NoiseGeneratorOctaves c;
  private World p;
  private final boolean q;
  private double[] r;
  
  public ChunkProviderGenerate(World paramWorld, long paramLong, boolean paramBoolean)
  {
    this.p = paramWorld;
    this.q = paramBoolean;
    
    this.k = new Random(paramLong);
    this.l = new NoiseGeneratorOctaves(this.k, 16);
    this.m = new NoiseGeneratorOctaves(this.k, 16);
    this.n = new NoiseGeneratorOctaves(this.k, 8);
    this.o = new NoiseGeneratorOctaves(this.k, 4);
    
    this.a = new NoiseGeneratorOctaves(this.k, 10);
    this.b = new NoiseGeneratorOctaves(this.k, 16);

    this.c = new NoiseGeneratorOctaves(this.k, 8);
  }
  


  public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    int i1 = 4;
    int i2 = 16;
    int i3 = 63;
    
    int i4 = i1 + 1;
    int i5 = 17;
    int i6 = i1 + 1;
    
    this.y = this.p.getWorldChunkManager().getBiomes(this.y, paramInt1 * 4 - 2, paramInt2 * 4 - 2, i4 + 5, i6 + 5);
    this.r = a(this.r, paramInt1 * i1, 0, paramInt2 * i1, i4, i5, i6);
    
    for (int i7 = 0; i7 < i1; i7++) {
      for (int i8 = 0; i8 < i1; i8++) {
        for (int i9 = 0; i9 < i2; i9++) {
          double d1 = 0.125D;
          double d2 = this.r[(((i7 + 0) * i6 + (i8 + 0)) * i5 + (i9 + 0))];
          double d3 = this.r[(((i7 + 0) * i6 + (i8 + 1)) * i5 + (i9 + 0))];
          double d4 = this.r[(((i7 + 1) * i6 + (i8 + 0)) * i5 + (i9 + 0))];
          double d5 = this.r[(((i7 + 1) * i6 + (i8 + 1)) * i5 + (i9 + 0))];
          
          double d6 = (this.r[(((i7 + 0) * i6 + (i8 + 0)) * i5 + (i9 + 1))] - d2) * d1;
          double d7 = (this.r[(((i7 + 0) * i6 + (i8 + 1)) * i5 + (i9 + 1))] - d3) * d1;
          double d8 = (this.r[(((i7 + 1) * i6 + (i8 + 0)) * i5 + (i9 + 1))] - d4) * d1;
          double d9 = (this.r[(((i7 + 1) * i6 + (i8 + 1)) * i5 + (i9 + 1))] - d5) * d1;
          
          for (int i10 = 0; i10 < 8; i10++) {
            double d10 = 0.25D;
            
            double d11 = d2;
            double d12 = d3;
            double d13 = (d4 - d2) * d10;
            double d14 = (d5 - d3) * d10;
            
            for (int i11 = 0; i11 < 4; i11++) {
              int i12 = i11 + i7 * 4 << 11 | 0 + i8 * 4 << 7 | i9 * 8 + i10;
              int i13 = 128;
              i12 -= i13;
              double d15 = 0.25D;
              
              double d16 = d11;
              double d17 = (d12 - d11) * d15;
              d16 -= d17;
              for (int i14 = 0; i14 < 4; i14++) {
                if ((d16 += d17) > 0.0D) { // BTCS: '(d16 += d17 > 0.0D)' --> '((d16 += d17) > 0.0D)'
                  paramArrayOfByte[(i12 += i13)] = ((byte)Block.STONE.id);
                } else if (i9 * 8 + i10 < i3) {
                  paramArrayOfByte[(i12 += i13)] = ((byte)Block.STATIONARY_WATER.id);
                } else {
                  paramArrayOfByte[(i12 += i13)] = 0;
                }
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
  
  private double[] s = new double['Ā'];
  
  public void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, BiomeBase[] paramArrayOfBiomeBase) {
    int i1 = 63;
    
    double d1 = 0.03125D;
    this.s = this.o.a(this.s, paramInt1 * 16, paramInt2 * 16, 0, 16, 16, 1, d1 * 2.0D, d1 * 2.0D, d1 * 2.0D);
    
    for (int i2 = 0; i2 < 16; i2++) {
      for (int i3 = 0; i3 < 16; i3++) {
        BiomeBase localBiomeBase = paramArrayOfBiomeBase[(i3 + i2 * 16)];
        float f1 = localBiomeBase.i();
        int i4 = (int)(this.s[(i2 + i3 * 16)] / 3.0D + 3.0D + this.k.nextDouble() * 0.25D);
        
        int i5 = -1;
        
        int i6 = localBiomeBase.A;
        int i7 = localBiomeBase.B;
        
        for (int i8 = 127; i8 >= 0; i8--) {
          int i9 = (i3 * 16 + i2) * 128 + i8;
          
          if (i8 <= 0 + this.k.nextInt(5)) {
            paramArrayOfByte[i9] = ((byte)Block.BEDROCK.id);
          } else {
            int i10 = paramArrayOfByte[i9];
            
            if (i10 == 0) {
              i5 = -1;
            } else if (i10 == Block.STONE.id) {
              if (i5 == -1) {
                if (i4 <= 0) {
                  i6 = 0;
                  i7 = (byte)Block.STONE.id;
                } else if ((i8 >= i1 - 4) && (i8 <= i1 + 1)) {
                  i6 = localBiomeBase.A;
                  i7 = localBiomeBase.B;
                }
                
                if ((i8 < i1) && (i6 == 0)) {
                  if (f1 < 0.15F) i6 = (byte)Block.ICE.id; else {
                    i6 = (byte)Block.STATIONARY_WATER.id;
                  }
                }
                

                i5 = i4;
                if (i8 >= i1 - 1) paramArrayOfByte[i9] = (byte) i6; else // BTCS: added cast (byte)
                  paramArrayOfByte[i9] = (byte) i7; // BTCS: added cast (byte)
              } else if (i5 > 0) {
                i5--;
                paramArrayOfByte[i9] = (byte) i7; // BTCS: added cast (byte)
                


                if ((i5 == 0) && (i7 == Block.SAND.id)) {
                  i5 = this.k.nextInt(4);
                  i7 = (byte)Block.SANDSTONE.id;
                }
              }
            }
          }
        }
      }
    }
  }
  
  private WorldGenBase t = new WorldGenCaves();
  private WorldGenStronghold u = new WorldGenStronghold();
  private WorldGenVillage v = new WorldGenVillage(0);
  private WorldGenMineshaft w = new WorldGenMineshaft();
  

  private WorldGenBase x = new WorldGenCanyon();
  private BiomeBase[] y;
  double[] d;
  double[] e;
  
  public Chunk getChunkAt(int paramInt1, int paramInt2) { return getOrCreateChunk(paramInt1, paramInt2); }
  
  public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
  {
	nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 100");
    this.k.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 200");
    byte[] arrayOfByte1 = new byte[32768];
    
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 300");
    a(paramInt1, paramInt2, arrayOfByte1);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 400");
    this.y = this.p.getWorldChunkManager().getBiomeBlock(this.y, paramInt1 * 16, paramInt2 * 16, 16, 16);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 500");
    a(paramInt1, paramInt2, arrayOfByte1, this.y);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 600");
    
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 700");
    this.t.a(this, this.p, paramInt1, paramInt2, arrayOfByte1);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 800");
    this.x.a(this, this.p, paramInt1, paramInt2, arrayOfByte1);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 900");
    if (this.q) {
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1100");
      this.w.a(this, this.p, paramInt1, paramInt2, arrayOfByte1);
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1200");
      this.v.a(this, this.p, paramInt1, paramInt2, arrayOfByte1);
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1300");
      this.u.a(this, this.p, paramInt1, paramInt2, arrayOfByte1);
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1400");
    }
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1500");
    Chunk localChunk = new Chunk(this.p, arrayOfByte1, paramInt1, paramInt2);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1600");
    byte[] arrayOfByte2 = localChunk.l();
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1700");
    
    for (int i1 = 0; i1 < arrayOfByte2.length; i1++) {
    	nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1800");
      arrayOfByte2[i1] = ((byte)this.y[i1].id);
    }
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 1900");
    
    localChunk.initLighting();
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderGenerate.getOrCreateChunck() - 2000");
    
    return localChunk;
  }
  
  double[] f;
  double[] g;
  double[] h;
  float[] i;
  private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if (paramArrayOfDouble == null) {
      paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];
    }
    if (this.i == null) {
      this.i = new float[25];
      for (int i1 = -2; i1 <= 2; i1++) {
        for (int i2 = -2; i2 <= 2; i2++) {
          float f1 = 10.0F / MathHelper.c(i1 * i1 + i2 * i2 + 0.2F);
          this.i[(i1 + 2 + (i2 + 2) * 5)] = f1;
        }
      }
    }
    
    double d1 = 684.412D;
    double d2 = 684.412D;

    this.g = this.a.a(this.g, paramInt1, paramInt3, paramInt4, paramInt6, 1.121D, 1.121D, 0.5D);
    this.h = this.b.a(this.h, paramInt1, paramInt3, paramInt4, paramInt6, 200.0D, 200.0D, 0.5D);
    
    this.d = this.n.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 160.0D, d1 / 80.0D);
    this.e = this.l.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
    this.f = this.m.a(this.f, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
    paramInt1 = paramInt3 = 0;
    
    int i3 = 0;
    int i4 = 0;
    
    for (int i5 = 0; i5 < paramInt4; i5++) {
      for (int i6 = 0; i6 < paramInt6; i6++) {
        float f2 = 0.0F;
        float f3 = 0.0F;
        float f4 = 0.0F;
        
        int i7 = 2;
        
        BiomeBase localBiomeBase1 = this.y[(i5 + 2 + (i6 + 2) * (paramInt4 + 5))];
        for (int i8 = -i7; i8 <= i7; i8++) {
          for (int i9 = -i7; i9 <= i7; i9++) {
            BiomeBase localBiomeBase2 = this.y[(i5 + i8 + 2 + (i6 + i9 + 2) * (paramInt4 + 5))];
            float f5 = this.i[(i8 + 2 + (i9 + 2) * 5)] / (localBiomeBase2.D + 2.0F);
            if (localBiomeBase2.D > localBiomeBase1.D) {
              f5 /= 2.0F;
            }
            f2 += localBiomeBase2.E * f5;
            f3 += localBiomeBase2.D * f5;
            f4 += f5;
          }
        }
        f2 /= f4;
        f3 /= f4;
        
        f2 = f2 * 0.9F + 0.1F;
        f3 = (f3 * 4.0F - 1.0F) / 8.0F;
        
        double d3 = this.h[i4] / 8000.0D;
        if (d3 < 0.0D) d3 = -d3 * 0.3D;
        d3 = d3 * 3.0D - 2.0D;
        
        if (d3 < 0.0D) {
          d3 /= 2.0D;
          if (d3 < -1.0D) d3 = -1.0D;
          d3 /= 1.4D;
          d3 /= 2.0D;
        } else {
          if (d3 > 1.0D) d3 = 1.0D;
          d3 /= 8.0D;
        }
        
        i4++;
        
        for (int i10 = 0; i10 < paramInt5; i10++) {
          double d4 = f3;
          double d5 = f2;
          
          d4 += d3 * 0.2D;
          d4 = d4 * paramInt5 / 16.0D;
          
          double d6 = paramInt5 / 2.0D + d4 * 4.0D;
          
          double d7 = 0.0D;
          
          double d8 = (i10 - d6) * 12.0D * 128.0D / 128.0D / d5;
          
          if (d8 < 0.0D) { d8 *= 4.0D;
          }
          double d9 = this.e[i3] / 512.0D;
          double d10 = this.f[i3] / 512.0D;
          
          double d11 = (this.d[i3] / 10.0D + 1.0D) / 2.0D;
          if (d11 < 0.0D) { d7 = d9;
          } else if (d11 > 1.0D) d7 = d10; else
            d7 = d9 + (d10 - d9) * d11;
          d7 -= d8;
          
          if (i10 > paramInt5 - 4) {
            double d12 = (i10 - (paramInt5 - 4)) / 3.0F;
            d7 = d7 * (1.0D - d12) + -10.0D * d12;
          }
          
          paramArrayOfDouble[i3] = d7;
          i3++;
        }
      }
    }
    return paramArrayOfDouble;
  }
  
  public boolean isChunkLoaded(int paramInt1, int paramInt2) {
    return true;
  }
  
  int[][] j = new int[32][32];
  
  public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
  {
    BlockSand.instaFall = true;
    int i1 = paramInt1 * 16;
    int i2 = paramInt2 * 16;
    
    BiomeBase localBiomeBase = this.p.getBiome(i1 + 16, i2 + 16);

    this.k.setSeed(this.p.getSeed());
    long l1 = this.k.nextLong() / 2L * 2L + 1L;
    long l2 = this.k.nextLong() / 2L * 2L + 1L;
    this.k.setSeed(paramInt1 * l1 + paramInt2 * l2 ^ this.p.getSeed());
    
    boolean bool = false;
    
    if (this.q) {
      this.w.a(this.p, this.k, paramInt1, paramInt2);
      bool = this.v.a(this.p, this.k, paramInt1, paramInt2);
      this.u.a(this.p, this.k, paramInt1, paramInt2); }
    int i4;
    int i5;
    if ((!bool) && (this.k.nextInt(4) == 0)) {
      int i3 = i1 + this.k.nextInt(16) + 8; // BTCS: added 'int '
      i4 = this.k.nextInt(128);
      i5 = i2 + this.k.nextInt(16) + 8;
      new WorldGenLakes(Block.STATIONARY_WATER.id).a(this.p, this.k, i3, i4, i5);
    }
    
    if ((!bool) && (this.k.nextInt(8) == 0)) {
      int i3 = i1 + this.k.nextInt(16) + 8; // BTCS: added 'int '
      i4 = this.k.nextInt(this.k.nextInt(120) + 8);
      i5 = i2 + this.k.nextInt(16) + 8;
      if ((i4 < 63) || (this.k.nextInt(10) == 0)) { new WorldGenLakes(Block.STATIONARY_LAVA.id).a(this.p, this.k, i3, i4, i5);
      }
    }
    for (int i3 = 0; i3 < 8; i3++) {
      i4 = i1 + this.k.nextInt(16) + 8;
      i5 = this.k.nextInt(128);
      int i6 = i2 + this.k.nextInt(16) + 8;
      if (!new WorldGenDungeons().a(this.p, this.k, i4, i5, i6)) {}
    }
    

    localBiomeBase.a(this.p, this.k, i1, i2);
    
    SpawnerCreature.a(this.p, localBiomeBase, i1 + 8, i2 + 8, 16, 16, this.k);
    
    i1 += 8;
    i2 += 8;
    int i3; // BTCS
    for (i3 = 0; i3 < 16; i3++) {
      for (i4 = 0; i4 < 16; i4++) {
        i5 = this.p.f(i1 + i3, i2 + i4);
        
        if (this.p.s(i3 + i1, i5 - 1, i4 + i2)) {
          this.p.setTypeId(i3 + i1, i5 - 1, i4 + i2, Block.ICE.id);
        }
        if (this.p.u(i3 + i1, i5, i4 + i2)) {
          this.p.setTypeId(i3 + i1, i5, i4 + i2, Block.SNOW.id);
        }
      }
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

  public List getMobsFor(EnumCreatureType paramEnumCreatureType, int paramInt1, int paramInt2, int paramInt3)
  {
    BiomeBase localBiomeBase = this.p.getBiome(paramInt1, paramInt3);
    if (localBiomeBase == null) {
      return null;
    }
    return localBiomeBase.getMobs(paramEnumCreatureType);
  }
  
  public ChunkPosition findNearestMapFeature(World paramWorld, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    if (("Stronghold".equals(paramString)) && (this.u != null)) {
      return this.u.getNearestGeneratedFeature(paramWorld, paramInt1, paramInt2, paramInt3);
    }
    return null;
  }
}
