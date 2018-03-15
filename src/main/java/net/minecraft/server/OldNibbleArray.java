package net.minecraft.server;


public class OldNibbleArray
{
  public final byte[] a;
  
  private final int b;
  
  private final int c;
  

  public OldNibbleArray(byte[] paramArrayOfByte, int paramInt)
  {
    this.a = paramArrayOfByte;
    this.b = paramInt;
    this.c = (paramInt + 4);
  }
  
  public int a(int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt1 << this.c | paramInt3 << this.b | paramInt2;
    int j = i >> 1;
    int k = i & 0x1;
    
    if (k == 0) {
      return this.a[j] & 0xF;
    }
    return this.a[j] >> 4 & 0xF;
  }
}
