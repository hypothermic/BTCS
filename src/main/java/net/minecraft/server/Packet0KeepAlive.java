package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet0KeepAlive extends Packet
{
  public int a;
  
  public Packet0KeepAlive() {}
  
  public Packet0KeepAlive(int paramInt) {
    this.a = paramInt;
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
    try {
		this.a = paramDataInputStream.readInt();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X35 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
    try {
		paramDataOutputStream.writeInt(this.a);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X36 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public int a() {
    return 4;
  }
}
