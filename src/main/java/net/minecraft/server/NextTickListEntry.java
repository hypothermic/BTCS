package net.minecraft.server;

public class NextTickListEntry implements Comparable {
  private static long f = 0L;
  public int a;
  public int b;
  public int c; public int d; public long e; private long g = f++;
  
  public NextTickListEntry(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramInt4;
  }
  
  public boolean equals(Object paramObject) {
    if ((paramObject instanceof NextTickListEntry)) {
      NextTickListEntry localNextTickListEntry = (NextTickListEntry)paramObject;
      return (this.a == localNextTickListEntry.a) && (this.b == localNextTickListEntry.b) && (this.c == localNextTickListEntry.c) && (this.d == localNextTickListEntry.d);
    }
    return false;
  }
  
  public int hashCode() {
    return (this.a * 1024 * 1024 + this.c * 1024 + this.b) * 256 + this.d;
  }
  
  public NextTickListEntry a(long paramLong) {
    this.e = paramLong;
    return this;
  }
  
  public int compareTo(NextTickListEntry paramNextTickListEntry) {
    if (this.e < paramNextTickListEntry.e) return -1;
    if (this.e > paramNextTickListEntry.e) return 1;
    if (this.g < paramNextTickListEntry.g) return -1;
    if (this.g > paramNextTickListEntry.g) return 1;
    return 0;
  }

  // BTCS start
public int compareTo(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
}
// BTCS end
}
