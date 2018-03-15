package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet43SetExperience extends Packet
{
  public float a;
  public int b;
  public int c;
  
  public Packet43SetExperience() {}
  
  public Packet43SetExperience(float paramFloat, int paramInt1, int paramInt2) {
    this.a = paramFloat;
    this.b = paramInt1;
    this.c = paramInt2;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readFloat();
    this.c = paramDataInputStream.readShort();
    this.b = paramDataInputStream.readShort();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X148 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeFloat(this.a);
    paramDataOutputStream.writeShort(this.c);
    paramDataOutputStream.writeShort(this.b);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X149 happened in Packet");
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
