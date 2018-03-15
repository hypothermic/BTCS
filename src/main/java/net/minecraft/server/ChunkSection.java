package net.minecraft.server;

public class ChunkSection
{
  private int a;
  private int b;
  private int c;
  private byte[] d;
  private NibbleArray e;
  private NibbleArray f;
  private NibbleArray g;
  private NibbleArray h;
  
  public ChunkSection(int i) {
    this.a = i;
    this.d = new byte['က'];
    this.f = new NibbleArray(this.d.length, 4);
    this.h = new NibbleArray(this.d.length, 4);
    this.g = new NibbleArray(this.d.length, 4);
  }
  
  public ChunkSection(int y, byte[] blkData, byte[] extBlkData)
  {
    this.a = y;
    this.d = blkData;
    if (extBlkData != null) {
      this.e = new NibbleArray(extBlkData, 4);
    }
    this.f = new NibbleArray(this.d.length, 4);
    this.h = new NibbleArray(this.d.length, 4);
    this.g = new NibbleArray(this.d.length, 4);
    d();
  }
  
  public int a(int i, int j, int k)
  {
    int l = this.d[(j << 8 | k << 4 | i)] & 0xFF;
    
    return this.e != null ? this.e.a(i, j, k) << 8 | l : l;
  }
  
  public void a(int i, int j, int k, int l) {
    int i1 = this.d[(j << 8 | k << 4 | i)] & 0xFF;
    
    if (this.e != null) {
      i1 |= this.e.a(i, j, k) << 8;
    }
    
    if ((i1 == 0) && (l != 0)) {
      this.b += 1;
      if ((Block.byId[l] != null) && (Block.byId[l].n())) {
        this.c += 1;
      }
    } else if ((i1 != 0) && (l == 0)) {
      this.b -= 1;
      if ((Block.byId[i1] != null) && (Block.byId[i1].n())) {
        this.c -= 1;
      }
    } else if ((Block.byId[i1] != null) && (Block.byId[i1].n()) && ((Block.byId[l] == null) || (!Block.byId[l].n()))) {
      this.c -= 1;
    } else if (((Block.byId[i1] == null) || (!Block.byId[i1].n())) && (Block.byId[l] != null) && (Block.byId[l].n())) {
      this.c += 1;
    }
    
    this.d[(j << 8 | k << 4 | i)] = ((byte)(l & 0xFF));
    if (l > 255) {
      if (this.e == null) {
        this.e = new NibbleArray(this.d.length, 4);
      }
      
      this.e.a(i, j, k, (l & 0xF00) >> 8);
    } else if (this.e != null) {
      this.e.a(i, j, k, 0);
    }
  }
  
  public int b(int i, int j, int k) {
    return this.f.a(i, j, k);
  }
  
  public void b(int i, int j, int k, int l) {
    this.f.a(i, j, k, l);
  }
  
  public boolean a() {
    return this.b == 0;
  }
  
  public boolean b() {
    return this.c > 0;
  }
  
  public int c() {
    return this.a;
  }
  
  public void c(int i, int j, int k, int l) {
    this.h.a(i, j, k, l);
  }
  
  public int c(int i, int j, int k) {
    return this.h.a(i, j, k);
  }
  
  public void d(int i, int j, int k, int l) {
    this.g.a(i, j, k, l);
  }
  
  public int d(int i, int j, int k) {
    return this.g.a(i, j, k);
  }
  
  public void d() {
    this.b = 0;
    this.c = 0;
    
    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < 16; j++) {
        for (int k = 0; k < 16; k++) {
          int l = a(i, j, k);
          
          if (l > 0) {
            if (Block.byId[l] == null) {
              this.d[(j << 8 | k << 4 | i)] = 0;
              if (this.e != null) {
                this.e.a(i, j, k, 0);
              }
            } else {
              this.b += 1;
              if (Block.byId[l].n()) {
                this.c += 1;
              }
            }
          }
        }
      }
    }
  }
  
  public void e() {}
  
  public int f() {
    return this.b;
  }
  
  public byte[] g() {
    return this.d;
  }
  
  public NibbleArray h() {
    return this.e;
  }
  
  public NibbleArray i() {
    return this.f;
  }
  
  public NibbleArray j() {
    return this.g;
  }
  
  public NibbleArray k() {
    return this.h;
  }
  
  public void a(byte[] abyte) {
    this.d = abyte;
  }
  
  public void a(NibbleArray nibblearray) {
    this.e = nibblearray;
  }
  
  public void b(NibbleArray nibblearray) {
    this.f = nibblearray;
  }
  
  public void c(NibbleArray nibblearray) {
    this.g = nibblearray;
  }
  
  public void d(NibbleArray nibblearray) {
    this.h = nibblearray;
  }
}
