package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;







public class Packet7UseEntity
  extends Packet
{
  public int a;
  public int target;
  public int action;
  
  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.a = paramDataInputStream.readInt();
    this.target = paramDataInputStream.readInt();
    this.action = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X174 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeInt(this.target);
    paramDataOutputStream.writeByte(this.action);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X175 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 9;
  }
}
