package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet33RelEntityMoveLook extends Packet30Entity
{
  public Packet33RelEntityMoveLook() {
    this.g = true;
  }
  
  public Packet33RelEntityMoveLook(int paramInt, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5) {
    super(paramInt);
    this.b = paramByte1;
    this.c = paramByte2;
    this.d = paramByte3;
    this.e = paramByte4;
    this.f = paramByte5;
    this.g = true;
  }
  
  public void a(DataInputStream paramDataInputStream) {
    super.a(paramDataInputStream);
    try {
    this.b = paramDataInputStream.readByte();
    this.c = paramDataInputStream.readByte();
    this.d = paramDataInputStream.readByte();
    this.e = paramDataInputStream.readByte();
    this.f = paramDataInputStream.readByte();
    } catch (IOException x) {
		  System.out.println("BTCS: Exception X128 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
    super.a(paramDataOutputStream);
    try {
    paramDataOutputStream.writeByte(this.b);
    paramDataOutputStream.writeByte(this.c);
    paramDataOutputStream.writeByte(this.d);
    paramDataOutputStream.writeByte(this.e);
    paramDataOutputStream.writeByte(this.f);
    } catch (IOException x) {
		  System.out.println("BTCS: Exception X129 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 9;
  }
}
