package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Packet71Weather
  extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  
  public Packet71Weather() {}
  
  public Packet71Weather(Entity paramEntity)
  {
    this.a = paramEntity.id;
    this.b = MathHelper.floor(paramEntity.locX * 32.0D);
    this.c = MathHelper.floor(paramEntity.locY * 32.0D);
    this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
    if ((paramEntity instanceof EntityWeatherLighting)) {
      this.e = 1;
    }
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.e = paramDataInputStream.readByte();
    this.b = paramDataInputStream.readInt();
    this.c = paramDataInputStream.readInt();
    this.d = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X172 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.e);
    paramDataOutputStream.writeInt(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.writeInt(this.d);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X173 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 17;
  }
}
