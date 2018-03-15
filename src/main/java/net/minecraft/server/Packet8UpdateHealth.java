package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet
{
  public int a;
  public int b;
  public float c;
  
  public Packet8UpdateHealth() {}
  
  public Packet8UpdateHealth(int paramInt1, int paramInt2, float paramFloat)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramFloat;
  }
  
  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.a = paramDataInputStream.readShort();
    this.b = paramDataInputStream.readShort();
    this.c = paramDataInputStream.readFloat();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X176 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream)
  {
	  try {
    paramDataOutputStream.writeShort(this.a);
    paramDataOutputStream.writeShort(this.b);
    paramDataOutputStream.writeFloat(this.c);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X177 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 8;
  }
}
