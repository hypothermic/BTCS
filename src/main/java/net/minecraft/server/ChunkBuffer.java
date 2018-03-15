package net.minecraft.server;

import java.io.ByteArrayOutputStream;

class ChunkBuffer extends ByteArrayOutputStream
{
  private int b;
  private int c;
  
  final RegionFile a; // BTCS
  
  public ChunkBuffer(RegionFile regionfile, int paramInt1, int paramInt2) // BTCS: added RF
  {
    super(8096);
    this.a = regionfile; // BTCS
    this.b = paramInt1;
    this.c = paramInt2;
  }
  
  public void close() {
    this.a.a(this.b, this.c, this.buf, this.count);
  }
}
