package net.minecraft.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtilities
{
  public static String a(Map<?, ?> paramMap) // BTCS: added '<?, ?>'
  {
    StringBuilder localStringBuilder = new StringBuilder();
    
    for (Map.Entry localEntry : paramMap.entrySet()) {
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append('&');
      }
      try
      {
        localStringBuilder.append(java.net.URLEncoder.encode((String)localEntry.getKey(), "UTF-8"));
      } catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
        localUnsupportedEncodingException1.printStackTrace();
      }
      
      if (localEntry.getValue() != null) {
        localStringBuilder.append('=');
        try {
          localStringBuilder.append(java.net.URLEncoder.encode(localEntry.getValue().toString(), "UTF-8"));
        } catch (UnsupportedEncodingException localUnsupportedEncodingException2) {
          localUnsupportedEncodingException2.printStackTrace();
        }
      }
    }
    
    return localStringBuilder.toString();
  }
  
  public static String a(URL paramURL, Map paramMap, boolean paramBoolean) {
    return a(paramURL, a(paramMap), paramBoolean);
  }
  
  public static String a(URL paramURL, String paramString, boolean paramBoolean) {
    try {
      String str1 = paramString;
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection();
      localHttpURLConnection.setRequestMethod("POST");
      localHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      
      localHttpURLConnection.setRequestProperty("Content-Length", "" + str1.getBytes().length);
      localHttpURLConnection.setRequestProperty("Content-Language", "en-US");
      
      localHttpURLConnection.setUseCaches(false);
      localHttpURLConnection.setDoInput(true);
      localHttpURLConnection.setDoOutput(true);
      

      DataOutputStream localDataOutputStream = new DataOutputStream(localHttpURLConnection.getOutputStream());
      localDataOutputStream.writeBytes(str1);
      localDataOutputStream.flush();
      localDataOutputStream.close();
      

      BufferedReader localBufferedReader = new BufferedReader(new java.io.InputStreamReader(localHttpURLConnection.getInputStream()));
      
      StringBuffer localStringBuffer = new StringBuffer();
      String str2;
      while ((str2 = localBufferedReader.readLine()) != null) {
        localStringBuffer.append(str2);
        localStringBuffer.append('\r');
      }
      
      localBufferedReader.close();
      return localStringBuffer.toString();
    } catch (Exception localException) {
      if (!paramBoolean)
        java.util.logging.Logger.getLogger("Minecraft").log(java.util.logging.Level.SEVERE, "Could not post to " + paramURL, localException);
    }
    return "";
  }
}
