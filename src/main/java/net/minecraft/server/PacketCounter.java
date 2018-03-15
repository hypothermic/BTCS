package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;

public class PacketCounter
{
  public static boolean a = true;
  
  private static final Map b = new HashMap();
  private static final Map c = new HashMap();
  
  private static final Object d = new Object();
  


























  public static void a(int paramInt, long paramLong)
  {
    if (!a) { return;
    }
    synchronized (d)
    {
      if (b.containsKey(Integer.valueOf(paramInt))) {
        b.put(Integer.valueOf(paramInt), Long.valueOf(((Long)b.get(Integer.valueOf(paramInt))).longValue() + 1L));
        c.put(Integer.valueOf(paramInt), Long.valueOf(((Long)c.get(Integer.valueOf(paramInt))).longValue() + paramLong));
      } else {
        b.put(Integer.valueOf(paramInt), Long.valueOf(1L));
        c.put(Integer.valueOf(paramInt), Long.valueOf(paramLong));
      }
    }
  }
}
