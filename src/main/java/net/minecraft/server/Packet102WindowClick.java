package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;











public class Packet102WindowClick
  extends Packet
{
  public int a;
  public int slot;
  public int button;
  public short d;
  public ItemStack item;
  public boolean shift;
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readByte();
    this.slot = paramDataInputStream.readShort();
    this.button = paramDataInputStream.readByte();
    this.d = paramDataInputStream.readShort();
    this.shift = paramDataInputStream.readBoolean();
    
    this.item = b(paramDataInputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X44 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.a);
    paramDataOutputStream.writeShort(this.slot);
    paramDataOutputStream.writeByte(this.button);
    paramDataOutputStream.writeShort(this.d);
    paramDataOutputStream.writeBoolean(this.shift);
    
    a(this.item, paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X45 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 11;
  }
}
