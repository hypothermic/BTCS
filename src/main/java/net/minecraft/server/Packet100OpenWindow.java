package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;





public class Packet100OpenWindow
  extends Packet
{
  public int a;
  public int b;
  public String c;
  public int d;
  
  public Packet100OpenWindow() {}
  
  public Packet100OpenWindow(int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramString;
    this.d = paramInt3;
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try { // BTCS
    this.a = (paramDataInputStream.readByte() & 0xFF);
    this.b = (paramDataInputStream.readByte() & 0xFF);
    this.c = a(paramDataInputStream, 32);
    this.d = (paramDataInputStream.readByte() & 0xFF);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X37 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeByte(this.a & 0xFF);
    paramDataOutputStream.writeByte(this.b & 0xFF);
    a(this.c, paramDataOutputStream);
    paramDataOutputStream.writeByte(this.d & 0xFF);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X38 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 3 + this.c.length();
  }
}
