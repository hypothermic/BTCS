package net.minecraft.server;

public class NibbleArray {
  public final byte[] a;
  private final int b;
  private final int c;
  
  public NibbleArray(int paramInt1, int paramInt2) {
    this.a = new byte[paramInt1 >> 1];
    this.b = paramInt2;
    this.c = (paramInt2 + 4);
  }
  
  public NibbleArray(byte[] paramArrayOfByte, int paramInt) {
    this.a = paramArrayOfByte;
    this.b = paramInt;
    this.c = (paramInt + 4);
  }
  
  public int a(int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt2 << this.c | paramInt3 << this.b | paramInt1;
    int j = i >> 1;
    int k = i & 0x1;
    
    if (k == 0) {
      return this.a[j] & 0xF;
    }
    return this.a[j] >> 4 & 0xF;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt2 << this.c | paramInt3 << this.b | paramInt1;
    
    int j = i >> 1;
    int k = i & 0x1;
    
    if (k == 0) {
      this.a[j] = ((byte)(this.a[j] & 0xF0 | paramInt4 & 0xF));
    } else {
      this.a[j] = ((byte)(this.a[j] & 0xF | (paramInt4 & 0xF) << 4));
    }
  }
}
