package net.minecraft.server;

import java.util.HashSet;
import java.util.Set;

public class IntHashMap
{
  private transient IntHashMapEntry[] a;
  private transient int b;
  private int c;
  private final float d;
  private volatile transient int e;
  private Set f = new HashSet();
  
  public IntHashMap()
  {
    this.d = 0.75F;
    this.c = 12;
    this.a = new IntHashMapEntry[16];
  }
  
  // BTCS start
  static int f(int i) {
      return g(i);
  }
  // BTCS end
  
  private static int g(int paramInt) {
    paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
    return paramInt ^ paramInt >>> 7 ^ paramInt >>> 4;
  }
  
  private static int a(int paramInt1, int paramInt2) {
    return paramInt1 & paramInt2 - 1;
  }

  public Object get(int paramInt)
  {
    int i = g(paramInt);
    for (IntHashMapEntry localIntHashMapEntry = this.a[a(i, this.a.length)]; localIntHashMapEntry != null; localIntHashMapEntry = localIntHashMapEntry.c) {
      if (localIntHashMapEntry.a == paramInt) return localIntHashMapEntry.b;
    }
    return null;
  }
  
  public boolean b(int paramInt) {
    return c(paramInt) != null;
  }
  
  final IntHashMapEntry c(int paramInt) {
    int i = g(paramInt);
    for (IntHashMapEntry localIntHashMapEntry = this.a[a(i, this.a.length)]; localIntHashMapEntry != null; localIntHashMapEntry = localIntHashMapEntry.c) {
      if (localIntHashMapEntry.a == paramInt) return localIntHashMapEntry;
    }
    return null;
  }
  
  public void a(int paramInt, Object paramObject) {
    this.f.add(Integer.valueOf(paramInt));
    int i = g(paramInt);
    int j = a(i, this.a.length);
    for (IntHashMapEntry localIntHashMapEntry = this.a[j]; localIntHashMapEntry != null; localIntHashMapEntry = localIntHashMapEntry.c) {
      if (localIntHashMapEntry.a == paramInt) {
        localIntHashMapEntry.b = paramObject;
      }
    }
    
    this.e += 1;
    a(i, paramInt, paramObject, j);
  }
  
  private void h(int paramInt)
  {
    IntHashMapEntry[] arrayOfIntHashMapEntry1 = this.a;
    int i = arrayOfIntHashMapEntry1.length;
    if (i == 1073741824) {
      this.c = Integer.MAX_VALUE;
      return;
    }
    
    IntHashMapEntry[] arrayOfIntHashMapEntry2 = new IntHashMapEntry[paramInt];
    a(arrayOfIntHashMapEntry2);
    this.a = arrayOfIntHashMapEntry2;
    this.c = ((int)(paramInt * this.d));
  }
  
  private void a(IntHashMapEntry[] paramArrayOfIntHashMapEntry) {
    IntHashMapEntry[] arrayOfIntHashMapEntry = this.a;
    int i = paramArrayOfIntHashMapEntry.length;
    for (int j = 0; j < arrayOfIntHashMapEntry.length; j++) {
      Object localObject = arrayOfIntHashMapEntry[j];
      if (localObject != null) {
        arrayOfIntHashMapEntry[j] = null;
        do {
          IntHashMapEntry localIntHashMapEntry = ((IntHashMapEntry)localObject).c;
          int k = a(((IntHashMapEntry)localObject).d, i);
          ((IntHashMapEntry)localObject).c = paramArrayOfIntHashMapEntry[k];
          paramArrayOfIntHashMapEntry[k] = (IntHashMapEntry) localObject; // BTCS: added cast (IntHashMapEntry)
          localObject = localIntHashMapEntry;
        } while (localObject != null);
      }
    }
  }
  
  public Object d(int paramInt) {
    this.f.remove(Integer.valueOf(paramInt));
    IntHashMapEntry localIntHashMapEntry = e(paramInt);
    return localIntHashMapEntry == null ? null : localIntHashMapEntry.b;
  }
  
  final IntHashMapEntry e(int paramInt) {
    int i = g(paramInt);
    int j = a(i, this.a.length);
    Object localObject1 = this.a[j];
    Object localObject2 = localObject1;
    
    while (localObject2 != null) {
      IntHashMapEntry localIntHashMapEntry = ((IntHashMapEntry)localObject2).c;
      if (((IntHashMapEntry)localObject2).a == paramInt) {
        this.e += 1;
        this.b -= 1;
        if (localObject1 == localObject2) this.a[j] = localIntHashMapEntry; else
          ((IntHashMapEntry)localObject1).c = localIntHashMapEntry;
        return (IntHashMapEntry)localObject2;
      }
      localObject1 = localObject2;
      localObject2 = localIntHashMapEntry;
    }
    
    return (IntHashMapEntry)localObject2;
  }
  
  public void a() {
    this.e += 1;
    IntHashMapEntry[] arrayOfIntHashMapEntry = this.a;
    for (int i = 0; i < arrayOfIntHashMapEntry.length; i++)
      arrayOfIntHashMapEntry[i] = null;
    this.b = 0;
  }
  
  private void a(int paramInt1, int paramInt2, Object paramObject, int paramInt3)
  {
    IntHashMapEntry localIntHashMapEntry = this.a[paramInt3];
    this.a[paramInt3] = new IntHashMapEntry(paramInt1, paramInt2, paramObject, localIntHashMapEntry);
    if (this.b++ >= this.c) h(2 * this.a.length);
  }
}
