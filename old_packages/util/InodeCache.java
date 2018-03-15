package org.ibex.nestedvm.util;






public class InodeCache
{
  private static final Object PLACEHOLDER = new Object();
  
  private static final short SHORT_PLACEHOLDER = -2;
  private static final short SHORT_NULL = -1;
  private static final int LOAD_FACTOR = 2;
  private final int maxSize;
  private final int totalSlots;
  private final int maxUsedSlots;
  private final Object[] keys;
  private final short[] next;
  private final short[] prev;
  private final short[] inodes;
  private final short[] reverse;
  private int size;
  private int usedSlots;
  private short mru;
  private short lru;
  
  public InodeCache() { this(1024); }
  
  public InodeCache(int paramInt) { this.maxSize = paramInt;
    this.totalSlots = (paramInt * 2 * 2 + 3);
    this.maxUsedSlots = (this.totalSlots / 2);
    if (this.totalSlots > 32767) throw new IllegalArgumentException("cache size too large");
    this.keys = new Object[this.totalSlots];
    this.next = new short[this.totalSlots];
    this.prev = new short[this.totalSlots];
    this.inodes = new short[this.totalSlots];
    this.reverse = new short[this.totalSlots];
    clear();
  }
  
  private static void fill(Object[] paramArrayOfObject, Object paramObject) { for (int i = 0; i < paramArrayOfObject.length; i++) paramArrayOfObject[i] = paramObject; }
  private static void fill(short[] paramArrayOfShort, short paramShort) { for (int i = 0; i < paramArrayOfShort.length; i++) paramArrayOfShort[i] = paramShort; }
  
  public final void clear() { this.size = (this.usedSlots = 0);
    this.mru = (this.lru = -1);
    fill(this.keys, null);
    fill(this.inodes, (short)-1);
    fill(this.reverse, (short)-1);
  }
  
  public final short get(Object paramObject) {
    int i = paramObject.hashCode() & 0x7FFFFFFF;
    int j = i % this.totalSlots;
    int k = j;
    int m = 1;
    int n = 1;
    
    int i1 = -1;
    Object localObject;
    short s; int i3; while ((localObject = this.keys[j]) != null) {
      if (localObject == PLACEHOLDER) {
        if (i1 == -1) i1 = j;
      } else if (localObject.equals(paramObject)) {
        s = this.inodes[j];
        if (j == this.mru) return s;
        if (this.lru == j) {
          this.lru = this.next[this.lru];
        } else {
          i2 = this.prev[j];
          i3 = this.next[j];
          this.next[i2] = i3;
          this.prev[i3] = i2;
        }
        this.prev[j] = this.mru;
        this.next[this.mru] = ((short)j);
        this.mru = ((short)j);
        return s;
      }
      j = Math.abs((k + (n != 0 ? 1 : -1) * m * m) % this.totalSlots);
      if (n == 0) m++;
      n = n == 0 ? 1 : 0;
    }
    


    if (i1 == -1)
    {
      s = j;
      if (this.usedSlots == this.maxUsedSlots) {
        clear();
        return get(paramObject);
      }
      this.usedSlots += 1;
    }
    else {
      s = i1;
    }
    
    if (this.size == this.maxSize)
    {
      this.keys[this.lru] = PLACEHOLDER;
      this.inodes[this.lru] = -2;
      this.lru = this.next[this.lru];
    } else {
      if (this.size == 0) this.lru = ((short)s);
      this.size += 1;
    }
    
    label488:
    for (int i2 = i & 0x7FFF;; i2++) {
      j = i2 % this.totalSlots;
      k = j;
      m = 1;
      n = 1;
      i1 = -1;
      
      while ((i3 = this.reverse[j]) != -1) {
        int i4 = this.inodes[i3];
        if (i4 == -2) {
          if (i1 == -1) i1 = j;
        } else if (i4 == i2) {
            break label488;
          }
        j = Math.abs((k + (n != 0 ? 1 : -1) * m * m) % this.totalSlots);
        if (n == 0) m++;
        n = n == 0 ? 1 : 0;
      }
      
      if (i1 == -1) break; j = i1; break;
    }
    
    this.keys[s] = paramObject;
    this.reverse[j] = ((short)s);
    this.inodes[s] = ((short)i2);
    if (this.mru != -1) {
      this.prev[s] = this.mru;
      this.next[this.mru] = ((short)s);
    }
    this.mru = ((short)s);
    return (short)i2;
  }
  
  public Object reverse(short paramShort) {
    int i = paramShort % this.totalSlots;
    int j = i;
    int k = 1;
    int m = 1;
    int n;
    while ((n = this.reverse[i]) != -1) {
      if (this.inodes[n] == paramShort) return this.keys[n];
      i = Math.abs((j + (m != 0 ? 1 : -1) * k * k) % this.totalSlots);
      if (m == 0) k++;
      m = m == 0 ? 1 : 0;
    }
    return null;
  }
}
