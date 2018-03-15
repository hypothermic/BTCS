package net.minecraft.server;


public class LongHashMap
{
  private transient LongHashMapEntry[] entries;
  
  private transient int count;
  
  private int c;
  
  private final float d;
  
  private volatile transient int e;
  
  public LongHashMap()
  {
    this.d = 0.75F;
    this.c = 12;
    this.entries = new LongHashMapEntry[16];
  }
  
  // BTCS start
  static int f(long i) {
      return g(i);
  }
  // BTCS end
  
  private static int g(long paramLong) {
    return a((int)(paramLong ^ paramLong >>> 32));
  }
  
  private static int a(int paramInt) {
    paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
    return paramInt ^ paramInt >>> 7 ^ paramInt >>> 4;
  }
  
  private static int a(int paramInt1, int paramInt2) {
    return paramInt1 & paramInt2 - 1;
  }
  
  public int count() {
    return this.count;
  }
  



  public Object getEntry(long paramLong)
  {
    int i = g(paramLong);
    for (LongHashMapEntry localLongHashMapEntry = this.entries[a(i, this.entries.length)]; localLongHashMapEntry != null; localLongHashMapEntry = localLongHashMapEntry.c) {
      if (localLongHashMapEntry.a == paramLong) return localLongHashMapEntry.b;
    }
    return null;
  }
  
  public boolean contains(long paramLong) {
    return c(paramLong) != null;
  }
  
  final LongHashMapEntry c(long paramLong) {
    int i = g(paramLong);
    for (LongHashMapEntry localLongHashMapEntry = this.entries[a(i, this.entries.length)]; localLongHashMapEntry != null; localLongHashMapEntry = localLongHashMapEntry.c) {
      if (localLongHashMapEntry.a == paramLong) return localLongHashMapEntry;
    }
    return null;
  }
  



  public void put(long paramLong, Object paramObject)
  {
    int i = g(paramLong);
    int j = a(i, this.entries.length);
    for (LongHashMapEntry localLongHashMapEntry = this.entries[j]; localLongHashMapEntry != null; localLongHashMapEntry = localLongHashMapEntry.c) {
      if (localLongHashMapEntry.a == paramLong) {
        localLongHashMapEntry.b = paramObject;
      }
    }
    
    this.e += 1;
    a(i, paramLong, paramObject, j);
  }
  
  private void b(int paramInt)
  {
    LongHashMapEntry[] arrayOfLongHashMapEntry1 = this.entries;
    int i = arrayOfLongHashMapEntry1.length;
    if (i == 1073741824) {
      this.c = Integer.MAX_VALUE;
      return;
    }
    
    LongHashMapEntry[] arrayOfLongHashMapEntry2 = new LongHashMapEntry[paramInt];
    a(arrayOfLongHashMapEntry2);
    this.entries = arrayOfLongHashMapEntry2;
    this.c = ((int)(paramInt * this.d));
  }
  
  private void a(LongHashMapEntry[] paramArrayOfLongHashMapEntry) {
    LongHashMapEntry[] arrayOfLongHashMapEntry = this.entries;
    int i = paramArrayOfLongHashMapEntry.length;
    for (int j = 0; j < arrayOfLongHashMapEntry.length; j++) {
      Object localObject = arrayOfLongHashMapEntry[j];
      if (localObject != null) {
        arrayOfLongHashMapEntry[j] = null;
        do {
          LongHashMapEntry localLongHashMapEntry = ((LongHashMapEntry)localObject).c;
          int k = a(((LongHashMapEntry)localObject).d, i);
          ((LongHashMapEntry)localObject).c = paramArrayOfLongHashMapEntry[k];
          paramArrayOfLongHashMapEntry[k] = (LongHashMapEntry) localObject; // BTCS: added cast (LongHashMapEntry)
          localObject = localLongHashMapEntry;
        } while (localObject != null);
      }
    }
  }
  
  public Object remove(long paramLong) {
    LongHashMapEntry localLongHashMapEntry = e(paramLong);
    return localLongHashMapEntry == null ? null : localLongHashMapEntry.b;
  }
  
  final LongHashMapEntry e(long paramLong) {
    int i = g(paramLong);
    int j = a(i, this.entries.length);
    Object localObject1 = this.entries[j];
    Object localObject2 = localObject1;
    
    while (localObject2 != null) {
      LongHashMapEntry localLongHashMapEntry = ((LongHashMapEntry)localObject2).c;
      if (((LongHashMapEntry)localObject2).a == paramLong) {
        this.e += 1;
        this.count -= 1;
        if (localObject1 == localObject2) this.entries[j] = localLongHashMapEntry; else
          ((LongHashMapEntry)localObject1).c = localLongHashMapEntry;
        return (LongHashMapEntry)localObject2;
      }
      localObject1 = localObject2;
      localObject2 = localLongHashMapEntry;
    }
    
    return (LongHashMapEntry)localObject2;
  }
  
















































































  private void a(int paramInt1, long paramLong, Object paramObject, int paramInt2)
  {
    LongHashMapEntry localLongHashMapEntry = this.entries[paramInt2];
    this.entries[paramInt2] = new LongHashMapEntry(paramInt1, paramLong, paramObject, localLongHashMapEntry);
    if (this.count++ >= this.c) b(2 * this.entries.length);
  }
}
