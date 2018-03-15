package org.ibex.nestedvm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

public abstract class Platform
{
  private static final Platform p;
  
  static
  {
    float f;
    try
    {
      if (getProperty("java.vm.name").equals("SableVM")) {
        f = 1.2F;
      } else
        f = Float.valueOf(getProperty("java.specification.version")).floatValue();
    } catch (Exception localException1) {
      System.err.println("WARNING: " + localException1 + " while trying to find jvm version -  assuming 1.1");
      f = 1.1F;
    }
    String str;
    if (f >= 1.4F) { str = "Jdk14";
    } else if (f >= 1.3F) { str = "Jdk13";
    } else if (f >= 1.2F) { str = "Jdk12";
    } else if (f >= 1.1F) str = "Jdk11"; else {
      throw new Error("JVM Specification version: " + f + " is too old. (see org.ibex.util.Platform to add support)");
    }
    try {
      p = (Platform)Class.forName(Platform.class.getName() + "$" + str).newInstance();
    } catch (Exception localException2) {
      localException2.printStackTrace();
      throw new Error("Error instansiating platform class");
    }
  }
  
  public static String getProperty(String paramString) {
    try {
      return System.getProperty(paramString);
    } catch (SecurityException localSecurityException) {}
    return null;
  }
  
  public static boolean atomicCreateFile(File paramFile)
    throws IOException
  {
    return p._atomicCreateFile(paramFile);
  }
  
  public static Seekable.Lock lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
    return p._lockFile(paramSeekable, paramRandomAccessFile, paramLong1, paramLong2, paramBoolean);
  }
  
  public static void socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException { p._socketHalfClose(paramSocket, paramBoolean); }
  
  public static void socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException {
    p._socketSetKeepAlive(paramSocket, paramBoolean);
  }
  
  public static InetAddress inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException { return p._inetAddressFromBytes(paramArrayOfByte); }
  

  public static String timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale) { return p._timeZoneGetDisplayName(paramTimeZone, paramBoolean1, paramBoolean2, paramLocale); }
  public static String timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2) { return timeZoneGetDisplayName(paramTimeZone, paramBoolean1, paramBoolean2, Locale.getDefault()); }
  
  public static void setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt)
    throws IOException
  {
    p._setFileLength(paramRandomAccessFile, paramInt);
  }
  
  public static File[] listRoots() { return p._listRoots(); }
  

  public static File getRoot(File paramFile) { return p._getRoot(paramFile); }
  
  abstract boolean _atomicCreateFile(File paramFile) throws IOException;
  
  static class Jdk11 extends Platform {
    boolean _atomicCreateFile(File paramFile) throws IOException { if (paramFile.exists()) return false;
      new FileOutputStream(paramFile).close();
      return true;
    }
    
    Seekable.Lock _lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException { throw new IOException("file locking requires jdk 1.4+"); }
    

    void _socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException { throw new IOException("half closing sockets not supported"); }
    
    InetAddress _inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException {
      if (paramArrayOfByte.length != 4) throw new UnknownHostException("only ipv4 addrs supported");
      return InetAddress.getByName("" + (paramArrayOfByte[0] & 0xFF) + "." + (paramArrayOfByte[1] & 0xFF) + "." + (paramArrayOfByte[2] & 0xFF) + "." + (paramArrayOfByte[3] & 0xFF));
    }
    
    void _socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException { if (paramBoolean) throw new SocketException("keepalive not supported");
    }
    
    String _timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale) { String[][] arrayOfString = new java.text.DateFormatSymbols(paramLocale).getZoneStrings();
      String str = paramTimeZone.getID();
      for (int i = 0; i < arrayOfString.length; i++)
        if (arrayOfString[i][0].equals(str))
          return arrayOfString[i][2];
      StringBuffer localStringBuffer = new StringBuffer("GMT");
      int j = paramTimeZone.getRawOffset() / 1000;
      if (j < 0) { localStringBuffer.append("-");j = -j;
      } else { localStringBuffer.append("+"); }
      localStringBuffer.append(j / 3600);j %= 3600;
      if (j > 0) localStringBuffer.append(":").append(j / 60); j %= 60;
      if (j > 0) localStringBuffer.append(":").append(j);
      return localStringBuffer.toString();
    }
    
    void _setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException {
      FileInputStream localFileInputStream = new FileInputStream(paramRandomAccessFile.getFD());
      FileOutputStream localFileOutputStream = new FileOutputStream(paramRandomAccessFile.getFD());
      
      byte[] arrayOfByte = new byte['Ѐ'];
      for (; paramInt > 0; paramInt -= i) {
        i = localFileInputStream.read(arrayOfByte, 0, Math.min(paramInt, arrayOfByte.length));
        if (i == -1) break;
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
      if (paramInt == 0) { return;
      }
      
      for (int i = 0; i < arrayOfByte.length; i++) arrayOfByte[i] = 0;
      while (paramInt > 0) {
        localFileOutputStream.write(arrayOfByte, 0, Math.min(paramInt, arrayOfByte.length));
        paramInt -= arrayOfByte.length;
      }
    }
    
    RandomAccessFile _truncatedRandomAccessFile(File paramFile, String paramString) throws IOException {
      new FileOutputStream(paramFile).close();
      return new RandomAccessFile(paramFile, paramString);
    }
    
    File[] _listRoots() {
      String[] arrayOfString = { "java.home", "java.class.path", "java.library.path", "java.io.tmpdir", "java.ext.dirs", "user.home", "user.dir" };
      Hashtable localHashtable = new Hashtable();
      for (int i = 0; i < arrayOfString.length; i++) {
        String str = getProperty(arrayOfString[i]);
        if (str != null)
          for (;;) {
            localObject = str;
            int k;
            if ((k = str.indexOf(File.pathSeparatorChar)) != -1) {
              localObject = str.substring(0, k);
              str = str.substring(k + 1);
            }
            File localFile = getRoot(new File((String)localObject));
            
            localHashtable.put(localFile, Boolean.TRUE);
            if (k == -1) break;
          }
      }
      File[] arrayOfFile = new File[localHashtable.size()];
      int j = 0;
      for (Object localObject = localHashtable.keys(); ((Enumeration)localObject).hasMoreElements();)
        arrayOfFile[(j++)] = ((File)((Enumeration)localObject).nextElement());
      return arrayOfFile;
    }
    
    File _getRoot(File paramFile) {
      if (!paramFile.isAbsolute()) paramFile = new File(paramFile.getAbsolutePath());
      String str;
      while ((str = paramFile.getParent()) != null) paramFile = new File(str);
      if (paramFile.getPath().length() == 0) paramFile = new File("/");
      return paramFile;
    }
  }
  
  abstract Seekable.Lock _lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException;
  
  static class Jdk12 extends Platform.Jdk11 { boolean _atomicCreateFile(File paramFile) throws IOException { return paramFile.createNewFile(); }
    
    String _timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale)
    {
      return paramTimeZone.getDisplayName(paramBoolean1, paramBoolean2 ? 1 : 0, paramLocale);
    }
    
    void _setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException {
      paramRandomAccessFile.setLength(paramInt);
    }
    
    File[] _listRoots() { return File.listRoots(); }
  }
  
  abstract void _socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException;
  
  static class Jdk13 extends Platform.Jdk12 { void _socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException { if (paramBoolean) paramSocket.shutdownOutput(); else {
        paramSocket.shutdownInput();
      }
    }
    
    void _socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException { paramSocket.setKeepAlive(paramBoolean); }
  }
  
  abstract void _socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException;
  
  static class Jdk14 extends Platform.Jdk13 { InetAddress _inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException { return InetAddress.getByAddress(paramArrayOfByte); }
    
    Seekable.Lock _lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
      FileLock localFileLock;
      try {
        localFileLock = (paramLong1 == 0L) && (paramLong2 == 0L) ? paramRandomAccessFile.getChannel().lock() : paramRandomAccessFile.getChannel().tryLock(paramLong1, paramLong2, paramBoolean);
      } catch (OverlappingFileLockException localOverlappingFileLockException) {
        localFileLock = null; }
      if (localFileLock == null) return null;
      return new Platform.Jdk14FileLock(paramSeekable, localFileLock); } }
  
  abstract InetAddress _inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException;
  
  abstract String _timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale);
  
  abstract void _setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException;
  
  private static final class Jdk14FileLock extends Seekable.Lock { Jdk14FileLock(Seekable paramSeekable, FileLock paramFileLock) { this.s = paramSeekable;this.l = paramFileLock; }
    public Seekable seekable() { return this.s; }
    public boolean isShared() { return this.l.isShared(); }
    public boolean isValid() { return this.l.isValid(); }
    public void release() throws IOException { this.l.release(); }
    public long position() { return this.l.position(); }
    public long size() { return this.l.size(); }
    public String toString() { return this.l.toString(); }
    
    private final Seekable s;
    private final FileLock l;
  }
  
  abstract File[] _listRoots();
  
  abstract File _getRoot(File paramFile);
}
