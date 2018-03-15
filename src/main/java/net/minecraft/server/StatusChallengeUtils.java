package net.minecraft.server;


public class StatusChallengeUtils
{
  public static char[] a = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  





  public static String a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - 1;
    int j = paramInt1 > i ? i : paramInt1;
    while ((0 != paramArrayOfByte[j]) && (j < i)) {
      j++;
    }
    
    return new String(paramArrayOfByte, paramInt1, j - paramInt1);
  }
  
  public static int a(byte[] paramArrayOfByte, int paramInt) {
    return b(paramArrayOfByte, paramInt, paramArrayOfByte.length);
  }
  
  public static int b(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
    if (0 > paramInt2 - paramInt1 - 4)
    {

      return 0;
    }
    return paramArrayOfByte[(paramInt1 + 3)] << 24 | (paramArrayOfByte[(paramInt1 + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 8 | paramArrayOfByte[paramInt1] & 0xFF;
  }
  



  public static int c(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (0 > paramInt2 - paramInt1 - 4)
    {

      return 0;
    }
    return paramArrayOfByte[paramInt1] << 24 | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt1 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt1 + 3)] & 0xFF;
  }
  









  public static String a(byte paramByte)
  {
    return "" + a[((paramByte & 0xF0) >>> 4)] + a[(paramByte & 0xF)];
  }
}
