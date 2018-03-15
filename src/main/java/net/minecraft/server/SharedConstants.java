package net.minecraft.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SharedConstants
{
  private static String a()
  {
    String str1 = "";
    try {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(SharedConstants.class.getResourceAsStream("/font.txt"), "UTF-8"));
      String str2 = "";
      while ((str2 = localBufferedReader.readLine()) != null) {
        if (!str2.startsWith("#")) {
          str1 = str1 + str2;
        }
      }
      localBufferedReader.close();
    }
    catch (Exception localException) {}
    return str1;
  }
  

  public static final String allowedCharacters = a();
  
  public static final boolean isAllowedChatCharacter(char paramChar) {
    return (paramChar != '§') && ((allowedCharacters.indexOf(paramChar) >= 0) || (paramChar > ' '));
  }
  
  public static final char[] b = { '/', '\n', '\r', '\t', '\000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
}
