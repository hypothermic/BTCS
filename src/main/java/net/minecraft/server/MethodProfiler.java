package net.minecraft.server;

import java.util.List;

public class MethodProfiler {
  public static boolean a = false;
  
  private static List b = new java.util.ArrayList();
  private static List c = new java.util.ArrayList();
  private static String d = "";
  private static java.util.Map e = new java.util.HashMap();
  

























  public static void a(String paramString)
  {
    if (!a) return;
    if (d.length() > 0) d += ".";
    d += paramString;
    b.add(d);
    c.add(Long.valueOf(System.nanoTime()));
  }
  
  public static void a() {
    if (!a) return;
    long l1 = System.nanoTime();
    long l2 = ((Long)c.remove(c.size() - 1)).longValue();
    b.remove(b.size() - 1);
    long l3 = l1 - l2;
    
    if (e.containsKey(d)) {
      e.put(d, Long.valueOf(((Long)e.get(d)).longValue() + l3));
    } else {
      e.put(d, Long.valueOf(l3));
    }
    
    d = b.size() > 0 ? (String)b.get(b.size() - 1) : "";
    
    if (l3 > 100000000L) {
      System.out.println(d + " " + l3);
    }
  }
  










































  public static void b(String paramString)
  {
    a();
    a(paramString);
  }
}
