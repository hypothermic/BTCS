package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet132TileEntityData extends Packet
{
  public int a; // this is probably the X position, not sure.
  public int b; // this is probably the Y position
  public int c; // this is probably the Z position
  public int d; // this is probably the action type??
  public int e; // this is probably the 1st nbt tag compound although it's an int? wtf?
  public int f; // 2nd custom data?
  public int g; // 3rd custom data? don't know what it would else be.
  
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
		  System.out.println("BTCS: Exception X71 happened in Packet132");
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
		  System.out.println("BTCS: Exception X72 happened in Packet132");
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
