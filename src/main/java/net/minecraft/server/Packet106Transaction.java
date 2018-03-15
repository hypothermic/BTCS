package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet106Transaction extends Packet
{
  public int a;
  public short b;
  public boolean c;
  
  public Packet106Transaction() {}
  
  public Packet106Transaction(int paramInt, short paramShort, boolean paramBoolean)
  {
    this.a = paramInt;
    this.b = paramShort;
    this.c = paramBoolean;
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readByte();
    this.b = paramDataInputStream.readShort();
    this.c = (paramDataInputStream.readByte() != 0);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X55 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.a);
    paramDataOutputStream.writeShort(this.b);
    paramDataOutputStream.writeByte(this.c ? 1 : 0);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X56 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 4;
  }
}
