package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet131ItemData extends Packet
{
  public short a;
  public short b;
  public byte[] c;
  
  public Packet131ItemData()
  {
    this.lowPriority = true;
  }
  
  public Packet131ItemData(short paramShort1, short paramShort2, byte[] paramArrayOfByte) {
    this.lowPriority = true;
    this.a = paramShort1;
    this.b = paramShort2;
    this.c = paramArrayOfByte;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readShort();
    this.b = paramDataInputStream.readShort();
    this.c = new byte[paramDataInputStream.readByte() & 0xFF];
    paramDataInputStream.readFully(this.c);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X69 (lennyface) happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeShort(this.a);
    paramDataOutputStream.writeShort(this.b);
    paramDataOutputStream.writeByte(this.c.length);
    paramDataOutputStream.write(this.c);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X70 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 4 + this.c.length;
  }
}
