package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet104WindowItems extends Packet
{
  public int a;
  public ItemStack[] b;
  
  public Packet104WindowItems() {}
  
  public Packet104WindowItems(int paramInt, List paramList)
  {
    this.a = paramInt;
    this.b = new ItemStack[paramList.size()];
    for (int i = 0; i < this.b.length; i++) {
      ItemStack localItemStack = (ItemStack)paramList.get(i);
      this.b[i] = (localItemStack == null ? null : localItemStack.cloneItemStack());
    }
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readByte();
    int i = paramDataInputStream.readShort();
    this.b = new ItemStack[i];
    for (int j = 0; j < i; j++) {
      this.b[j] = b(paramDataInputStream);
    }} catch (IOException x) {
    	System.out.println("BTCS: Exception X49 happened in Packet");
    	x.printStackTrace();
    }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.a);
    paramDataOutputStream.writeShort(this.b.length);
    for (int i = 0; i < this.b.length; i++) {
      a(this.b[i], paramDataOutputStream);
    }} catch (IOException x) {
    	System.out.println("BTCS: Exception X50 happened in Packet");
    	x.printStackTrace();
    }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 3 + this.b.length * 5;
  }
}
