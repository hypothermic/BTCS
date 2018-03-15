package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet54PlayNoteBlock extends Packet {
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  
  public Packet54PlayNoteBlock() {}
  
  public Packet54PlayNoteBlock(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) { this.a = paramInt1;
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
    this.d = paramDataInputStream.read();
    this.e = paramDataInputStream.read();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X158 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.writeShort(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.write(this.d);
    paramDataOutputStream.write(this.e);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X159 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 12;
  }
}
