package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet60Explosion extends Packet
{
  public double a;
  public double b;
  public double c;
  public float d;
  public java.util.Set e;
  
  public Packet60Explosion() {}
  
  public Packet60Explosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, java.util.Set paramSet)
  {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.c = paramDouble3;
    this.d = paramFloat;
    this.e = new java.util.HashSet(paramSet);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readDouble();
    this.b = paramDataInputStream.readDouble();
    this.c = paramDataInputStream.readDouble();
    this.d = paramDataInputStream.readFloat();
    int i = paramDataInputStream.readInt();
    
    this.e = new java.util.HashSet();
    
    int j = (int)this.a;
    int k = (int)this.b;
    int m = (int)this.c;
    for (int n = 0; n < i; n++) {
      int i1 = paramDataInputStream.readByte() + j;
      int i2 = paramDataInputStream.readByte() + k;
      int i3 = paramDataInputStream.readByte() + m;
      this.e.add(new ChunkPosition(i1, i2, i3));
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X164 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeDouble(this.a);
    paramDataOutputStream.writeDouble(this.b);
    paramDataOutputStream.writeDouble(this.c);
    paramDataOutputStream.writeFloat(this.d);
    paramDataOutputStream.writeInt(this.e.size());
    
    int i = (int)this.a;
    int j = (int)this.b;
    int k = (int)this.c;
    for (ChunkPosition localChunkPosition : (ChunkPosition[]) this.e.toArray()) { // BTCS: added cast and .toArray()
      int m = localChunkPosition.x - i;
      int n = localChunkPosition.y - j;
      int i1 = localChunkPosition.z - k;
      paramDataOutputStream.writeByte(m);
      paramDataOutputStream.writeByte(n);
      paramDataOutputStream.writeByte(i1);
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X165 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 32 + this.e.size() * 3;
  }
}
