package net.minecraft.server;

import java.io.DataInputStream;
import java.io.IOException;

public class Packet6SpawnPosition extends Packet {
  public int x;
  public int y;
  public int z;
  
  public Packet6SpawnPosition() {}
  
  public Packet6SpawnPosition(int paramInt1, int paramInt2, int paramInt3) { this.x = paramInt1;
    this.y = paramInt2;
    this.z = paramInt3;
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.x = paramDataInputStream.readInt();
    this.y = paramDataInputStream.readInt();
    this.z = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X168 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(java.io.DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.x);
    paramDataOutputStream.writeInt(this.y);
    paramDataOutputStream.writeInt(this.z);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X169 happened in Packet");
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
