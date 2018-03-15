package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet10Flying
  extends Packet
{
  public double x;
  public double y;
  public double z;
  public double stance;
  public float yaw;
  public float pitch;
  public boolean g;
  public boolean hasPos;
  public boolean hasLook;
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public void a(DataInputStream paramDataInputStream) {
    try {
		this.g = (paramDataInputStream.read() != 0);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X61 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
    try {
		paramDataOutputStream.write(this.g ? 1 : 0);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X62 happened in Packet");
		e.printStackTrace();
	}
  }
  
  public int a() {
    return 1;
  }
}
