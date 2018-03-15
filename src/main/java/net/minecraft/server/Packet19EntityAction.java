package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet19EntityAction
  extends Packet
{
  public int a;
  public int animation;
  
  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.a = paramDataInputStream.readInt();
    this.animation = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X90 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.animation);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X91 happened in Packet");
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
