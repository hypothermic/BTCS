package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet9Respawn
  extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public WorldType e;
  
  public Packet9Respawn() {}
  
  public Packet9Respawn(int paramInt1, byte paramByte, WorldType paramWorldType, int paramInt2, int paramInt3)
  {
    this.a = paramInt1;
    this.b = paramByte;
    this.c = paramInt2;
    this.d = paramInt3;
    this.e = paramWorldType;
  }
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readByte();
    this.d = paramDataInputStream.readByte();
    this.c = paramDataInputStream.readShort();
    String str = a(paramDataInputStream, 16);
    this.e = WorldType.getType(str);
    if (this.e == null) {
      this.e = WorldType.NORMAL;
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X178 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.b);
    paramDataOutputStream.writeByte(this.d);
    paramDataOutputStream.writeShort(this.c);
    a(this.e.name(), paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X179 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  // BTCS informal note: finally the last fucking packet is done. I've spent 4 hours adding try-catch statements to these packets ffs. I could have probably more easily implemented 'throws' clauses.
  
  public int a() {
    return 8 + this.e.name().length();
  }
}
