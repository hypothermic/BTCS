package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet25EntityPainting extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  public String f;
  
  public Packet25EntityPainting() {}
  
  public Packet25EntityPainting(EntityPainting paramEntityPainting)
  {
    this.a = paramEntityPainting.id;
    this.b = paramEntityPainting.x;
    this.c = paramEntityPainting.y;
    this.d = paramEntityPainting.z;
    this.e = paramEntityPainting.direction;
    this.f = paramEntityPainting.art.A;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.f = a(paramDataInputStream, EnumArt.z);
    this.b = paramDataInputStream.readInt();
    this.c = paramDataInputStream.readInt();
    this.d = paramDataInputStream.readInt();
    this.e = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X110 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    a(this.f, paramDataOutputStream);
    paramDataOutputStream.writeInt(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.writeInt(this.d);
    paramDataOutputStream.writeInt(this.e);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X111 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 24;
  }
}
