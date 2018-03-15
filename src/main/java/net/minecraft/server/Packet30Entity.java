package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet30Entity
  extends Packet
{
  public int a;
  public byte b;
  public byte c;
  public byte d;
  public byte e;
  public byte f;
  public boolean g = false;
  
  public Packet30Entity() {}
  
  public Packet30Entity(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X122 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X123 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 4;
  }
}
