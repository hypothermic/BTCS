package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet132TileEntityData extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  public int f;
  public int g;
  
  public Packet132TileEntityData()
  {
    this.lowPriority = true;
  }
  
  public Packet132TileEntityData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    this.lowPriority = true;
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramInt4;
    this.e = paramInt5;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readShort();
    this.c = paramDataInputStream.readInt();
    this.d = paramDataInputStream.readByte();
    this.e = paramDataInputStream.readInt();
    this.f = paramDataInputStream.readInt();
    this.g = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X71 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeShort(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.writeByte((byte)this.d);
    paramDataOutputStream.writeInt(this.e);
    paramDataOutputStream.writeInt(this.f);
    paramDataOutputStream.writeInt(this.g);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X72 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 25;
  }
}
