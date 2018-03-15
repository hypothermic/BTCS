package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet26AddExpOrb extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  
  public Packet26AddExpOrb() {}
  
  public Packet26AddExpOrb(EntityExperienceOrb paramEntityExperienceOrb)
  {
    this.a = paramEntityExperienceOrb.id;
    this.b = MathHelper.floor(paramEntityExperienceOrb.locX * 32.0D);
    this.c = MathHelper.floor(paramEntityExperienceOrb.locY * 32.0D);
    this.d = MathHelper.floor(paramEntityExperienceOrb.locZ * 32.0D);
    this.e = paramEntityExperienceOrb.y_();
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readInt();
    this.c = paramDataInputStream.readInt();
    this.d = paramDataInputStream.readInt();
    this.e = paramDataInputStream.readShort();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X112 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeInt(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.writeInt(this.d);
    paramDataOutputStream.writeShort(this.e);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X113 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 18;
  }
}
