package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;





public class Packet70Bed
  extends Packet
{
  public static final String[] a = { "tile.bed.notValid", null, null, "gameMode.changed" };
  
  public int b;
  
  public int c;
  

  public Packet70Bed() {}
  
  public Packet70Bed(int paramInt1, int paramInt2)
  {
    this.b = paramInt1;
    this.c = paramInt2;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.b = paramDataInputStream.readByte();
    this.c = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X170 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.b);
    paramDataOutputStream.writeByte(this.c);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X171 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 2;
  }
}
