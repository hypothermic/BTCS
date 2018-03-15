package net.minecraft.server;

import java.util.Random;






public class WorldGenBase
{
  protected int b = 8;
  protected Random c = new Random();
  protected World d;
  
  public void a(IChunkProvider paramIChunkProvider, World paramWorld, int paramInt1, int paramInt2, byte[] paramArrayOfByte) {
    int i = this.b;
    this.d = paramWorld;
    
    this.c.setSeed(paramWorld.getSeed());
    long l1 = this.c.nextLong();
    long l2 = this.c.nextLong();
    
    for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
      for (int k = paramInt2 - i; k <= paramInt2 + i; k++) {
        long l3 = j * l1;
        long l4 = k * l2;
        this.c.setSeed(l3 ^ l4 ^ paramWorld.getSeed());
        a(paramWorld, j, k, paramInt1, paramInt2, paramArrayOfByte);
      }
    }
  }
  
  protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte) {}
}
