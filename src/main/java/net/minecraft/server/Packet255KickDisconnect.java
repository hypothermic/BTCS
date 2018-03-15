package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet
{
  public String a;
  
  public Packet255KickDisconnect() {}
  
  public Packet255KickDisconnect(String paramString) {
    this.a = paramString;
  }
  
  public void a(DataInputStream paramDataInputStream) {
    try {
		this.a = a(paramDataInputStream, 256);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X108 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
    try {
		a(this.a, paramDataOutputStream);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X109 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return this.a.length();
  }
}
