package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet101CloseWindow extends Packet
{
  public int a;
  
  public Packet101CloseWindow() {}
  
  public Packet101CloseWindow(int paramInt) {
    this.a = paramInt;
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.handleContainerClose(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
    try {
		this.a = paramDataInputStream.readByte();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X40 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
    try {
		paramDataOutputStream.writeByte(this.a);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X41 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public int a() {
    return 1;
  }
}
