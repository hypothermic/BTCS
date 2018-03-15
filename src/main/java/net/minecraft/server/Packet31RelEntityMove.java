package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet31RelEntityMove
  extends Packet30Entity
{
  public Packet31RelEntityMove() {}
  
  public Packet31RelEntityMove(int paramInt, byte paramByte1, byte paramByte2, byte paramByte3)
  {
    super(paramInt);
    this.b = paramByte1;
    this.c = paramByte2;
    this.d = paramByte3;
  }
  
  public void a(DataInputStream paramDataInputStream) {
    super.a(paramDataInputStream);
    try {
    this.b = paramDataInputStream.readByte();
    this.c = paramDataInputStream.readByte();
    this.d = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X124 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
    super.a(paramDataOutputStream);
    try {
    paramDataOutputStream.writeByte(this.b);
    paramDataOutputStream.writeByte(this.c);
    paramDataOutputStream.writeByte(this.d);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X125 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 7;
  }
}
