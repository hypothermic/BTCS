package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet32EntityLook
  extends Packet30Entity
{
  public Packet32EntityLook()
  {
    this.g = true;
  }
  
  public Packet32EntityLook(int paramInt, byte paramByte1, byte paramByte2) {
    super(paramInt);
    this.e = paramByte1;
    this.f = paramByte2;
    this.g = true;
  }
  
  public void a(DataInputStream paramDataInputStream) {
    super.a(paramDataInputStream);
    try {
    this.e = paramDataInputStream.readByte();
    this.f = paramDataInputStream.readByte();
    } catch (IOException x) {
		  System.out.println("BTCS: Exception X126 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
    super.a(paramDataOutputStream);
    try {
    paramDataOutputStream.writeByte(this.e);
    paramDataOutputStream.writeByte(this.f);
    } catch (IOException x) {
		  System.out.println("BTCS: Exception X127 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 6;
  }
}
