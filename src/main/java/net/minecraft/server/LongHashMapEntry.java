package net.minecraft.server;

class LongHashMapEntry
{
  final long a;
  
  Object b;
  
  LongHashMapEntry c;
  
  final int d;
  
  LongHashMapEntry(int paramInt, long paramLong, Object paramObject, LongHashMapEntry paramLongHashMapEntry)
  {
    this.b = paramObject;
    this.c = paramLongHashMapEntry;
    this.a = paramLong;
    this.d = paramInt;
  }
  
  public final long a() {
    return this.a;
  }
  
  public final Object b() {
    return this.b;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LongHashMapEntry)) return false;
    LongHashMapEntry localLongHashMapEntry = (LongHashMapEntry)paramObject;
    Long localLong1 = Long.valueOf(a());
    Long localLong2 = Long.valueOf(localLongHashMapEntry.a());
    if ((localLong1 == localLong2) || ((localLong1 != null) && (localLong1.equals(localLong2)))) {
      Object localObject1 = b();
      Object localObject2 = localLongHashMapEntry.b();
      if ((localObject1 == localObject2) || ((localObject1 != null) && (localObject1.equals(localObject2)))) return true;
    }
    return false;
  }
  
  public final int hashCode() {
    return LongHashMap.f(this.a);
  }
  
  public final String toString() {
    return a() + "=" + b();
  }
}
