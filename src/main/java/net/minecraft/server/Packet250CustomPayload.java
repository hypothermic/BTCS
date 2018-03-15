package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet250CustomPayload
  extends Packet
{
  public String tag;
  public int length;
  public byte[] data;
  
  public void a(DataInputStream paramDataInputStream)
  { try {
    this.tag = a(paramDataInputStream, 16);
    this.length = paramDataInputStream.readShort();
    
    if ((this.length > 0) && (this.length < 32767)) {
      this.data = new byte[this.length];
      paramDataInputStream.readFully(this.data);
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X106 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    a(this.tag, paramDataOutputStream);
    paramDataOutputStream.writeShort((short)this.length);
    if (this.data != null) {
      paramDataOutputStream.write(this.data);
    }} catch (IOException x) {
		  System.out.println("BTCS: Exception X107 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 2 + this.tag.length() * 2 + 2 + this.length;
  }
}
