package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet130UpdateSign extends Packet {
  public int x;
  public int y;
  public int z;
  public String[] lines;
  
  public Packet130UpdateSign() {
    this.lowPriority = true;
  }
  
  public Packet130UpdateSign(int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString) {
    this.lowPriority = true;
    this.x = paramInt1;
    this.y = paramInt2;
    this.z = paramInt3;
    this.lines = paramArrayOfString;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.x = paramDataInputStream.readInt();
    this.y = paramDataInputStream.readShort();
    this.z = paramDataInputStream.readInt();
    this.lines = new String[4];
    for (int i = 0; i < 4; i++)
      this.lines[i] = a(paramDataInputStream, 15);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X67 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.x);
    paramDataOutputStream.writeShort(this.y);
    paramDataOutputStream.writeInt(this.z);
    for (int i = 0; i < 4; i++)
      a(this.lines[i], paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X68 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    int i = 0;
    for (int j = 0; j < 4; j++)
      i += this.lines[j].length();
    return i;
  }
}
