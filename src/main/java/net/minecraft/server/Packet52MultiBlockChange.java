package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;




public class Packet52MultiBlockChange
  extends Packet
{
  public int a;
  public int b;
  public byte[] c;
  public int d;
  private static byte[] e = new byte[0];
  
  public Packet52MultiBlockChange() {
    this.lowPriority = true;
  }
  
  public Packet52MultiBlockChange(int paramInt1, int paramInt2, short[] paramArrayOfShort, int paramInt3, World paramWorld) {
    this.lowPriority = true;
    this.a = paramInt1;
    this.b = paramInt2;
    this.d = paramInt3;
    
    int i = 4 * paramInt3;
    Chunk localChunk = paramWorld.getChunkAt(paramInt1, paramInt2);
    try
    {
      if (paramInt3 >= 64) {
        System.out.println("ChunkTilesUpdatePacket compress " + paramInt3);
        if (e.length < i) {
          e = new byte[i];
        }
        
      }
      else
      {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(i);
        DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
        
        for (int j = 0; j < paramInt3; j++) {
          int k = paramArrayOfShort[j] >> 12 & 0xF;
          int m = paramArrayOfShort[j] >> 8 & 0xF;
          int n = paramArrayOfShort[j] & 0xFF;
          
          localDataOutputStream.writeShort(paramArrayOfShort[j]);
          localDataOutputStream.writeShort((short)((localChunk.getTypeId(k, n, m) & 0xFFF) << 4 | localChunk.getData(k, n, m) & 0xF));
        }
        
        this.c = localByteArrayOutputStream.toByteArray();
        if (this.c.length != i) {
          throw new RuntimeException("Expected length " + i + " doesn't match received length " + this.c.length);
        }
      }
    } catch (IOException localIOException) {
      System.err.println(localIOException.getMessage());
      this.c = null;
    }
  }
  












  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readInt();
    
    this.d = (paramDataInputStream.readShort() & 0xFFFF);
    int i = paramDataInputStream.readInt();
    if (i > 0) {
      this.c = new byte[i];
      paramDataInputStream.readFully(this.c);
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X156 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeInt(this.b);
    paramDataOutputStream.writeShort((short)this.d);
    if (this.c != null) {
      paramDataOutputStream.writeInt(this.c.length);
      paramDataOutputStream.write(this.c);
    } else {
      paramDataOutputStream.writeInt(0);
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X157 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 10 + this.d * 4;
  }
}
