package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;







public class Packet107SetCreativeSlot
  extends Packet
{
  public int slot;
  public ItemStack b;
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.slot = paramDataInputStream.readShort();
    this.b = b(paramDataInputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X58 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeShort(this.slot);
    a(this.b, paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X59 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 8;
  }
}
