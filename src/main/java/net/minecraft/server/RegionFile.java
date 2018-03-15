package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile
{
  private static final byte[] a = new byte['က'];
  
  private final File b;
  private RandomAccessFile c;
  private final int[] d;
  private final int[] e;
  private ArrayList f;
  private int g;
  private long h = 0L;
  
  public RegionFile(File paramFile) {
    this.d = new int['Ѐ'];
    this.e = new int['Ѐ'];
    
    this.b = paramFile;
    b("REGION LOAD " + this.b);
    
    this.g = 0;
    try
    {
      if (paramFile.exists()) {
        this.h = paramFile.lastModified();
      }
      
      this.c = new RandomAccessFile(paramFile, "rw");
      
      if (this.c.length() < 4096L)
      {
        for (int i = 0; i < 1024; i++) { // BTCS: added decl 'int '
          this.c.writeInt(0);
        }
        
        for (int i = 0; i < 1024; i++) { // BTCS: added decl 'int '
          this.c.writeInt(0);
        }
        
        this.g += 8192;
      }
      
      if ((this.c.length() & 0xFFF) != 0L)
      {
        for (int i = 0; i < (this.c.length() & 0xFFF); i++) { // BTCS: added decl 'int '
          this.c.write(0);
        }
      }
      

      int i = (int)this.c.length() / 4096;
      this.f = new ArrayList(i);
      
      for (int j = 0; j < i; j++) {
        this.f.add(Boolean.valueOf(true));
      }
      
      this.f.set(0, Boolean.valueOf(false));
      this.f.set(1, Boolean.valueOf(false));
      
      this.c.seek(0L);
      int k; 
      for (int j = 0; j < 1024; j++) {  // BTCS: added decl 'int '
        k = this.c.readInt();
        this.d[j] = k;
        if ((k != 0) && ((k >> 8) + (k & 0xFF) <= this.f.size())) {
          for (int m = 0; m < (k & 0xFF); m++) {
            this.f.set((k >> 8) + m, Boolean.valueOf(false));
          }
        }
      }
      for (int j = 0; j < 1024; j++) { // BTCS: added decl 'int '
        k = this.c.readInt();
        this.e[j] = k;
      }
    } catch (IOException localIOException) {
      localIOException.printStackTrace();
    }
  }
  







  private void a(String paramString) {}
  







  private void b(String paramString)
  {
    a(paramString + "\n");
  }
  
  private void a(String paramString1, int paramInt1, int paramInt2, String paramString2) {
    a("REGION " + paramString1 + " " + this.b.getName() + "[" + paramInt1 + "," + paramInt2 + "] = " + paramString2);
  }
  
  private void a(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2) {
    a("REGION " + paramString1 + " " + this.b.getName() + "[" + paramInt1 + "," + paramInt2 + "] " + paramInt3 + "B = " + paramString2);
  }
  
  private void b(String paramString1, int paramInt1, int paramInt2, String paramString2) {
    a(paramString1, paramInt1, paramInt2, paramString2 + "\n");
  }
  



  public synchronized DataInputStream a(int paramInt1, int paramInt2)
  {
    if (d(paramInt1, paramInt2)) {
      b("READ", paramInt1, paramInt2, "out of bounds");
      return null;
    }
    try
    {
      int i = e(paramInt1, paramInt2);
      if (i == 0)
      {
        return null;
      }
      
      int j = i >> 8;
      int k = i & 0xFF;
      
      if (j + k > this.f.size()) {
        b("READ", paramInt1, paramInt2, "invalid sector");
        return null;
      }
      
      this.c.seek(j * 4096);
      int m = this.c.readInt();
      
      if (m > 4096 * k) {
        b("READ", paramInt1, paramInt2, "invalid length: " + m + " > 4096 * " + k);
        return null; }
      if (m <= 0) {
        b("READ", paramInt1, paramInt2, "invalid length: " + m + " < 1");
        return null;
      }
      
      int n = this.c.readByte();
      byte[] arrayOfByte; DataInputStream localDataInputStream; if (n == 1) {
        arrayOfByte = new byte[m - 1];
        this.c.read(arrayOfByte);
        return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(arrayOfByte))));
      }
      
      if (n == 2) {
        arrayOfByte = new byte[m - 1];
        this.c.read(arrayOfByte);
        return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(arrayOfByte))));
      }
      


      b("READ", paramInt1, paramInt2, "unknown version " + n);
      return null;
    } catch (IOException localIOException) {
      b("READ", paramInt1, paramInt2, "exception"); }
    return null;
  }
  
  public DataOutputStream b(int paramInt1, int paramInt2)
  {
    if (d(paramInt1, paramInt2)) { return null;
    }
    return new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(this, paramInt1, paramInt2)));
  }
  

















  protected synchronized void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    try
    {
      int i = e(paramInt1, paramInt2);
      int j = i >> 8;
      int k = i & 0xFF;
      int m = (paramInt3 + 5) / 4096 + 1;
      

      if (m >= 256) {
        return;
      }
      
      if ((j != 0) && (k == m))
      {
        a("SAVE", paramInt1, paramInt2, paramInt3, "rewrite");
        a(j, paramArrayOfByte, paramInt3);

      }
      else
      {
        for (int n = 0; n < k; n++) {
          this.f.set(j + n, Boolean.valueOf(true));
        }
        

        int n = this.f.indexOf(Boolean.valueOf(true));  // BTCS: added decl 'int '
        int i1 = 0;
        int i2; if (n != -1) {
          for (i2 = n; i2 < this.f.size(); i2++) {
            if (i1 != 0) {
              if (((Boolean)this.f.get(i2)).booleanValue()) i1++; else
                i1 = 0;
            } else if (((Boolean)this.f.get(i2)).booleanValue()) {
              n = i2;
              i1 = 1;
            }
            if (i1 >= m) {
              break;
            }
          }
        }
        
        if (i1 >= m)
        {
          a("SAVE", paramInt1, paramInt2, paramInt3, "reuse");
          j = n;
          a(paramInt1, paramInt2, j << 8 | m);
          for (i2 = 0; i2 < m; i2++) {
            this.f.set(j + i2, Boolean.valueOf(false));
          }
          a(j, paramArrayOfByte, paramInt3);

        }
        else
        {

          a("SAVE", paramInt1, paramInt2, paramInt3, "grow");
          this.c.seek(this.c.length());
          j = this.f.size();
          for (i2 = 0; i2 < m; i2++) {
            this.c.write(a);
            this.f.add(Boolean.valueOf(false));
          }
          this.g += 4096 * m;
          
          a(j, paramArrayOfByte, paramInt3);
          a(paramInt1, paramInt2, j << 8 | m);
        }
      }
      b(paramInt1, paramInt2, (int)(System.currentTimeMillis() / 1000L));
    } catch (IOException localIOException) {
      localIOException.printStackTrace();
    }
  }
  
  private void a(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    b(" " + paramInt1);
    try { // BTCS: added try-catch
    this.c.seek(paramInt1 * 4096);
    this.c.writeInt(paramInt2 + 1);
    this.c.writeByte(2);
    this.c.write(paramArrayOfByte, 0, paramInt2);
    } catch (IOException x) {
    	System.out.println("BTCS: Exception X200 happened in RegionFile");
    	x.printStackTrace();
    }
  }
  
  private boolean d(int paramInt1, int paramInt2)
  {
    return (paramInt1 < 0) || (paramInt1 >= 32) || (paramInt2 < 0) || (paramInt2 >= 32);
  }
  
  private int e(int paramInt1, int paramInt2) {
    return this.d[(paramInt1 + paramInt2 * 32)];
  }
  
  public boolean c(int paramInt1, int paramInt2) {
    return e(paramInt1, paramInt2) != 0;
  }
  
  private void a(int paramInt1, int paramInt2, int paramInt3) {
	  try {
    this.d[(paramInt1 + paramInt2 * 32)] = paramInt3;
    this.c.seek((paramInt1 + paramInt2 * 32) * 4);
    this.c.writeInt(paramInt3);
	  } catch (IOException x) {
	    	System.out.println("BTCS: Exception X201 happened in RegionFile");
	    	x.printStackTrace();
	    }
  }
  
  private void b(int paramInt1, int paramInt2, int paramInt3) {
	  try {
    this.e[(paramInt1 + paramInt2 * 32)] = paramInt3;
    this.c.seek(4096 + (paramInt1 + paramInt2 * 32) * 4);
    this.c.writeInt(paramInt3);
	  } catch (IOException x) {
	    	System.out.println("BTCS: Exception X202 happened in RegionFile");
	    	x.printStackTrace();
	    }
  }
  
  public void a() {
	  try {
    this.c.close();
	  } catch (IOException x) {
	    	System.out.println("BTCS: Exception X203 happened in RegionFile");
	    	x.printStackTrace();
	    }
  }
}
