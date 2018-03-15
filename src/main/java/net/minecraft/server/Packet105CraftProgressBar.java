package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet105CraftProgressBar extends Packet
{
  public int a;
  public int b;
  public int c;
  
  public Packet105CraftProgressBar() {}
  
  public Packet105CraftProgressBar(int paramInt1, int paramInt2, int paramInt3)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readByte();
    this.b = paramDataInputStream.readShort();
    this.c = paramDataInputStream.readShort();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X52 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.a);
    paramDataOutputStream.writeShort(this.b);
    paramDataOutputStream.writeShort(this.c);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X53 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 5;
  }
}
