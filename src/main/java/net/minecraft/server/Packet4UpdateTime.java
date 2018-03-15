package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet4UpdateTime extends Packet
{
  public long a;
  
  public Packet4UpdateTime() {}
  
  public Packet4UpdateTime(long paramLong) {
    this.a = paramLong;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readLong();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X150 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeLong(this.a);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X151 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 8;
  }
}
