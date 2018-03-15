package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet200Statistic extends Packet
{
  public int a;
  public int b;
  
  public Packet200Statistic() {}
  
  public Packet200Statistic(int paramInt1, int paramInt2)
  {
    this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X94 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.b);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X95 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 6;
  }
}
