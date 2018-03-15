package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet17EntityLocationAction extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  
  public Packet17EntityLocationAction() {}
  
  public Packet17EntityLocationAction(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.e = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramInt4;
    this.a = paramEntity.id;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.e = paramDataInputStream.readByte();
    this.b = paramDataInputStream.readInt();
    this.c = paramDataInputStream.readByte();
    this.d = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X87 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.e);
    paramDataOutputStream.writeInt(this.b);
    paramDataOutputStream.writeByte(this.c);
    paramDataOutputStream.writeInt(this.d);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X88 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 14;
  }
}
