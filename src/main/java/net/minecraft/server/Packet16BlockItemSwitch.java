package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;




public class Packet16BlockItemSwitch
  extends Packet
{
  public int itemInHandIndex;
  
  public void a(DataInputStream paramDataInputStream)
  {
    try {
		this.itemInHandIndex = paramDataInputStream.readShort();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X85 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
    try {
		paramDataOutputStream.writeShort(this.itemInHandIndex);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X86 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 2;
  }
}
