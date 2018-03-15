package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;




public class Packet41MobEffect
  extends Packet
{
  public int a;
  public byte b;
  public byte c;
  public short d;
  
  public Packet41MobEffect() {}
  
  public Packet41MobEffect(int paramInt, MobEffect paramMobEffect)
  {
    this.a = paramInt;
    this.b = ((byte)(paramMobEffect.getEffectId() & 0xFF));
    this.c = ((byte)(paramMobEffect.getAmplifier() & 0xFF));
    this.d = ((short)paramMobEffect.getDuration());
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readByte();
    this.c = paramDataInputStream.readByte();
    this.d = paramDataInputStream.readShort();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X142 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeByte(this.b);
    paramDataOutputStream.writeByte(this.c);
    paramDataOutputStream.writeShort(this.d);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X143 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 8;
  }
}
