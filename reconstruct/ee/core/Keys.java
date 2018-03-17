package ee.core;

public class Keys
{
  public static final int EXTRA = 0;
  public static final int CHARGE = 1;
  public static final int TOGGLE = 2;
  public static final int RELEASE = 3;
  public static final int LEFT_CLICK = 4;
  public static final int JUMP = 5;
  public static final int SNEAK = 6;
  
  public static String toString(int var0)
  {
    switch (var0)
    {
    case 0: 
      return "KEYS.EXTRA";
    case 1: 
      return "KEYS.CHARGE";
    case 2: 
      return "KEYS.TOGGLE";
    case 3: 
      return "KEYS.RELEASE";
    case 4: 
      return "KEYS.LEFT_CLICK";
    case 5: 
      return "KEYS.JUMP";
    case 6: 
      return "KEYS.SNEAK";
    }
    return "Unknown Key";
  }
}
