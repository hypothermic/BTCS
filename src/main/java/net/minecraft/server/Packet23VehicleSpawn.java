package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

















public class Packet23VehicleSpawn
  extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  public int f;
  public int g;
  public int h;
  public int i;
  
  public Packet23VehicleSpawn() {}
  
  public Packet23VehicleSpawn(Entity paramEntity, int paramInt)
  {
    this(paramEntity, paramInt, 0);
  }
  
  public Packet23VehicleSpawn(Entity paramEntity, int paramInt1, int paramInt2) {
    this.a = paramEntity.id;
    this.b = MathHelper.floor(paramEntity.locX * 32.0D);
    this.c = MathHelper.floor(paramEntity.locY * 32.0D);
    this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
    this.h = paramInt1;
    this.i = paramInt2;
    if (paramInt2 > 0) {
      double d1 = paramEntity.motX;
      double d2 = paramEntity.motY;
      double d3 = paramEntity.motZ;
      double d4 = 3.9D;
      if (d1 < -d4) d1 = -d4;
      if (d2 < -d4) d2 = -d4;
      if (d3 < -d4) d3 = -d4;
      if (d1 > d4) d1 = d4;
      if (d2 > d4) d2 = d4;
      if (d3 > d4) d3 = d4;
      this.e = ((int)(d1 * 8000.0D));
      this.f = ((int)(d2 * 8000.0D));
      this.g = ((int)(d3 * 8000.0D));
    }
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.h = paramDataInputStream.readByte();
    this.b = paramDataInputStream.readInt();
    this.c = paramDataInputStream.readInt();
    this.d = paramDataInputStream.readInt();
    this.i = paramDataInputStream.readInt();
    if (this.i > 0) {
      this.e = paramDataInputStream.readShort();
      this.f = paramDataInputStream.readShort();
      this.g = paramDataInputStream.readShort();
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X104 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.h);
    paramDataOutputStream.writeInt(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.writeInt(this.d);
    paramDataOutputStream.writeInt(this.i);
    if (this.i > 0) {
      paramDataOutputStream.writeShort(this.e);
      paramDataOutputStream.writeShort(this.f);
      paramDataOutputStream.writeShort(this.g);
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X105 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 21 + this.i > 0 ? 6 : 0;
  }
}
