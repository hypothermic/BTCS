package net.minecraft.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;




public class MojangStatisticsGenerator
{
  private Map a = new HashMap();
  private final URL b;
  
  public MojangStatisticsGenerator(String paramString) {
    try {
      this.b = new URL("http://snoop.minecraft.net/" + paramString);
    } catch (MalformedURLException localMalformedURLException) {
      throw new IllegalArgumentException();
    }
  }
  
  public void a(String paramString, Object paramObject) {
    this.a.put(paramString, paramObject);
  }
  
  public void a() {
    MojangStatisticsThread localMojangStatisticsThread = new MojangStatisticsThread(this, "reporter");
    localMojangStatisticsThread.setDaemon(true);
    localMojangStatisticsThread.start();
  }
  
  // BTCS start
  static URL a(MojangStatisticsGenerator mojangstatisticsgenerator) {
      return mojangstatisticsgenerator.b;
  }

  static Map b(MojangStatisticsGenerator mojangstatisticsgenerator) {
      return mojangstatisticsgenerator.a;
  }
  // BTCS end
}
