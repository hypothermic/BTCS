package net.minecraft.server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class AchievementMap
{
  public static AchievementMap a = new AchievementMap();
  
  private Map b = new HashMap();
  
  private AchievementMap() {
    try {
      BufferedReader localBufferedReader = new BufferedReader(new java.io.InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));
      String str;
      while ((str = localBufferedReader.readLine()) != null) {
        String[] arrayOfString = str.split(",");
        int i = Integer.parseInt(arrayOfString[0]);
        this.b.put(Integer.valueOf(i), arrayOfString[1]);
      }
      localBufferedReader.close();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  public static String a(int paramInt) {
    return (String)a.b.get(Integer.valueOf(paramInt));
  }
}
