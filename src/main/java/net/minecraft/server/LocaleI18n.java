package net.minecraft.server;

public class LocaleI18n
{
  private static LocaleLanguage a = LocaleLanguage.a(); // BTCS: added '= LocaleLanguage.a()'
  
  public static String get(String paramString) {
    return a.b(paramString);
  }
  
  public static String get(String paramString, Object... paramVarArgs) {
    return a.a(paramString, paramVarArgs);
  }
}
