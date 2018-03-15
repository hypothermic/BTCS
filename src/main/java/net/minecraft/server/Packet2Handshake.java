package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet2Handshake extends Packet
{
  public String a;
  
  public Packet2Handshake() {}
  
  public Packet2Handshake(String paramString)
  {
    this.a = paramString;
  }
  



  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.a = a(paramDataInputStream, 64);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X120 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    a(this.a, paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X121 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 4 + this.a.length() + 4;
  }
}
