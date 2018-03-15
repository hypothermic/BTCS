package net.minecraft.server;

class IntHashMapEntry {
  final int a;
  Object b;
  IntHashMapEntry c;
  final int d;
  
  IntHashMapEntry(int paramInt1, int paramInt2, Object paramObject, IntHashMapEntry paramIntHashMapEntry)
  {
    this.b = paramObject;
    this.c = paramIntHashMapEntry;
    this.a = paramInt2;
    this.d = paramInt1;
  }
  
  public final int a() {
    return this.a;
  }
  
  public final Object b() {
    return this.b;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof IntHashMapEntry)) return false;
    IntHashMapEntry localIntHashMapEntry = (IntHashMapEntry)paramObject;
    Integer localInteger1 = Integer.valueOf(a());
    Integer localInteger2 = Integer.valueOf(localIntHashMapEntry.a());
    if ((localInteger1 == localInteger2) || ((localInteger1 != null) && (localInteger1.equals(localInteger2)))) {
      Object localObject1 = b();
      Object localObject2 = localIntHashMapEntry.b();
      if ((localObject1 == localObject2) || ((localObject1 != null) && (localObject1.equals(localObject2)))) return true;
    }
    return false;
  }
  
  public final int hashCode() {
    return IntHashMap.f(this.a);
  }
  
  public final String toString() {
    return a() + "=" + b();
  }
}
