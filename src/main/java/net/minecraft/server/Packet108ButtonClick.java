package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;





public class Packet108ButtonClick
  extends Packet
{
  public int a;
  public int b;
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readByte();
    this.b = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X60 happened in Packet");
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.a);
    paramDataOutputStream.writeByte(this.b);
	  } catch (IOException x) {
		  
	  }
  }
  
  public int a() {
    return 2;
  }
}
