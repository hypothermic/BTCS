package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5EntityEquipment extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  
  public Packet5EntityEquipment() {}
  
  public Packet5EntityEquipment(int paramInt1, int paramInt2, ItemStack paramItemStack)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    if (paramItemStack == null) {
      this.c = -1;
      this.d = 0;
    } else {
      this.c = paramItemStack.id;
      this.d = paramItemStack.getData();
    }
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readShort();
    this.c = paramDataInputStream.readShort();
    this.d = paramDataInputStream.readShort();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X160 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeShort(this.b);
    paramDataOutputStream.writeShort(this.c);
    paramDataOutputStream.writeShort(this.d);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X161 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 8;
  }
}
