package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;







public class Packet15Place
  extends Packet
{
  public int a;
  public int b;
  public int c;
  public int face;
  public ItemStack itemstack;
  
  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.read();
    this.c = paramDataInputStream.readInt();
    this.face = paramDataInputStream.read();
    this.itemstack = b(paramDataInputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X80 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.write(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.write(this.face);
    a(this.itemstack, paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X81 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 15;
  }
}
