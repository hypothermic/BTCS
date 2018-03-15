package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;




public class Packet42RemoveMobEffect
  extends Packet
{
  public int a;
  public byte b;
  
  public Packet42RemoveMobEffect() {}
  
  public Packet42RemoveMobEffect(int paramInt, MobEffect paramMobEffect)
  {
    this.a = paramInt;
    this.b = ((byte)(paramMobEffect.getEffectId() & 0xFF));
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readByte();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X144 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.b);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X145 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 5;
  }
}
