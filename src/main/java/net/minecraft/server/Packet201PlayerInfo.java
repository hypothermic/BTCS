package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet201PlayerInfo extends Packet
{
  public String a;
  public boolean b;
  public int c;
  
  public Packet201PlayerInfo() {}
  
  public Packet201PlayerInfo(String paramString, boolean paramBoolean, int paramInt)
  {
    this.a = paramString;
    this.b = paramBoolean;
    this.c = paramInt;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = a(paramDataInputStream, 16);
    this.b = (paramDataInputStream.readByte() != 0);
    this.c = paramDataInputStream.readShort();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X96 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    a(this.a, paramDataOutputStream);
    paramDataOutputStream.writeByte(this.b ? 1 : 0);
    paramDataOutputStream.writeShort(this.c);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X97 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return this.a.length() + 2 + 1 + 2;
  }
}
