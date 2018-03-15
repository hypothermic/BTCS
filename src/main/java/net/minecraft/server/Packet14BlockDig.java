package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet14BlockDig
  extends Packet
{
  public int a;
  public int b;
  public int c;
  public int face;
  public int e;
  
  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.e = paramDataInputStream.read();
    this.a = paramDataInputStream.readInt();
    this.b = paramDataInputStream.read();
    this.c = paramDataInputStream.readInt();
    this.face = paramDataInputStream.read();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X76 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.write(this.e);
    paramDataOutputStream.writeInt(this.a);
    paramDataOutputStream.write(this.b);
    paramDataOutputStream.writeInt(this.c);
    paramDataOutputStream.write(this.face);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X77 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 11;
  }
}
