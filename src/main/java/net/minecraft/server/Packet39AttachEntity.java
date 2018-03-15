package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet39AttachEntity extends Packet
{
  public int a;
  public int b;
  
  public Packet39AttachEntity() {}
  
  public Packet39AttachEntity(Entity paramEntity1, Entity paramEntity2) {
    this.a = paramEntity1.id;
    this.b = (paramEntity2 != null ? paramEntity2.id : -1);
  }
  
  public int a() {
    return 8;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X136 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeInt(this.b);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X137 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
}
