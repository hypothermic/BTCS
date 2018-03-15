package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;




public class Packet103SetSlot
  extends Packet
{
  public int a;
  public int b;
  public ItemStack c;
  
  public Packet103SetSlot() {}
  
  public Packet103SetSlot(int paramInt1, int paramInt2, ItemStack paramItemStack)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = (paramItemStack == null ? paramItemStack : paramItemStack.cloneItemStack());
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readByte();
    this.b = paramDataInputStream.readShort();
    this.c = b(paramDataInputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X47 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.a);
    paramDataOutputStream.writeShort(this.b);
    a(this.c, paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X48 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 8;
  }
}
