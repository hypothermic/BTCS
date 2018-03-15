package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet35EntityHeadRotation extends Packet
{
  public int a;
  public byte b;
  
  public Packet35EntityHeadRotation() {}
  
  public Packet35EntityHeadRotation(int paramInt, byte paramByte) {
    this.a = paramInt;
    this.b = paramByte;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X132 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.b);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X133 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 5;
  }
}
