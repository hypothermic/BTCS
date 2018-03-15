package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet28EntityVelocity extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  
  public Packet28EntityVelocity() {}
  
  public Packet28EntityVelocity(Entity paramEntity)
  {
    this(paramEntity.id, paramEntity.motX, paramEntity.motY, paramEntity.motZ);
  }
  
  public Packet28EntityVelocity(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
    this.a = paramInt;
    double d1 = 3.9D;
    if (paramDouble1 < -d1) paramDouble1 = -d1;
    if (paramDouble2 < -d1) paramDouble2 = -d1;
    if (paramDouble3 < -d1) paramDouble3 = -d1;
    if (paramDouble1 > d1) paramDouble1 = d1;
    if (paramDouble2 > d1) paramDouble2 = d1;
    if (paramDouble3 > d1) paramDouble3 = d1;
    this.b = ((int)(paramDouble1 * 8000.0D));
    this.c = ((int)(paramDouble2 * 8000.0D));
    this.d = ((int)(paramDouble3 * 8000.0D));
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readShort();
    this.c = paramDataInputStream.readShort();
    this.d = paramDataInputStream.readShort();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X115 happened in Packet");
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
		  System.out.println("BTCS: Exception X116 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 10;
  }
}
