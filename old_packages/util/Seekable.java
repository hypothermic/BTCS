package org.ibex.nestedvm.util;

import java.io.IOException;

public abstract class Seekable {
  public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException;
  
  public abstract int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException;
  
  public abstract int length() throws IOException;
  
  public abstract void seek(int paramInt) throws IOException;
  
  public abstract void close() throws IOException;
  
  public abstract int pos() throws IOException;
  
  public void sync() throws IOException { throw new IOException("sync not implemented for " + getClass()); }
  
  public void resize(long paramLong) throws IOException {
    throw new IOException("resize not implemented for " + getClass());
  }
  
  public Lock lock(long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
    throw new IOException("lock not implemented for " + getClass());
  }
  
  public int read() throws IOException {
    byte[] arrayOfByte = new byte[1];
    int i = read(arrayOfByte, 0, 1);
    return i == -1 ? -1 : arrayOfByte[0] & 0xFF;
  }
  
  public int tryReadFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
    int i = 0;
    while (paramInt2 > 0) {
      int j = read(paramArrayOfByte, paramInt1, paramInt2);
      if (j == -1) break;
      paramInt1 += j;
      paramInt2 -= j;
      i += j;
    }
    return i == 0 ? -1 : i;
  }
  
  public static class ByteArray extends Seekable {
    protected byte[] data;
    protected int pos;
    private final boolean writable;
    
    public ByteArray(byte[] paramArrayOfByte, boolean paramBoolean) {
      this.data = paramArrayOfByte;
      this.pos = 0;
      this.writable = paramBoolean;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
      paramInt2 = Math.min(paramInt2, this.data.length - this.pos);
      if (paramInt2 <= 0) return -1;
      System.arraycopy(this.data, this.pos, paramArrayOfByte, paramInt1, paramInt2);
      this.pos += paramInt2;
      return paramInt2;
    }
    
    public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
      if (!this.writable) throw new IOException("read-only data");
      paramInt2 = Math.min(paramInt2, this.data.length - this.pos);
      if (paramInt2 <= 0) throw new IOException("no space");
      System.arraycopy(paramArrayOfByte, paramInt1, this.data, this.pos, paramInt2);
      this.pos += paramInt2;
      return paramInt2;
    }
    
    public int length() { return this.data.length; }
    public int pos() { return this.pos; }
    public void seek(int paramInt) { this.pos = paramInt; }
    
    public void close() {}
  }
  
  public static class File extends Seekable { private final java.io.File file;
    private final java.io.RandomAccessFile raf;
    
    public File(String paramString) throws IOException { this(paramString, false); }
    public File(String paramString, boolean paramBoolean) throws IOException { this(new java.io.File(paramString), paramBoolean, false); }
    
    public File(java.io.File paramFile, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
      this.file = paramFile;
      String str = paramBoolean1 ? "rw" : "r";
      this.raf = new java.io.RandomAccessFile(paramFile, str);
      if (paramBoolean2) Platform.setFileLength(this.raf, 0);
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { return this.raf.read(paramArrayOfByte, paramInt1, paramInt2); }
    public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { this.raf.write(paramArrayOfByte, paramInt1, paramInt2);return paramInt2; }
    public void sync() throws IOException { this.raf.getFD().sync(); }
    public void seek(int paramInt) throws IOException { this.raf.seek(paramInt); }
    public int pos() throws IOException { return (int)this.raf.getFilePointer(); }
    public int length() throws IOException { return (int)this.raf.length(); }
    public void close() throws IOException { this.raf.close(); }
    public void resize(long paramLong) throws IOException { Platform.setFileLength(this.raf, (int)paramLong); }
    
    public boolean equals(Object paramObject) { return (paramObject != null) && ((paramObject instanceof File)) && (this.file.equals(((File)paramObject).file)); }
    
    public Seekable.Lock lock(long paramLong1, long paramLong2, boolean paramBoolean)
      throws IOException
    {
      return Platform.lockFile(this, this.raf, paramLong1, paramLong2, paramBoolean);
    }
  }
  
  public static class InputStream extends Seekable {
    private byte[] buffer = new byte['က'];
    private int bytesRead = 0;
    private boolean eof = false;
    private int pos;
    private java.io.InputStream is;
    
    public InputStream(java.io.InputStream paramInputStream) { this.is = paramInputStream; }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
      if ((this.pos >= this.bytesRead) && (!this.eof)) readTo(this.pos + 1);
      paramInt2 = Math.min(paramInt2, this.bytesRead - this.pos);
      if (paramInt2 <= 0) return -1;
      System.arraycopy(this.buffer, this.pos, paramArrayOfByte, paramInt1, paramInt2);
      this.pos += paramInt2;
      return paramInt2;
    }
    
    private void readTo(int paramInt) throws IOException {
      if (paramInt >= this.buffer.length) {
        byte[] arrayOfByte = new byte[Math.max(this.buffer.length + Math.min(this.buffer.length, 65536), paramInt)];
        System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.bytesRead);
        this.buffer = arrayOfByte;
      }
      while (this.bytesRead < paramInt) {
        int i = this.is.read(this.buffer, this.bytesRead, this.buffer.length - this.bytesRead);
        if (i == -1) {
          this.eof = true;
          break;
        }
        this.bytesRead += i;
      }
    }
    
    public int length() throws IOException {
      while (!this.eof) readTo(this.bytesRead + 4096);
      return this.bytesRead;
    }
    
    public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { throw new IOException("read-only"); }
    public void seek(int paramInt) { this.pos = paramInt; }
    public int pos() { return this.pos; }
    public void close() throws IOException { this.is.close(); } }
  public static abstract class Lock { public abstract Seekable seekable();
    public abstract boolean isShared();
    public abstract boolean isValid();
    private Object owner = null;
    

    public abstract void release() throws IOException;
    
    public abstract long position();
    
    public abstract long size();
    
    public void setOwner(Object paramObject) { this.owner = paramObject; }
    public Object getOwner() { return this.owner; }
    
    public final boolean contains(int paramInt1, int paramInt2) {
      return (paramInt1 >= position()) && (position() + size() >= paramInt1 + paramInt2);
    }
    
    public final boolean contained(int paramInt1, int paramInt2) {
      return (paramInt1 < position()) && (position() + size() < paramInt1 + paramInt2);
    }
    
    public final boolean overlaps(int paramInt1, int paramInt2) {
      return (contains(paramInt1, paramInt2)) || (contained(paramInt1, paramInt2));
    }
  }
}
