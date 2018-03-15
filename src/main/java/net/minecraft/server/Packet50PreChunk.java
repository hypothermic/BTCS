package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet50PreChunk extends Packet {
  public int a;
  public int b;
  public boolean c;
  
  public Packet50PreChunk() { this.lowPriority = false; }
  
  public Packet50PreChunk(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.lowPriority = false;
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramBoolean;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.readInt();
    this.c = (paramDataInputStream.read() != 0);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X152 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeInt(this.b);
    paramDataOutputStream.write(this.c ? 1 : 0);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X153 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 9;
  }
}
